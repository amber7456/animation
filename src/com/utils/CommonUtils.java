package com.utils;

import java.util.Date;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CommonUtils {

	/** 验证码图片样 */
	public static final String VALIDATE_CODE_FONTNAME = "Comic Sans MS"; // 验证码fontname
	public static final String VALIDATE_CODE_FONTSIZE = "20"; // 验证码字体大小
	public static final String VALIDATE_CODE_FONTSTYLE = "Font.PLAIN"; // 验证码字体样式
	public static final String VALIDATE_CODE_COMMONCODE = "0000"; // 通用验证码
	public static final String VALIDATE_CODE_COMMONCODE_SWITCH = "VALIDATE_CODE_COMMONCODE_SWITCH"; // 通用验证码开关配置名
	/** 语种begin */
	/** 简体中文语言类型标识 */
	public static final String LANGUAGE_TYPE_ZH_CN = "zh_CN";
	/** 繁体中文语言类型标识 */
	public static final String LANGUAGE_TYPE_ZH_TW = "zh_TW";
	/** 英文语言类型标识 */
	public static final String LANGUAGE_TYPE_EN_US = "en_US";

	private static final String APP_BUNDLE_NAMW = "ApplicationResources";

	public static final String pageRange = "10";

	public static String getNowStr() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	public static String getNowStr(String formatType) {
		SimpleDateFormat format = new SimpleDateFormat(formatType);
		return format.format(new Date());
	}

	// 方法二：通过类加载目录getClassLoader()加载属性文件
	public static String getValueByKey(String key) {
		String result = "";

		// 方法二：通过类加载目录getClassLoader()加载属性文件
		InputStream in = CommonUtils.class.getClassLoader().getResourceAsStream("common.properties");
		// InputStream in =
		// this.getClass().getClassLoader().getResourceAsStream("mailServer.properties");

		// 注：Object.class.getResourceAsStream在action中调用报错，在普通java工程中可用
		// InputStream in =
		// Object.class.getResourceAsStream("/mailServer.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
			result = prop.getProperty(key).trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 下载excel文件
	public static boolean downLoadFile(HttpServletRequest request, HttpServletResponse response, String fileName,
			File file) throws Exception {
		// 设置输出的格式
		response.reset();
		// 根据浏览器设置下载文件名的编码
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
			// fileName = URLEncoder.encode(fileName, "UTF-8");
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		} else {
			// fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		}
		response.setContentType("application/xls;charset=GBK");
		// 设置下载文件头
		response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
		response.setHeader("Connection", "close");
		// 设置文件长度
		int fileLeng = Integer.parseInt(file.length() + "");
		response.setContentLength(fileLeng);

		byte[] buffer = new byte[4096];// 缓冲区
		BufferedOutputStream output = null;
		BufferedInputStream input = null;
		try {
			input = new BufferedInputStream(new FileInputStream(file));
			output = new BufferedOutputStream(response.getOutputStream());
			int n = -1;
			// 遍历，开始下载
			while ((n = input.read(buffer, 0, 4096)) > -1) {
				output.write(buffer, 0, n);
			}
			output.flush(); // 不可少
			response.flushBuffer();// 不可少
		} catch (Exception e) {
			// 异常捕捉
			e.printStackTrace();
		} finally {
			// 关闭流，不可少
			if (input != null)
				input.close();
			if (output != null)
				output.close();
			file.delete();
		}
		return false;
	}

	/**
	 * 使用ArrayList处理，如果是自己定义的类，则要合理重写equals方法
	 * 
	 * @param a
	 * @return
	 */
	public static <T> int getMostFrequentByArrayList(T a[]) {
		if (a == null || a.length == 0) {
			return 0;
		}
		int size = a.length;
		int result = 0;
		// 持有所有集合的集合。指定容量以免扩容带来的性能消耗，不过可能浪费空间
		ArrayList<ArrayList<T>> severalTempList = new ArrayList<ArrayList<T>>(size);

		for (int i = 0; i < size; i++) {
			boolean isAdd = false;
			T t = a[i];
			// 遍历severalTempList每个ArrayList，找到t所属的则添加上去
			for (int j = 0; j < severalTempList.size(); j++) {
				ArrayList<T> singleTemps = severalTempList.get(j);
				if (singleTemps != null) {
					if (t.equals(singleTemps.get(0))) {
						singleTemps.add(t);
						isAdd = true;
					}
				}
			}
			// 找不到t所属的则创建新的ArrayList添加到severalTempList，并将t添加到新的ArrayList
			if (!isAdd) {
				ArrayList<T> singleTemps = new ArrayList<T>();
				singleTemps.add(t);
				severalTempList.add(singleTemps);
			}
		}

		// 经过拆分后长度最大的集合
		ArrayList<T> largestList = severalTempList.get(0);
		// 从索引为1开始就可以了
		for (int i = 1; i < severalTempList.size(); i++) {
			// 通过遍历找到元素最多的集合
			if (severalTempList.get(i).size() > largestList.size()) {
				largestList = severalTempList.get(i);
			}

			result = largestList.size();
		}

		return result;

	}

	/**
	 * @author 把主机格式的字符串20APR转换成04-20
	 */
	public static String changeHostDateToCommonFormat(String hostDate) {

		hostDate = hostDate.trim();
		if (hostDate.length() < 5) {
			return "";
		}
		hostDate = hostDate.toUpperCase();
		String result = "";
		String[] sMonName = new String[12];
		String MonName = "";
		String month = "";
		sMonName[0] = "JAN";
		sMonName[1] = "FEB";
		sMonName[2] = "MAR";
		sMonName[3] = "APR";
		sMonName[4] = "MAY";
		sMonName[5] = "JUN";
		sMonName[6] = "JUL";
		sMonName[7] = "AUG";
		sMonName[8] = "SEP";
		sMonName[9] = "OCT";
		sMonName[10] = "NOV";
		sMonName[11] = "DEC";

		MonName = hostDate.substring(2, 5);

		for (int i = 0; i < 12; i++) {
			if (MonName.equals(sMonName[i])) {
				if (i >= 9) {
					month = Integer.toString(i + 1);

				} else {
					month = "0" + Integer.toString(i + 1);
				}
			}
		}

		if (hostDate.length() < 7) {
			result = month + "-" + hostDate.substring(0, 2);
		}
		return result;
	}

	// 时间格式
	public static String changeTimeFormat(String str_input, String format) {
		String time = "";
		if (str_input != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmm");
			sdf.setLenient(false);
			try {
				time = sdf.format(sdf2.parse("20090909" + str_input));
			} catch (Exception e) {
				return time;
			}
			return time;
		}
		return time;
	}

	/**
	 * 
	 * @param key
	 *            key
	 * @param lang
	 *            语言 中文：zh、英文：en、繁体：chn
	 * @return
	 */
	public static String getAppString(String resname, String key, String lang) {
		Locale locale = null;
		if ("zh".equalsIgnoreCase(lang)) {
			locale = new Locale("zh", "CN");
		} else if ("tw".equalsIgnoreCase(lang)) {
			locale = new Locale("zh", "TW");
		} else if ("en".equalsIgnoreCase(lang)) {
			locale = new Locale("en", "US");
		} else {
			locale = new Locale("zh", "CN");
		}
		if (resname == null || "".equals(resname)) {
			resname = APP_BUNDLE_NAMW;
		}
		ResourceBundle localResourceCN = ResourceBundle.getBundle(resname, locale);
		try {
			return localResourceCN.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static String getFormateDate(Date date, String lang) {
		String formateDate = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		String shortNameOfMonth = "";
		int year = calendar.get(Calendar.YEAR);

		if (lang.length() > 0 && (lang.equals("zh") || lang.equals("tw"))) {
			formateDate = "" + year + "年" + month + "月" + dayOfMonth + "日";
		} else {
			switch (month) {
			case 1:
				shortNameOfMonth = "Jan";
				break;
			case 2:
				shortNameOfMonth = "Feb";
				break;
			case 3:
				shortNameOfMonth = "Mar";
				break;
			case 4:
				shortNameOfMonth = "Apr";
				break;
			case 5:
				shortNameOfMonth = "May";
				break;
			case 6:
				shortNameOfMonth = "June";
				break;
			case 7:
				shortNameOfMonth = "July";
				break;
			case 8:
				shortNameOfMonth = "Aug";
				break;
			case 9:
				shortNameOfMonth = "Sept";
				break;
			case 10:
				shortNameOfMonth = "Oct";
				break;
			case 11:
				shortNameOfMonth = "Nov";
				break;
			case 12:
				shortNameOfMonth = "Dec";
				break;
			}

			if (dayOfMonth > 10) {
				formateDate = "" + dayOfMonth + shortNameOfMonth + year;
			} else {
				formateDate = "0" + dayOfMonth + shortNameOfMonth + year;
			}

		}
		return formateDate;
	}

	public static String getFormateDate(String date) {
		String formateDate = "";
		String dayOfMonth = date.substring(0, 2);
		String month = date.substring(2);
		int monthIndex = 0;

		String[] shortNameOfMonth = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV",
				"DEC" };
		for (int i = 0; i < shortNameOfMonth.length; i++) {
			if (shortNameOfMonth[i].equals(month)) {
				monthIndex = i + 1;
				break;
			}
		}
		if (dayOfMonth.subSequence(0, 1).equals("0")) {
			formateDate = monthIndex + "月" + dayOfMonth.substring(1) + "日";
		} else {
			formateDate = monthIndex + "月" + dayOfMonth + "日";
		}
		return formateDate;
	}

	// 转成unicode编码
	public static String toUnicode(String theString, boolean escapeSpace) {
		int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
		}
		StringBuffer outBuffer = new StringBuffer(bufLen);

		for (int x = 0; x < len; x++) {
			char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if ((aChar > 61) && (aChar < 127)) {
				if (aChar == '\\') {
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch (aChar) {
			case ' ':
				if (x == 0 || escapeSpace)
					outBuffer.append('\\');
				outBuffer.append(' ');
				break;
			case '\t':
				outBuffer.append('\\');
				outBuffer.append('t');
				break;
			case '\n':
				outBuffer.append('\\');
				outBuffer.append('n');
				break;
			case '\r':
				outBuffer.append('\\');
				outBuffer.append('r');
				break;
			case '\f':
				outBuffer.append('\\');
				outBuffer.append('f');
				break;
			case '=': // Fall through
			case ':': // Fall through
			case '#': // Fall through
			case '!':
				outBuffer.append('\\');
				outBuffer.append(aChar);
				break;
			default:
				if ((aChar < 0x0020) || (aChar > 0x007e)) {
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(toHex((aChar >> 12) & 0xF));
					outBuffer.append(toHex((aChar >> 8) & 0xF));
					outBuffer.append(toHex((aChar >> 4) & 0xF));
					outBuffer.append(toHex(aChar & 0xF));
				} else {
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}

	// 将unicode解码
	public static String fromUnicode(String str) {
		str = str == null ? "" : str;
		char[] in = str.toCharArray();
		int len = str.length();
		int off = 0;
		char[] convtBuf = new char[len];
		if (convtBuf.length < len) {
			int newLen = len * 2;
			if (newLen < 0) {
				newLen = Integer.MAX_VALUE;
			}
			convtBuf = new char[newLen];
		}
		char aChar;
		char[] out = convtBuf;
		int outLen = 0;
		int end = off + len;

		while (off < end) {
			aChar = in[off++];
			if (aChar == '\\') {
				aChar = in[off++];
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = in[off++];
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
						}
					}
					out[outLen++] = (char) value;
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					out[outLen++] = aChar;
				}
			} else {
				out[outLen++] = (char) aChar;
			}
		}
		return new String(out, 0, outLen);
	}

	private static char toHex(int nibble) {
		return hexDigit[(nibble & 0xF)];
	}

	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	public static boolean isToChange(String lang) {
		if (lang == null || lang.length() == 0) {
			return false;
		} else {
			if ("zh".equalsIgnoreCase(lang) || "en".equalsIgnoreCase(lang) || "tw".equalsIgnoreCase(lang)) {
				return false;
			} else {
				return true;
			}
		}
	}

	/***************************************************************************
	 * 生成长度为len的激活码
	 * 
	 * @param len
	 *            激活码长度
	 * @return
	 */
	public static String generateActivationCode(int len) {
		return generateRandomString(len);
	}

	/***************************************************************************
	 * 生成imei码
	 * 
	 * @return
	 */
	public static String generateIMEI() {
		return generateRandomString(15);
	}

	public static String generateSessionId() {
		return generateRandomString(15);
	}

	public static String generateRandomString(int len) {
		Random random = new Random(System.currentTimeMillis());
		String sRand = "";
		for (int i = 0; i < len; i++) {
			String rand = String.valueOf(random.nextInt(10));

			sRand += rand;

		}
		return sRand;
	}

	public static String genId(int len) {
		Random random = new Random(System.currentTimeMillis());
		String sRand = "";
		for (int i = 0; i < len; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}
		return sRand;
	}

}
