package com.disk;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basedao.dbtool.MapBean;
import com.bean.disk.DiskBean;

@Component
public class DiskService {

	@Autowired
	public DiskDao diskDao;

	public List<MapBean> getDiskList(DiskBean diskBean) throws SQLException {
		return diskDao.getDiskList(diskBean);
	}

	public int diskAdd(DiskBean diskBean) { 
		return diskDao.diskAdd(diskBean);
	}

}
