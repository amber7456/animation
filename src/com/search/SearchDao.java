package com.search;

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
public class SearchDao {

	@Autowired
	public DBTools DBTools;

	public List<MapBean> searchAnimationByName(SearchBean searchBean) throws SQLException {
		List<MapBean> dataList = new ArrayList<MapBean>();
		List<Param> pList = new ArrayList<Param>();
		pList.add(new Param("%" + searchBean.getAnimation_name() + "%", ColumnType.VARCHAR));
		dataList = DBTools.table("ANIMATION_INFORMATION").where(" ANIMATION_NAME LIKE ? ", pList)
				.order(" DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y'),ANIMATION_TYPE ASC,ANIMATION_BROADCAST_TIME ")
				.select();
		return dataList;
	}

	public List<MapBean> searchAnimationByYear(SearchBean searchBean) throws SQLException {
		List<MapBean> dataList = new ArrayList<MapBean>();
		List<Param> pList = new ArrayList<Param>();
		pList.add(new Param(searchBean.getStartYear(), ColumnType.VARCHAR));
		dataList = DBTools.table("ANIMATION_INFORMATION")
				.where(" DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') = ? ", pList)
				.order(" ANIMATION_TYPE,ANIMATION_BROADCAST_TIME ").select();
		return dataList;
	}

	public List<MapBean> advSearch(SearchBean searchBean) throws SQLException {
		List<MapBean> dataList = new ArrayList<MapBean>();
		List<Param> pList = new ArrayList<Param>();

		StringBuffer sql = new StringBuffer(
				"SELECT DISTINCT t1.* FROM ANIMATION_INFORMATION t1 LEFT JOIN ANIMATION_RESOURCE t2 "
						+ "ON t1.ANIMATION_ID = t2.ANIMATION_ID WHERE 1 = 1 ");

		if (!searchBean.getAnimation_type().equals("ALL")) {
			sql.append(" AND ANIMATION_TYPE = ? ");
			pList.add(new Param(searchBean.getAnimation_type(), ColumnType.VARCHAR));
		}

		if (!searchBean.getAnimation_source().equals("ALL")) {
			sql.append(" AND ANIMATION_SOURCE = ? ");
			pList.add(new Param(searchBean.getAnimation_source(), ColumnType.VARCHAR));
		}

		if (!searchBean.getIsBD().equals("ALL")) {
			sql.append(" AND HAVE_BD_RESOURCE = ? ");
			pList.add(new Param(searchBean.getIsBD(), ColumnType.VARCHAR));
		}

		if (searchBean.getYearRange() != null && !searchBean.getYearRange().equals("")) {
			sql.append(
					" AND DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') >= ? AND DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') <= ? ");
			pList.add(new Param(searchBean.getYearRange().split(" - ")[0].replace("年", ""), ColumnType.VARCHAR));
			pList.add(new Param(searchBean.getYearRange().split(" - ")[1].replace("年", ""), ColumnType.VARCHAR));
		}

		if (!searchBean.getDisk_name().equals("ALL")) {
			sql.append(" AND t2.RESOURCE_ADDRESS = ? ");
			pList.add(new Param(searchBean.getDisk_name(), ColumnType.VARCHAR));
		}

		if (searchBean.getAnimation_name() != null && searchBean.getAnimation_name().trim().length() > 0) {
			sql.append(" AND ANIMATION_NAME LIKE ? ");
			pList.add(new Param("%" + searchBean.getAnimation_name() + "%", ColumnType.VARCHAR));
		}

		sql.append(" ORDER BY DATE_FORMAT(t1.ANIMATION_BROADCAST_TIME, '%Y') , t1.ANIMATION_TYPE , t1.ANIMATION_BROADCAST_TIME ");

		dataList = DBTools.query(sql.toString(), pList);

		return dataList;
	}

