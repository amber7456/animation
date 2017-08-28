/**
 * @(#)BaseDAO.java  
 *       
 * System：MSKY
 * Version：1.0
 *  
 * Copyright (c)  WWW.TRAVELSKY.NET
 * All rights reserved.
 * 
 * Author：ASA
 * Create_date：2015-3-12
 * Description：
 * 
 * Modifier：
 * Modify_date：
 * Modify_reson：
 *
*/
package com.utils;

import org.apache.log4j.Logger;
 
public class Log4jFactory {
	/**
	 * 获得Log4j日志类
	 * @param clazz	class, like MSKYLog4jFactory.class
	 * @return
	 */
	public static Logger getLogger(Class<?> clazz){
		return Logger.getLogger(clazz);
	}
}
