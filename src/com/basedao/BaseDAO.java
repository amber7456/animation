package com.basedao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.basedao.dbtool.MapBean;
import com.basedao.dbtool.MapRow;
import com.utils.Log4jFactory;

/**
 * @author Chy
 * 
 *         DAO层父类
 */
@SuppressWarnings("rawtypes")
@Repository("BaseDAO")
public abstract class BaseDAO<T> {

	public static final String DBName = "sims.";
	public int pageSize = 10;

	/**
	 * 日志输出
	 */
	private static final Logger LOG = Log4jFactory.getLogger(BaseDAO.class);
	/**
	 * SQL执行时间的预警值，将来要在配置中存储
	 */
	private static final long SQL_TIME_WARNING_THRESHOLD = 2000;
	/**
	 * JDBC对象
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 列表查询
	 * 
	 * @param sql
	 *            SQL
	 * @param rm
	 *            存放对象
	 * @return
	 */
	protected List<T> query(String sql, RowMapper rm) {
		return this.query(sql, null, rm);
	}

	protected List<MapBean> queryRowMap(String sql) {
		return this.queryRowMap(sql, null);
	}

	/**
	 * 不带条件分页查询
	 * 
	 * @param baseSql
	 *            基础SQL
	 * @param rm
	 *            存放对象
	 * @param begin
	 *            开始行数
	 * @param length
	 *            最大取出行数
	 * @return
	 */
	protected List<T> pageQuery(String baseSql, RowMapper rm, int begin, int length) {

		String sql = baseSql + " limit ?,? ";
		Object[] newparams = new Object[2];

		int i = 0;
		newparams[i++] = new Integer(begin);
		newparams[i] = new Integer(length);

		BaseDAO.LOG.info(sql + this.toString(newparams));

		return this.query(sql, newparams, rm);
	}

	protected List<MapBean> pageQueryRowMap(String baseSql, int pageNum) {

		String sql = baseSql + " limit ?,? ";
		Object[] newparams = new Object[2];

		int i = 0;
		newparams[i++] = new Integer((Integer.valueOf(pageNum) - 1) * pageSize);
		newparams[i] = new Integer(pageSize);

		BaseDAO.LOG.info(sql + this.toString(newparams));

		return this.queryRowMap(sql, newparams);
	}

	/**
	 * 带条件分页查询
	 * 
	 * @param baseSql
	 *            基础SQL
	 * @param params
	 *            查询条件
	 * @param rm
	 *            存放对象
	 * @param begin
	 *            开始行数
	 * @param length
	 *            最大取出行数
	 * @return
	 */
	protected List<T> pageQuery(String baseSql, Object[] params, RowMapper rm, int begin, int length) {

		String sql = baseSql + " limit ?,? ";

		Object[] newparams = new Object[params.length + 2];

		int i = 0;

		if (null != params) {
			newparams = new Object[params.length + 2];
			for (i = 0; i < params.length; i++) {
				newparams[i] = params[i];
			}
		}

		newparams[i++] = new Integer(begin);
		newparams[i] = new Integer(length);

		BaseDAO.LOG.info(sql + this.toString(newparams));

		return this.query(sql, newparams, rm);
	}

	protected List<MapBean> pageQueryRowMap(String baseSql, Object[] params, int pageNum) {

		String sql = baseSql + " limit ?,? ";

		Object[] newparams = new Object[params.length + 2];

		int i = 0;

		if (null != params) {
			newparams = new Object[params.length + 2];
			for (i = 0; i < params.length; i++) {
				newparams[i] = params[i];
			}
		}

		newparams[i++] = new Integer((Integer.valueOf(pageNum) - 1) * 10);
		newparams[i] = new Integer(pageSize);

		BaseDAO.LOG.info(sql + this.toString(newparams));

		return this.queryRowMap(sql, newparams);
	}

	/**
	 * 带条件带数据类型分页查询
	 * 
	 * @param baseSql
	 *            基础SQL
	 * @param params
	 *            查询条件
	 * @param types
	 *            数据类型
	 * @param rm
	 *            存放对象
	 * @param begin
	 *            开始行数
	 * @param length
	 *            最大取出行数
	 * @return
	 */
	protected List<T> pageQuery(String baseSql, Object[] params, int types[], RowMapper rm, int begin, int length) {
		String sql = "select * from (select rownum as my_rownum,table_a.* from(" + baseSql
				+ ") table_a where rownum < ?) where my_rownum >= ?";
		Object[] newparams = new Object[params.length + 2];
		int[] newtypes = new int[types.length + 2];
		int i = 0;
		int j = 0;
		if (null != params) {
			newparams = new Object[params.length + 2];
			for (i = 0; i < params.length; i++) {
				newparams[i] = params[i];
			}
			newparams[i++] = begin + length;
			newparams[i] = begin;
		}
		if (null != types) {
			newtypes = new int[types.length + 2];
			for (j = 0; j < types.length; j++) {
				newtypes[j] = types[j];
			}
			newtypes[j++] = Types.INTEGER;
			newtypes[j] = Types.INTEGER;
		}
		BaseDAO.LOG.info(sql + this.toString(newparams));
		return this.query(sql, newparams, newtypes, rm);
	}