	public List<MapBean> searchResourceByYear(SearchBean searchBean) throws SQLException {
		String sql = "SELECT T2.* FROM ANIMATION_INFORMATION T1 JOIN ANIMATION_RESOURCE T2 ON T1.ANIMATION_ID = T2.ANIMATION_ID "
				+ "WHERE DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') = ? ORDER BY RESOURCE_TYPE ";
		List<MapBean> dataList = new ArrayList<MapBean>();
		List<Param> pList = new ArrayList<Param>();
		pList.add(new Param(searchBean.getStartYear(), ColumnType.VARCHAR));
		dataList = DBTools.query(sql, pList);
		return dataList;
	}

	public List<MapBean> searchResourceByName(SearchBean searchBean) throws SQLException {
		String sql = "SELECT T2.* FROM ANIMATION_INFORMATION T1 JOIN ANIMATION_RESOURCE T2 ON T1.ANIMATION_ID = T2.ANIMATION_ID "
				+ "WHERE ANIMATION_NAME LIKE ?  ORDER BY RESOURCE_TYPE ";
		List<MapBean> dataList = new ArrayList<MapBean>();
		List<Param> pList = new ArrayList<Param>();
		pList.add(new Param("%" + searchBean.getAnimation_name() + "%", ColumnType.VARCHAR));
		dataList = DBTools.query(sql, pList);
		return dataList;
	}

	public List<MapBean> searchResourceByAdv(SearchBean searchBean) throws SQLException {
		List<MapBean> dataList = new ArrayList<MapBean>();
		List<Param> pList = new ArrayList<Param>();

		StringBuffer sql = new StringBuffer(
				"SELECT t2.* FROM ANIMATION_INFORMATION t1 LEFT JOIN ANIMATION_RESOURCE t2 "
						+ "ON t1.ANIMATION_ID = t2.ANIMATION_ID WHERE 1 = 1 ");

		if (!searchBean.getAnimation_type().equals("ALL")) {
			sql.append(" AND ANIMATION_TYPE = ? ");
			pList.add(new Param(searchBean.getAnimation_type(), ColumnType.VARCHAR));
		}

		if (!searchBean.getAnimation_source().equals("ALL")) {
			sql.append(" AND ANIMATION_SOURCE = ? ");
			pList.add(new Param(searchBean.getAnimation_source(), ColumnType.VARCHAR));
		}

		if (!searchBean.getIsBD().equals("ALL")) {
			sql.append(" AND HAVE_BD_RESOURCE = ? ");
			pList.add(new Param(searchBean.getIsBD(), ColumnType.VARCHAR));
		}

		if (searchBean.getYearRange() != null && !searchBean.getYearRange().equals("")) {
			sql.append(
					" AND DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') >= ? AND DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') <= ? ");
			pList.add(new Param(searchBean.getYearRange().split(" - ")[0].replace("年", ""), ColumnType.VARCHAR));
			pList.add(new Param(searchBean.getYearRange().split(" - ")[1].replace("年", ""), ColumnType.VARCHAR));
		}

		if (!searchBean.getDisk_name().equals("ALL")) {
			sql.append(" AND t2.RESOURCE_ADDRESS = ? ");
			pList.add(new Param(searchBean.getDisk_name(), ColumnType.VARCHAR));
		}

		if (searchBean.getAnimation_name() != null && searchBean.getAnimation_name().trim().length() > 0) {
			sql.append(" AND ANIMATION_NAME LIKE ? ");
			pList.add(new Param("%" + searchBean.getAnimation_name() + "%", ColumnType.VARCHAR));
		}

		sql.append(" ORDER BY DATE_FORMAT(t1.ANIMATION_BROADCAST_TIME, '%Y') , t1.ANIMATION_TYPE , t1.ANIMATION_BROADCAST_TIME, t2.RESOURCE_TYPE DESC , t2.RESOURCE_ADDRESS ");

		dataList = DBTools.query(sql.toString(), pList);

		return dataList;
	}

}
