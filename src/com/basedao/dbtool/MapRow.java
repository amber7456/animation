package com.basedao.dbtool;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

public class MapRow implements RowMapper<MapBean> {
	public MapBean mapRow(ResultSet rs, int arg1) throws SQLException {
		MapBean db = new MapBean();
		Map<String, String> map = new HashMap<String, String>();
		ResultSetMetaData md = rs.getMetaData();
		for (int i = 1; i < (md.getColumnCount() + 1); i++) {
			if (md.getColumnTypeName(i).equals("BLOB")) {
				try {

					if (rs.getBlob(i) != null) {
						Blob bb = rs.getBlob(i);
						byte[] b = bb.getBytes(1, (int) bb.length());
						String result = new String(b, "utf-8");
						map.put(md.getColumnName(i), result);
					} else {
						map.put(md.getColumnName(i), null);
					}

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {
				map.put(md.getColumnName(i), rs.getString(md.getColumnName(i)));
			}
		}
		db.setData(map);
		return db;
	}
}
