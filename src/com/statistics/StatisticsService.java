package com.statistics;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basedao.dbtool.MapBean;
import com.bean.search.SearchBean;

@Component
public class StatisticsService {

	@Autowired
	public StatisticsDao statisticsDao;

	public Map<String, Object> getStatisticsForLine(SearchBean searchBean) throws SQLException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<MapBean> dataList = statisticsDao.getStatisticsForLine(searchBean);
		int startYear = Integer.valueOf(searchBean.getStartYear());
		int endYear = Integer.valueOf(searchBean.getEndYear());
		int[] yearArr = new int[endYear - startYear + 1];
		for (int i = 1; i < 8; i++) {
			int[] typeCount = new int[endYear - startYear + 1];
			for (int j = startYear; j <= endYear; j++) {
				for (int k = 0; k < dataList.size(); k++) {
					if (j == Integer.valueOf(dataList.get(k).getData().get("YEAR"))
							&& i == Integer.valueOf(dataList.get(k).getData().get("ANIMATION_TYPE"))) {
						typeCount[j - startYear] = Integer.valueOf(dataList.get(k).getData().get("COUNT"));
						break;
					}
				}
				yearArr[j - startYear] = j;
			}
			modelMap.put("TYPE" + i, typeCount);
		}
		modelMap.put("yearArr", yearArr);

		List<MapBean> yearSumList = statisticsDao.getStatisticsForYearSumList(searchBean);
		int[] yearSum = new int[endYear - startYear + 1];
		for (int i = startYear; i <= endYear; i++) {
			for (int j = 0; j < yearSumList.size(); j++) {
				if (i == Integer.valueOf(yearSumList.get(j).getData().get("YEAR"))) {
					yearSum[i - startYear] = Integer.valueOf(yearSumList.get(j).getData().get("SUM"));
					break;
				}
			}
		}
		modelMap.put("yearSum", yearSum);

		List<MapBean> yearSeasonSumList = statisticsDao.getStatisticsForYearSeasonSumList(searchBean);
		int[] yearSeasonSum = new int[endYear - startYear + 1];
		for (int i = startYear; i <= endYear; i++) {
			for (int j = 0; j < yearSeasonSumList.size(); j++) {
				if (i == Integer.valueOf(yearSeasonSumList.get(j).getData().get("YEAR"))) {
					yearSeasonSum[i - startYear] = Integer.valueOf(yearSeasonSumList.get(j).getData().get("SUM"));
					break;
				}
			}
		}
		modelMap.put("yearSeasonSum", yearSeasonSum);

		return modelMap;
	}

	public Map<String, Object> getStatisticsForPie(SearchBean searchBean) throws SQLException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<MapBean> dataList = statisticsDao.getStatisticsForPie(searchBean);

		for (int i = 1; i < 8; i++) {
			for (int j = 0; j < dataList.size(); j++) {
				if (i == Integer.valueOf(dataList.get(j).getData().get("ANIMATION_TYPE"))) {
					modelMap.put("TYPE" + i, Integer.valueOf(dataList.get(j).getData().get("COUNT")));
					break;
				}
			}
		}

		return modelMap;
	}

	public Map<String, Object> getStatisticsForBD(SearchBean searchBean) throws SQLException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		List<MapBean> allDataList = statisticsDao.getStatisticsForBD(searchBean, "ALL");
		int[] all = bdStatistics(allDataList);
		modelMap.put("all", all);
		
		List<MapBean> bdDataList = statisticsDao.getStatisticsForBD(searchBean, "BD");
		int[] bd = bdStatistics(bdDataList);
		modelMap.put("BD", bd);

		return modelMap;
	}

	private int[] bdStatistics(List<MapBean> dataList) {
		int[] arr = new int[9];

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < dataList.size(); j++) {
				if (i == (Integer.valueOf(dataList.get(j).getData().get("ANIMATION_TYPE")) - 1)) {
					arr[i] = Integer.valueOf(dataList.get(j).getData().get("COUNT"));
					break;
				}
			}
		}

		int seasonCount = 0;
		int allCount = 0;
		for (int i = 0; i < dataList.size(); i++) {
			if (Integer.valueOf(dataList.get(i).getData().get("ANIMATION_TYPE")) < 5) {
				seasonCount = seasonCount + Integer.valueOf(dataList.get(i).getData().get("COUNT"));
			}
			allCount = allCount + Integer.valueOf(dataList.get(i).getData().get("COUNT"));
		}
		arr[7] = seasonCount;
		arr[8] = allCount;
		return arr;
	}

}
