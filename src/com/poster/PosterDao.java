package com.poster;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.basedao.BaseDAO;
import com.bean.poster.PosterBean;

@Repository
public class PosterDao extends BaseDAO<PosterBean> {

	public byte[] getPosterById(String id) {
		String sql = "SELECT POSTER_HQ FROM animation_poster WHERE animation_id = ? ";
		Object params[] = new Object[] { id };
		List<PosterBean> photoList = this.query(sql, params, new PosterRowMapper());
		byte[] r = photoList.get(0).getPoster_hq();
		return r;
	}

	/**
	 * 数据的映射
	 */
	public class PosterRowMapper implements RowMapper<Object> {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			PosterBean poster = new PosterBean();
			if (hasColumn(rs, "POSTER_HQ")) {
				poster.setPoster_hq(rs.getBytes("POSTER_HQ"));
			}
			return poster;
		}
	}

}
