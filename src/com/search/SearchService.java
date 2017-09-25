package com.search;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basedao.dbtool.MapBean;
import com.bean.search.SearchBean;
import com.bean.yearlist.AnimationYearListBean;

@Component
public class SearchService {

	@Autowired
	public SearchDao searchDao;

	public List<MapBean> searchAnimationByName(SearchBean searchBean) throws SQLException {
		return searchDao.searchAnimationByName(searchBean);
	}

	public List<MapBean> searchAnimationByYear(SearchBean searchBean) throws SQLException {
		return searchDao.searchAnimationByYear(searchBean);
	}

	public List<AnimationYearListBean> advSearch(SearchBean searchBean) throws SQLException {

		List<AnimationYearListBean> animationYearList = new ArrayList<AnimationYearListBean>();
		List<MapBean> dataList = searchDao.advSearch(searchBean);

		HashSet<String> yearSet = new HashSet<String>();
		for (int i = 0; i < dataList.size(); i++) {
			String year = dataList.get(i).getData().get("ANIMATION_BROADCAST_TIME").substring(0, 4);
			yearSet.add(year);
		}
		List<String> yearList = new ArrayList<>(yearSet);
		Collections.sort(yearList);
		for (int i = 0; i < yearList.size(); i++) {
			AnimationYearListBean animationYearListBean = new AnimationYearListBean();
			animationYearListBean.setYear(yearList.get(i));
			List<MapBean> animationList = new ArrayList<MapBean>();
			for (int j = 0; j < dataList.size(); j++) {
				if (yearList.get(i).equals(dataList.get(j).getData().get("ANIMATION_BROADCAST_TIME").substring(0, 4))) {
					animationList.add(dataList.get(j));
				}
			}
			animationYearListBean.setAnimationList(animationList);
			animationYearList.add(animationYearListBean);
		}

		return animationYearList;
	}

	public AnimationYearListBean clearUp(List<MapBean> animationList, SearchBean searchBean) throws SQLException {

		AnimationYearListBean y = new AnimationYearListBean();
		List<MapBean> fuyuList = new ArrayList<MapBean>();
		for (int i = 0; i < animationList.size(); i++) {
			if (animationList.get(i).getData().get("ANIMATION_TYPE").equals("1")) {
				fuyuList.add(animationList.get(i));
			}
		}

		List<MapBean> haruList = new ArrayList<MapBean>();
		for (int i = 0; i < animationList.size(); i++) {
			if (animationList.get(i).getData().get("ANIMATION_TYPE").equals("2")) {
				haruList.add(animationList.get(i));
			}
		}

		List<MapBean> natsuList = new ArrayList<MapBean>();
		for (int i = 0; i < animationList.size(); i++) {
			if (animationList.get(i).getData().get("ANIMATION_TYPE").equals("3")) {
				natsuList.add(animationList.get(i));
			}
		}

		List<MapBean> akiList = new ArrayList<MapBean>();
		for (int i = 0; i < animationList.size(); i++) {
			if (animationList.get(i).getData().get("ANIMATION_TYPE").equals("4")) {
				akiList.add(animationList.get(i));
			}
		}

		List<MapBean> ovaList = new ArrayList<MapBean>();
		for (int i = 0; i < animationList.size(); i++) {
			if (animationList.get(i).getData().get("ANIMATION_TYPE").equals("5")) {
				ovaList.add(animationList.get(i));
			}
		}

		List<MapBean> movieList = new ArrayList<MapBean>();
		for (int i = 0; i < animationList.size(); i++) {
			if (animationList.get(i).getData().get("ANIMATION_TYPE").equals("6")) {
				movieList.add(animationList.get(i));
			}
		}

		List<MapBean> otherList = new ArrayList<MapBean>();
		for (int i = 0; i < animationList.size(); i++) {
			if (animationList.get(i).getData().get("ANIMATION_TYPE") == null
					|| animationList.get(i).getData().get("ANIMATION_TYPE").equals("7")
					|| animationList.get(i).getData().get("ANIMATION_TYPE").equals("")) {
				otherList.add(animationList.get(i));
			}
		}

		HashSet<String> yearSet = new HashSet<String>();
		for (int i = 0; i < animationList.size(); i++) {
			yearSet.add(animationList.get(i).getData().get("ANIMATION_BROADCAST_TIME").substring(0, 4));
		}
		List<String> yearList = new ArrayList<String>(yearSet);
		Collections.sort(yearList);

		Map<String, String> count = new HashMap<String, String>();
		count.put("count", String.valueOf(animationList.size()));
		count.put("fuyu", String.valueOf(fuyuList.size()));
		count.put("haru", String.valueOf(haruList.size()));
		count.put("natsu", String.valueOf(natsuList.size()));
		count.put("aki", String.valueOf(akiList.size()));
		count.put("ova", String.valueOf(ovaList.size()));
		count.put("movie", String.valueOf(movieList.size()));
		count.put("other", String.valueOf(otherList.size()));

		y.setYear(searchBean.getStartYear());
		y.setAnimationList(animationList);
		y.setFuyuList(fuyuList);
		y.setHaruList(haruList);
		y.setNatsuList(natsuList);
		y.setAkiList(akiList);
		y.setOvaList(ovaList);
		y.setMovieList(movieList);
		y.setOtherList(otherList);
		y.setCount(count);
		y.setYearList(yearList);
		return y;
	}

	public List<MapBean> searchResourceByYear(SearchBean searchBean) throws SQLException {
		return searchDao.searchResourceByYear(searchBean);
	}

	public List<MapBean> searchResourceByName(SearchBean searchBean) throws SQLException {
		return searchDao.searchResourceByName(searchBean);
	}

	public List<MapBean> searchResourceByAdv(SearchBean searchBean) throws SQLException {
		return searchDao.searchResourceByAdv(searchBean);
	}

}
