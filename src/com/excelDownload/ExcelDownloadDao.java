package com.excelDownload;

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
public class ExcelDownloadDao {

	@Autowired
	public DBTools DBTools;

	public List<MapBean> searchAnimationByName(SearchBean searchBean) throws SQLException {
		List<MapBean> dataList = new ArrayList<MapBean>();
		List<Param> pList = new ArrayList<Param>();
		pList.add(new Param("%" + searchBean.getAnimation_name() + "%", ColumnType.VARCHAR));
		dataList = DBTools.table("ANIMATION_INFORMATION").where(" ANIMATION_NAME LIKE ? ", pList).order(" DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y'),ANIMATION_TYPE ASC ").select();
		return dataList;
	}
	
	public List<MapBean> searchAnimationByYear(SearchBean searchBean) throws SQLException {
		List<MapBean> dataList = new ArrayList<MapBean>();
		List<Param> pList = new ArrayList<Param>();
		pList.add(new Param( searchBean.getStartYear() , ColumnType.VARCHAR));
		dataList = DBTools.table("ANIMATION_INFORMATION").where(" DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') = ? ", pList).select();
		return dataList;
	}

	public List<MapBean> advSearch(SearchBean searchBean) throws SQLException {
		List<MapBean> dataList = new ArrayList<MapBean>();
		List<Param> pList = new ArrayList<Param>();
		StringBuffer sql = new StringBuffer(
				"DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') >= ? AND DATE_FORMAT(ANIMATION_BROADCAST_TIME, '%Y') <= ? ");

		pList.add(new Param(searchBean.getStartYear(), ColumnType.VARCHAR));
		pList.add(new Param(searchBean.getEndYear(), ColumnType.VARCHAR));

		if (!searchBean.getAnimation_type().equals("ALL")) {
			sql.append(" AND ANIMATION_TYPE = ? ");
			pList.add(new Param(searchBean.getAnimation_type(), ColumnType.VARCHAR));
		}

		if (searchBean.getAnimation_name() != null && searchBean.getAnimation_name().trim().length() > 0) {
			sql.append(" AND ANIMATION_NAME LIKE ? ");
			pList.add(new Param("%" + searchBean.getAnimation_name() + "%", ColumnType.VARCHAR));
		}

		sql.append(" ORDER BY ANIMATION_BROADCAST_TIME ");
		dataList = DBTools.table("ANIMATION_INFORMATION").where(sql.toString(), pList).select();

		return dataList;
	}



}
