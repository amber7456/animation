package com.disk;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.basedao.dbtool.Column;
import com.basedao.dbtool.ColumnType;
import com.basedao.dbtool.DBTools;
import com.basedao.dbtool.MapBean;
import com.basedao.dbtool.Param;
import com.bean.disk.DiskBean;
import com.utils.CommonUtils;

@Transactional
@Repository
public class DiskDao {

	@Autowired
	public DBTools DBTools;

	public List<MapBean> getDiskList(DiskBean diskBean) throws SQLException {
		List<MapBean> dataList = new ArrayList<MapBean>();

		if (diskBean.getDisk_name() != null && diskBean.getDisk_name().trim().length() > 0) {
			List<Param> pList = new ArrayList<Param>();
			pList.add(new Param("%" + diskBean.getDisk_name() + "%", ColumnType.VARCHAR));
			dataList = DBTools.table("disk_information").where(" disk_name LIKE ? ", pList)
					.order("disk_name,create_time ").select();
		} else {
			dataList = DBTools.table("disk_information").order("disk_name,is_full ").select();
		}
		return dataList;

	}

	public List<MapBean> getDiskList() throws SQLException {
		List<MapBean> dataList = new ArrayList<MapBean>();
		dataList = DBTools.table("disk_information").order("is_full ").select();
		return dataList;
	}

	public int diskAdd(DiskBean diskBean) {
		int r = 0;

		List<Column> cList = new ArrayList<Column>();
		String id = CommonUtils.getNowStr("YYYYMMddHHmmssSSS");
		cList.add(new Column("disk_id", id, ColumnType.VARCHAR));//
		cList.add(new Column("disk_name", diskBean.getDisk_name().trim(), ColumnType.VARCHAR));//
		cList.add(new Column("disk_capacity", diskBean.getDisk_capacity(), ColumnType.VARCHAR));//
		cList.add(new Column("disk_state", "1", ColumnType.VARCHAR));//
		cList.add(new Column("is_full", "0", ColumnType.VARCHAR));//
		cList.add(new Column("create_time", CommonUtils.getNowStr("YYYY-MM-dd HH:mm:ss"), ColumnType.VARCHAR)); //
		r = DBTools.table("disk_information").add(cList);

		return r;
	}

	public List<MapBean> getDiskById(String disk_name) throws SQLException {
		List<MapBean> dataList = new ArrayList<MapBean>();
		List<Param> pList = new ArrayList<Param>();
		pList.add(new Param(disk_name, ColumnType.VARCHAR));
		dataList = DBTools.table("disk_information").where(" DISK_ID = ? ", pList).select();
		return dataList;
	}

	public int diskEdit(DiskBean diskBean) {
		int r = 0;
		List<Param> pList = new ArrayList<Param>();
		pList.add(new Param(diskBean.getDisk_id(), ColumnType.VARCHAR));

		List<Column> cList = new ArrayList<Column>();
		cList.add(new Column("disk_capacity", diskBean.getDisk_capacity(), ColumnType.VARCHAR));//
		cList.add(new Column("disk_state", diskBean.getDisk_state(), ColumnType.VARCHAR));//
		cList.add(new Column("is_full", diskBean.getIs_full(), ColumnType.VARCHAR));//
		r = DBTools.table("disk_information").where(" disk_id = ? ", pList).update(cList);

		return r;
	}

}