	protected List<MapBean> pageQueryRowMap(String baseSql, Object[] params, int types[], int begin, int length) {
		String sql = "select * from (select rownum as my_rownum,table_a.* from(" + baseSql
				+ ") table_a where rownum < ?) where my_rownum >= ?";
		Object[] newparams = new Object[params.length + 2];
		int[] newtypes = new int[types.length + 2];
		int i = 0;
		int j = 0;
		if (null != params) {
			newparams = new Object[params.length + 2];
			for (i = 0; i < params.length; i++) {
				newparams[i] = params[i];
			}
			newparams[i++] = begin + length;
			newparams[i] = begin;
		}
		if (null != types) {
			newtypes = new int[types.length + 2];
			for (j = 0; j < types.length; j++) {
				newtypes[j] = types[j];
			}
			newtypes[j++] = Types.INTEGER;
			newtypes[j] = Types.INTEGER;
		}
		BaseDAO.LOG.info(sql + this.toString(newparams));
		return this.queryRowMap(sql, newparams, newtypes);
	}

	/**
	 * 不带数据类型基础查询SQL
	 * 
	 * @param sql
	 *            SQL代码
	 * @param params
	 *            参数
	 * @param rm
	 *            存放对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<T> query(String sql, Object[] params, RowMapper rm) {
		try {
			BaseDAO.LOG.info(sql + this.toString(params));

			long begin_time = System.currentTimeMillis();

			List<T> objList = (List<T>) this.jdbcTemplate.query(sql, params, new RowMapperResultSetExtractor(rm));

			long end_time = System.currentTimeMillis();

			if ((end_time - begin_time) >= BaseDAO.SQL_TIME_WARNING_THRESHOLD) {
				BaseDAO.LOG.info("运行时间警告(" + (end_time - begin_time) + ")," + sql);
			}

			return objList;
		} catch (Exception e) {
			// e.printStackTrace();
			LOG.info(e.getStackTrace());
			LOG.info(e.getMessage());
			return null;
		}
	}

	/**
	 * 不需要数据映射的查询
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	protected List<MapBean> queryRowMap(String sql, Object[] params) {
		try {
			BaseDAO.LOG.info(sql + this.toString(params));

			long begin_time = System.currentTimeMillis();

			List<MapBean> objList = (List<MapBean>) this.jdbcTemplate.query(sql, params, new MapRow());

			long end_time = System.currentTimeMillis();

			if ((end_time - begin_time) >= BaseDAO.SQL_TIME_WARNING_THRESHOLD) {
				BaseDAO.LOG.info("运行时间警告(" + (end_time - begin_time) + ")," + sql);
			}

			return objList;
		} catch (Exception e) {
			// e.printStackTrace();
			LOG.info(e.getStackTrace());
			LOG.info(e.getMessage());
			return null;
		}
	}

	/**
	 * 带数据类型基础查询SQL
	 * 
	 * @param sql
	 *            SQL代码
	 * @param params
	 *            参数
	 * @param rm
	 *            存放对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<T> query(String sql, Object[] params, int types[], RowMapper rm) {
		try {
			BaseDAO.LOG.info(sql + this.toString(params));

			long begin_time = System.currentTimeMillis();

			List<T> objList = (List<T>) this.jdbcTemplate.query(sql, params, types,
					new RowMapperResultSetExtractor(rm));

			long end_time = System.currentTimeMillis();

			if ((end_time - begin_time) >= BaseDAO.SQL_TIME_WARNING_THRESHOLD) {
				BaseDAO.LOG.info("运行时间警告(" + (end_time - begin_time) + ")," + sql);
			}

			return objList;
		} catch (Exception e) {
			LOG.info(e.getStackTrace());
			LOG.info(e.getMessage());
			return null;
		}
	}

	/**
	 * 不需要条件带数据类型的查询
	 * 
	 * @param sql
	 * @param params
	 * @param types
	 * @return
	 */
	protected List<MapBean> queryRowMap(String sql, Object[] params, int types[]) {
		try {
			BaseDAO.LOG.info(sql + this.toString(params));

			long begin_time = System.currentTimeMillis();

			List<MapBean> objList = (List<MapBean>) this.jdbcTemplate.query(sql, params, types, new MapRow());

			long end_time = System.currentTimeMillis();

			if ((end_time - begin_time) >= BaseDAO.SQL_TIME_WARNING_THRESHOLD) {
				BaseDAO.LOG.info("运行时间警告(" + (end_time - begin_time) + ")," + sql);
			}

			return objList;
		} catch (Exception e) {
			LOG.info(e.getStackTrace());
			LOG.info(e.getMessage());
			return null;
		}
	}

