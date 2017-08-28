package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DBUtil {

	/**
	 * 日志输出
	 */
	private static final Logger LOG = Log4jFactory.getLogger(DBUtil.class);

	private static InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("config.properties");
	private static Properties prop = new Properties();

	public static Connection conn;

	// 连接数据库的方法
	public static Connection getConnection() throws Exception {
		try {
			prop.load(in);
			LOG.info("读取配置资源文件成功！");
		} catch (IOException e) {
			e.printStackTrace();
			LOG.info("读取配置资源文件异常！");
			throw new Exception(e.getMessage());
		}

		String url = prop.getProperty("jdbc.url");
		String user = prop.getProperty("jdbc.username");
		String password = prop.getProperty("jdbc.password");

		Connection conn = null;
		try {
			// 初始化驱动包
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			LOG.info("获取数据库连接成功！");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.info("获取数据库连接异常！");
			throw new Exception(e.getMessage());
		}
		return conn;
	}

	public static void closeConnection(Connection conn) throws Exception {
		try {
			if (conn != null)
				conn.close();
			LOG.info("关闭数据库连接成功！");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.info("关闭数据库连接异常！");
			throw new Exception(e.getMessage());
		}
	}

}
