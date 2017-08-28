package com.basedao.dbtool;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class DBTools {

	@Autowired
	public JdbcTemplate jdbcTemplate;

	private static Logger logger = Logger.getLogger(DBTools.class);

	/**
	 * 指定查询表
	 * 
	 * @param tableName
	 * @return
	 */
	public DBQuery table(String tableName) {
		DBQuery dbQuery = new DBQuery(this.jdbcTemplate, tableName);
		return dbQuery;
	}

	/**
	 * 带参数的查询方法
	 * 
	 * @param sql
	 * @param pList
	 * @return
	 * @throws SQLException
	 */
	public List<MapBean> query(String sql, List<Param> pList) throws SQLException {
		DBQuery dbQuery = new DBQuery(this.jdbcTemplate);
		List<MapBean> dbBeanList = dbQuery.query(sql, pList);
		// logger.info(sql);
		return dbBeanList;
	}

	// public List<MapBean> queryPagination(String sql, List<Param> pList, int
	// pageNum) throws SQLException {
	// DBQuery dbQuery = new DBQuery(this.jdbcTemplate);
	// List<Param> p = new ArrayList<Param>();
	// if (pList != null) {
	// p.addAll(pList);
	// }
	// int begin = (Integer.valueOf(pageNum) - 1) * 10;
	// p.add(new Param(begin, ColumnType.INTEGER));
	// p.add(new Param(Common.pageRange, ColumnType.INTEGER));
	// List<MapBean> dbBeanList = dbQuery.query("SELECT * FROM (" + sql + ") A
	// limit ?,?", p);
	// // logger.info("SELECT * FROM (" + sql + ") A limit ?,?");
	// return dbBeanList;
	// }

	public int getDataCount(String sql, List<Param> pList) throws SQLException {
		DBQuery dbQuery = new DBQuery(this.jdbcTemplate);
		String pageCount_sql = "SELECT  COUNT(*) AS COUNT FROM (" + sql + ") A";
		List<MapBean> dbBeanList = dbQuery.query(pageCount_sql, pList);
		// logger.info(pageCount_sql);
		return Integer.valueOf(dbBeanList.get(0).getData().get("COUNT"));
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

	public class DBQuery {
		// spring jdbc
		private JdbcTemplate jdbcTemplate;
		// 表名
		private String table = null;
		// 查询语句的表达式
		private String whereSql = null;
		// 查询语句的值
		private List<Param> whereParameterList = null;
		// 查询字段
		private String column = null;
		// 排序
		private String order = null;
		// Limit
		private String limit = null;

		private DBQuery() {
		}

		private DBQuery(JdbcTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
		}

		private DBQuery(JdbcTemplate jdbcTemplate, String table) {
			this.jdbcTemplate = jdbcTemplate;
			this.table = table;
		}

		public DBQuery column(String column) {
			this.column = column;
			return this;
		}

		public DBQuery where(String whereSql, List<Param> pList) {
			this.whereSql = whereSql;
			this.whereParameterList = pList;
			return this;
		}

		public DBQuery order(String order) {
			this.order = order;
			return this;
		}

		public DBQuery limit(int n) {
			this.limit = " LIMIT " + n;
			return this;
		}

		public DBQuery limit(int m, int n) {
			this.limit = " LIMIT " + m + ", " + n;
			return this;
		}

		/**
		 * 查询
		 * 
		 * @return
		 * @throws SQLException
		 */
		public List<MapBean> select() throws SQLException {
			StringBuffer sql = new StringBuffer();
			// 拼 sql语句
			sql.append("SELECT ");
			if (column != null && column.length() > 0) {
				sql.append(column);
			} else {
				sql.append("*");
			}
			// 表名
			sql.append(" FROM " + table);
			// 条件
			if (whereSql != null && whereSql.length() > 0) {
				sql.append(" WHERE " + whereSql);
			}
			// 排序
			if (order != null && order != "") {
				sql.append(" ORDER BY " + order);
			}
			// Limit
			if (limit != null) {
				sql.append(limit);
			}
			Object[] params = null;
			if (whereParameterList != null && whereParameterList.size() > 0) {
				params = new Object[whereParameterList.size()];//
				for (int i = 0, lenI = whereParameterList.size(); i < lenI; i++) {
					params[i] = whereParameterList.get(i).getValue();
				}
			}
			List<MapBean> dbBeanList = new ArrayList<MapBean>();
			dbBeanList = this.jdbcTemplate.query(sql.toString(), params, new resultRowMapper());
			logger.info(sql.toString() + " " + toString(params));
			return dbBeanList;
		}

		/**
		 * 添加
		 * 
		 * @param e
		 */
		public int add(List<Column> cList) {
			int[] types = new int[cList.size()];//
			Object[] params = new Object[cList.size()];//
			StringBuffer sql = new StringBuffer();
			int result = 0;
			if (cList != null && cList.size() > 0) {
				sql.append("INSERT INTO ");
				sql.append(this.table);
				sql.append(" (");
				int lenI = cList.size();
				for (int i = 0; i < lenI; i++) {
					if (i == 0) {
						sql.append(cList.get(i).getColumn());
					} else {
						sql.append("," + cList.get(i).getColumn());
					}

					types[i] = getColumnType(cList.get(i).getType());// 字段类型
					params[i] = cList.get(i).getValue();// 字段值
				}
				sql.append(") values (");
				for (int i = 0; i < lenI; i++) {
					if (i == 0) {
						sql.append("?");
					} else {
						sql.append(", ?");
					}
				}
				sql.append(")");
				result = this.jdbcTemplate.update(sql.toString(), params, types);
				logger.info(sql.toString() + " " + toString(params));
			}
			return result;
		}

		/**
		 * 修改
		 * 
		 * @param ele
		 * @return
		 */
		public int update(List<Column> cList) {
			StringBuffer sql = new StringBuffer();
			int result = 0;
			if (cList != null && cList.size() > 0) {
				sql.append("UPDATE ");
				sql.append(this.table);
				sql.append(" SET ");
				for (int i = 0, lenI = cList.size(); i < lenI; i++) {
					if (i == 0) {
						sql.append(cList.get(i).getColumn() + " = ?");
					} else {
						sql.append("," + cList.get(i).getColumn() + " = ?");
					}
				}
				int[] types = null;//
				Object[] params = null;//
				// 条件
				if (whereSql != null && whereSql.length() > 0) {
					sql.append(" WHERE " + whereSql);
					int updateEleLen = cList.size();
					int whereEleLenLen = whereParameterList.size();
					int lenght = updateEleLen + whereEleLenLen;
					params = new Object[lenght];
					types = new int[lenght];
					// 加入要修改的参数
					for (int i = 0; i < updateEleLen; i++) {
						params[i] = cList.get(i).getValue();
						types[i] = getColumnType(cList.get(i).getType());
					}
					// 加入条件参数
					for (int i = updateEleLen; i < lenght; i++) {
						params[i] = whereParameterList.get(i - updateEleLen).getValue();
						types[i] = getColumnType(whereParameterList.get(i - updateEleLen).getType());
					}
				} else {
					params = new Object[cList.size()];
					types = new int[cList.size()];
					// 加入要修改的参数
					for (int i = 0, lenI = cList.size(); i < lenI; i++) {
						params[i] = cList.get(i).getValue();
						types[i] = getColumnType(cList.get(i).getType());
					}
				}
				result = this.jdbcTemplate.update(sql.toString(), params, types);
				logger.info(sql.toString() + " " + toString(params));
			}
			return result;
		}

		/**
		 * 删除
		 * 
		 * @param ele
		 * @return
		 */
		public int delete() {
			StringBuffer sql = new StringBuffer();
			int result = 0;
			sql.append("DELETE FROM ");
			sql.append(this.table);
			int[] types = null;//
			Object[] params = null;//
			// 条件
			if (whereSql != null && whereSql.length() > 0) {
				sql.append(" WHERE " + whereSql);
				params = new Object[whereParameterList.size()];
				types = new int[whereParameterList.size()];
				for (int i = 0, lenI = whereParameterList.size(); i < lenI; i++) {
					params[i] = whereParameterList.get(i).getValue();
					types[i] = getColumnType(whereParameterList.get(i).getType());
				}
			}
			result = this.jdbcTemplate.update(sql.toString(), params, types);
			logger.info(sql.toString() + " " + toString(params));
			return result;
		}

		/**
		 * 带条件的查询
		 * 
		 * @param sql
		 * @param pList
		 * @return
		 * @throws SQLException
		 */
		private List<MapBean> query(String sql, List<Param> pList) throws SQLException {
			Object[] params = null;
			if (pList != null && pList.size() > 0) {
				params = new Object[pList.size()];
				for (int i = 0, lenI = pList.size(); i < lenI; i++) {
					params[i] = pList.get(i).getValue();
				}
			}
			List<MapBean> dbBeanList = new ArrayList<MapBean>();
			dbBeanList = this.jdbcTemplate.query(sql.toString(), params, new resultRowMapper());
			logger.info(sql.toString() + " " + toString(params));
			return dbBeanList;
		}

		// 数据映射
		public class resultRowMapper implements RowMapper<MapBean> {
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

		private int getColumnType(ColumnType columnType) {
			int result = 0;
			switch (columnType) {
			case VARCHAR:
				result = 12;
				break;
			case TIMESTAMP:
				result = 93;
				break;
			case INTEGER:
				result = 4;
				break;
			case FLOAT:
				result = 6;
				break;
			case DOUBLE:
				result = 8;
				break;
			case DATE:
				result = 91;
				break;
			case TIME:
				result = 92;
				break;
			case BLOB:
				result = 2004;
				break;
			case CLOB:
				result = 2005;
				break;
			case BIT:
				result = -7;
				break;
			case TINYINT:
				result = -6;
				break;
			case SMALLINT:
				result = 5;
				break;
			case BIGINT:
				result = -5;
				break;
			case REAL:
				result = 7;
				break;
			case NUMERIC:
				result = 2;
				break;
			case DECIMAL:
				result = 3;
				break;
			case CHAR:
				result = 1;
				break;
			case LONGVARCHAR:
				result = -1;
				break;
			case BINARY:
				result = -2;
				break;
			case VARBINARY:
				result = -3;
				break;
			case LONGVARBINARY:
				result = -4;
				break;
			case NULL:
				result = 0;
				break;
			case OTHER:
				result = 1111;
				break;
			case JAVA_OBJECT:
				result = 2000;
				break;
			case DISTINCT:
				result = 2001;
				break;
			case STRUCT:
				result = 2002;
				break;
			case ARRAY:
				result = 2003;
				break;
			case REF:
				result = 2006;
				break;
			case DATALINK:
				result = 70;
				break;
			case BOOLEAN:
				result = 16;
				break;
			case ROWID:
				result = -8;
				break;
			case NCHAR:
				result = -15;
				break;
			case NVARCHAR:
				result = -9;
				break;
			case LONGNVARCHAR:
				result = -16;
				break;
			case NCLOB:
				result = 2011;
				break;
			case SQLXML:
				result = 2009;
				break;
			}
			return result;
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
	}

}