	/**
	 * 不带条件数量查询
	 * 
	 * @param sql
	 * @return
	 */
	protected int queryForCount(String sql) {
		String newSql = "SELECT COUNT(*) FROM (" + sql + ") A";
		BaseDAO.LOG.info(newSql);
		return this.jdbcTemplate.queryForObject(newSql, null, Integer.class);
	}

	/**
	 * 带条件数量查询
	 * 
	 * @param sql
	 *            运行SQL
	 * @param params
	 *            条件
	 * @return
	 */
	protected int queryForCount(String sql, Object[] params) {
		String newSql = "SELECT COUNT(*) FROM (" + sql + ") A";
		BaseDAO.LOG.info(newSql + this.toString(params));
		return this.jdbcTemplate.queryForObject(newSql, params, Integer.class);
	}

	/**
	 * 带条件查询总数量
	 * 
	 * @param sql
	 *            运行SQL
	 * @param params
	 *            条件
	 * @param types
	 *            数据类型
	 * @return
	 */
	protected int queryForCount(String sql, Object[] params, int types[]) {

		BaseDAO.LOG.info(sql + this.toString(params));

		return this.jdbcTemplate.queryForObject(sql, params, types, Integer.class);
	}

	/**
	 * 查询公共对象
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	protected Object queryForObject(String sql, Object[] params) {

		BaseDAO.LOG.info(sql + this.toString(params));

		return this.jdbcTemplate.queryForObject(sql, params, String.class);
	}

	/**
	 * 带条件更新数据
	 * 
	 * @param sql
	 *            基础SQL
	 * @param params
	 *            条件
	 * @param types
	 *            数据类型
	 * @return
	 */
	protected int update(String sql, Object[] params, int[] types) {

		BaseDAO.LOG.info(sql + this.toString(params));

		long begin_time = System.currentTimeMillis();

		int result = this.jdbcTemplate.update(sql, params, types);

		long end_time = System.currentTimeMillis();

		if ((end_time - begin_time) >= BaseDAO.SQL_TIME_WARNING_THRESHOLD) {
			BaseDAO.LOG.info("运行时间警告(" + (end_time - begin_time) + ")," + sql);
		}

		return result;
	}

	/**
	 * 批量更新
	 * 
	 * @param sql
	 *            更新SQL
	 * @param setter
	 *            批量更新的数据
	 * @return
	 */
	protected int[] batchUpdate(String sql, BatchPreparedStatementSetter setter) {

		BaseDAO.LOG.info(sql);

		long begin_time = System.currentTimeMillis();

		int[] result = this.jdbcTemplate.batchUpdate(sql, setter);

		long end_time = System.currentTimeMillis();

		if ((end_time - begin_time) >= BaseDAO.SQL_TIME_WARNING_THRESHOLD) {
			BaseDAO.LOG.info("运行时间警告(" + (end_time - begin_time) + ")," + sql);
		}

		return result;
	}

	/**
	 * 删除一个对象
	 * 
	 * @author chaiyi
	 * @param sql
	 *            执行的SQL
	 */
	protected void delete(String sql, Object[] params) {

		BaseDAO.LOG.info(sql);

		long begin_time = System.currentTimeMillis();

		this.jdbcTemplate.update(sql, params);

		long end_time = System.currentTimeMillis();

		if ((end_time - begin_time) >= BaseDAO.SQL_TIME_WARNING_THRESHOLD) {
			BaseDAO.LOG.info("运行时间警告(" + (end_time - begin_time) + ")," + sql);
		}
	}

