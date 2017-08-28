package com.statistics;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.basedao.dbtool.ColumnType;
import com.basedao.dbtool.DBTools;
import com.basedao.dbtool.MapBean;
import com.basedao.dbtool.Param;
import com.bean.search.SearchBean;

@Repository
public class StatisticsDao {

	@Autowired
	public DBTools DBTools;

	//
	public List<MapBean> getStatisticsForLine(SearchBean searchBean) throws SQLException {
		List<MapBean> dataList = new ArrayList<MapBean>();
		List<Param> pList = new ArrayList<Param>();
		pList.add(new Param(searchBean.getStartYear(), ColumnType.VARCHAR));
		pList.add(new Param(searchBean.getEndYear(), ColumnType.VARCHAR));
		String sql = "SELECT DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') AS YEAR, ANIMATION_TYPE, COUNT(ANIMATION_NAME) AS COUNT FROM"
				+ " ANIMATION_INFORMATION WHERE DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') >= ? AND DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') <= ? "
				+ "GROUP BY DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y'), ANIMATION_TYPE ORDER BY YEAR, ANIMATION_TYPE";
		dataList = DBTools.query(sql, pList);
		return dataList;
	}

	/**
	 * 获取年总数
	 * 
	 * @param searchBean
	 * @return
	 * @throws SQLException
	 */
	public List<MapBean> getStatisticsForYearSumList(SearchBean searchBean) throws SQLException {
		String sql = "SELECT DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') AS YEAR ,COUNT(ANIMATION_NAME) AS SUM FROM "
				+ "ANIMATION_INFORMATION WHERE DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') >= ? AND DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') <= ? "
				+ "GROUP BY DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') ORDER BY DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y')";
		List<MapBean> dataList = new ArrayList<MapBean>();
		List<Param> pList = new ArrayList<Param>();
		pList.add(new Param(searchBean.getStartYear(), ColumnType.VARCHAR));
		pList.add(new Param(searchBean.getEndYear(), ColumnType.VARCHAR));
		dataList = DBTools.query(sql, pList);
		return dataList;
	}

	public List<MapBean> getStatisticsForYearSeasonSumList(SearchBean searchBean) throws SQLException {
		String sql = "SELECT DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') AS YEAR ,COUNT(ANIMATION_NAME) AS SUM FROM "
				+ "ANIMATION_INFORMATION WHERE DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') >= ? AND DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') <= ? AND ANIMATION_TYPE IN (1,2,3,4) "
				+ "GROUP BY DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') ORDER BY DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y')";
		List<MapBean> dataList = new ArrayList<MapBean>();
		List<Param> pList = new ArrayList<Param>();
		pList.add(new Param(searchBean.getStartYear(), ColumnType.VARCHAR));
		pList.add(new Param(searchBean.getEndYear(), ColumnType.VARCHAR));
		dataList = DBTools.query(sql, pList);
		return dataList;
	}

	public List<MapBean> getStatisticsForPie(SearchBean searchBean) throws SQLException {

		String sql = "SELECT ANIMATION_TYPE, COUNT(ANIMATION_NAME) AS COUNT FROM ANIMATION_INFORMATION WHERE DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') = ? "
				+ "GROUP BY ANIMATION_TYPE ORDER BY ANIMATION_TYPE ";
		List<MapBean> dataList = new ArrayList<MapBean>();
		List<Param> pList = new ArrayList<Param>();
		pList.add(new Param(searchBean.getStartYear(), ColumnType.VARCHAR));
		dataList = DBTools.query(sql, pList);
		return dataList;
	}

	public List<MapBean> getStatisticsForBD(SearchBean searchBean, String type) throws SQLException {
		String sql = "";
		List<MapBean> dataList = new ArrayList<MapBean>();
		List<Param> pList = new ArrayList<Param>();
		pList.add(new Param(searchBean.getStartYear(), ColumnType.VARCHAR)); 
		if (type.equals("ALL")) {
			sql = "SELECT DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') AS YEAR, ANIMATION_TYPE, COUNT(ANIMATION_NAME) AS COUNT FROM"
					+ " ANIMATION_INFORMATION WHERE DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') = ? "
					+ "GROUP BY DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y'), ANIMATION_TYPE ORDER BY YEAR, ANIMATION_TYPE";
		}

		if (type.equals("BD")) {
			sql = "SELECT DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') AS YEAR, ANIMATION_TYPE, COUNT(ANIMATION_NAME) AS COUNT FROM"
					+ " ANIMATION_INFORMATION WHERE DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') = ?  AND HAVE_BD_RESOURCE = 1 "
					+ "GROUP BY DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y'), ANIMATION_TYPE ORDER BY YEAR, ANIMATION_TYPE";
		}

		dataList = DBTools.query(sql, pList);
		return dataList;
	}

}