	/**
	 * 获得参数列表的字符串
	 * 
	 * @param params
	 *            参数列表
	 * @return
	 */
	public String toString(Object[] params) {

		if (null == params) {
			return "{[]}";
		}

		StringBuffer buff = new StringBuffer("{");

		for (int i = 0; i < params.length; i++) {
			Object obj = params[i];
			if (null != obj) {
				buff.append("[").append(this.getString(obj)).append("]");
			}
			if (i != (params.length - 1)) {
				buff.append(",");
			}
		}

		buff.append("}");

		return buff.toString();
	}

	/**
	 * 将对象转换为字符串
	 * 
	 * @param obj
	 *            对象
	 * @return
	 */
	public String getString(Object obj) {

		if (null == obj) {
			return "null";
		}

		if (obj instanceof Date) {
			Date date = (Date) obj;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
			return sdf.format(date);
		}

		if (obj instanceof String) {
			String str = (String) obj;
			return str;
		}

		return obj.toString();
	}

	/**
	 * 功能:取得特定订单号 年月日时分秒 输入：sequenceName 数据库表对应的sequence的名字 输出：订单号
	 * 说明：订单号生成规则:时间(如20060412152403)+四位流水号
	 * 时间以数据库时间为准，如果以应用服务器时间为准，如果服务器时间不一致，可造成订单号重复 现在的实现很弱智，需要改进
	 */
	@SuppressWarnings("unchecked")
	public final String getNextId(String sequenceName) {

		StringBuffer buf = new StringBuffer();
		buf.append("select to_char(sysdate,'yyyymmddhh24MISS')||");
		buf.append(sequenceName);
		buf.append(".nextval as id from dual");

		try {
			String id = (String) this.jdbcTemplate.query(buf.toString(), new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) {
					String str = null;
					try {
						if (rs.next()) {
							str = rs.getString("id");
						}
					} catch (SQLException e) {
					}
					return str;
				}
			});
			return id;

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 功能:取得特定流水编号 年月日时分 输入：sequenceName 数据库表对应的sequence的名字 输出：退款流水号
	 * 说明：订单号生成规则:时间(如200604121524)+四位流水号
	 * 时间以数据库时间为准，如果以应用服务器时间为准，如果服务器时间不一致，可造成订单号重复 现在的实现很弱智，需要改进
	 */
	@SuppressWarnings("unchecked")
	public final String getRefundNO(String sequenceName) {

		StringBuffer buf = new StringBuffer();
		buf.append("select to_char(sysdate,'yyyymmddhh24MI')||");
		buf.append(sequenceName);
		buf.append(".nextval as id from dual");

		try {
			String id = (String) this.jdbcTemplate.query(buf.toString(), new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) {
					String str = null;
					try {
						if (rs.next()) {
							str = rs.getString("id");
						}
					} catch (SQLException e) {
					}
					return str;
				}
			});
			return id;

		} catch (Exception e) {
			// e.printStackTrace();
			LOG.info(e.getStackTrace());
			LOG.info(e.getMessage());
			return null;
		}

	}

	/**
	 * 功能：从数据集得到某列的整型值 输入：数据集、列名 输出：字符串 备注：出现异常返回空
	 */
	public boolean hasColumn(ResultSet rs, String clmName) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		for (int i = 1; i < (md.getColumnCount() + 1); i++) {

			// if (md.getColumnName(i).equalsIgnoreCase(clmName)) {
			// return true;
			// }
			if (md.getColumnLabel(i).equalsIgnoreCase(clmName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 功能：从数据集得到某列的整型值 输入：数据集、列名 输出：字符串 备注：出现异常返回空
	 */
	public boolean hasColumn(HashMap<T, T> rs, String clmName) {
		if (rs.containsKey(clmName.toUpperCase())) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否确实存在
	 * 
	 * @param str
	 * @return
	 */
	public boolean isNotNull(String str) {
		if (str != null && str.trim().length() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	/**
	 * @param jdbcTemplate
	 *            the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 功能:判断sql语句是否执行成功(判断insert delete update语句的返回结果) 2013-8-19 by ywzhang
	 * 
	 * @param batchUpdate返回的结果或update返回的结果
	 * @return 执行成功返回true
	 */
	public final boolean isUpdateSuccess(int[] res) {
		if ((res == null) || (res.length == 0)) {
			return false;
		}
		// 判断SQL影响的行数
		for (int k = 0; k < res.length; k++) {
			if ((res[k] == 0) || (res[k] == Statement.EXECUTE_FAILED)) {
				return false;
			}
		}
		return true;
	}

	public int getPageCount(int dataCount) {
		int pageCount = 0;
		if (dataCount % 10 != 0) {
			pageCount = dataCount / 10 + 1;
		} else {
			pageCount = dataCount / 10;
		}
		return pageCount;
	}
}
