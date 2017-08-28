package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

//import jxl.Cell;
//import jxl.CellType;
//import jxl.DateCell;
//import jxl.Sheet;
//import jxl.Workbook;
//import jxl.WorkbookSettings;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.utils.CommonUtils;
import com.utils.DBUtil;

import org.apache.poi.ss.usermodel.DateUtil;

public class ExcelDao {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		String filePath = "D:/excel/0301.xlsx";
		// getExcel(excelFile, "UTF-8");
		ArrayList<String[]> list = readExcel(filePath);
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.get(i).length; j++) {
				System.out.print(list.get(i)[j] + "\t");
			}
			System.out.println();
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟
		// 开启数据库连接
		// 数据入库

		Connection conn = DBUtil.getConnection();

		int r = 0;

		String sql = "insert  into `animation_information`(`ANIMATION_ID`,`ANIMATION_NAME`,`ANIMATION_EPISODE`,`ANIMATION_BROADCAST_TIME`,"
				+ "`ANIMATION_TYPE`,`ANIMATION_SOURCE`,`ANIMATION_REMARK`, `HAVE_POSTER`,`LAST_UPDATE_TIME`,`HAVE_BD_RESOURCE`) values "
				+ " (?,?,?,?,?,?,?,?,?,?)";

		String sqlR = "insert  into `animation_resource`(`ANIMATION_ID`,`RESOURCE_TYPE`,`RESOURCE_FORMAT`,`RESOURCE_IMAGE_RESOLUTION`,"
				+ "`RESOURCE_SUB`,`RESOURCE_SUB_TYPE`,`RESOURCE_SUB_FORMAT`,`RESOURCE_TIME`,`RESOURCE_ADDRESS` ) "
				+ "values (?,?,?,?,?,?,?,?,? )"; // 9 个参数

		PreparedStatement pstmt = conn.prepareStatement(sql);
		PreparedStatement pstmtR = conn.prepareStatement(sqlR);

		for (int i = 0; i < list.size(); i++) {

			for (int j = 0; j < list.get(i).length; j++) {
				String id = CommonUtils.getNowStr("YYYYMMddHHmmssSSS");
				pstmt.setString(1, id);
				pstmt.setString(2, list.get(i)[1]);
				pstmt.setString(3, list.get(i)[3]);

				if (list.get(i)[2] != "") {
					String sDate = list.get(i)[2];

					java.util.Date date = sdf.parse(sDate);

					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int month = cal.get(Calendar.MONTH) + 1; // 注意月份是从0开始的,比如当前7月，获得的month为6
					if (month != 1 && month != 4 && month != 7 && month != 10) {
						pstmt.setString(5, "7");
					} else if (month == 1) {
						pstmt.setString(5, "1");
					} else if (month == 4) {
						pstmt.setString(5, "2");
					} else if (month == 7) {
						pstmt.setString(5, "3");
					} else if (month == 10) {
						pstmt.setString(5, "4");
					}
					pstmt.setString(4, list.get(i)[2]);
				} else { 
					pstmt.setString(4, CommonUtils.getNowStr());
				}
				pstmt.setString(6, "1");
				if (j == 5) {
					pstmt.setString(7, list.get(i)[4]);
				} else {
					pstmt.setString(7, "");
				}
				pstmt.setString(8, "NO");
				pstmt.setString(9, CommonUtils.getNowStr());
				pstmt.setString(10, "0");

				pstmtR.setString(1, id);
				pstmtR.setString(2, "TV");
				pstmtR.setString(3, "MP4");
				pstmtR.setString(4, "720P");
				pstmtR.setString(5, "YES");
				pstmtR.setString(6, "内嵌");
				pstmtR.setString(7, "NO");
				pstmtR.setString(8, list.get(i)[0]);
				pstmtR.setString(9, "ATN_03_瓦良格");

			}
			pstmtR.executeUpdate();
			r = pstmt.executeUpdate();

			//System.out.println("插入结果：" + r);

			try {
				Thread.sleep(100); // 1000 毫秒，也就是1秒.
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}

		pstmtR.close();
		pstmt.close();
		DBUtil.closeConnection(conn);

	}

	public static ArrayList<String[]> readExcel(String filePath) {

		ArrayList<String[]> list = new ArrayList<String[]>();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			// 同时支持Excel 2003、2007
			File excelFile = new File(filePath); // 创建文件对象
			FileInputStream is = new FileInputStream(excelFile); // 文件流
			Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel
															// 2003/2007/2010
															// 都是可以处理的
			int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
			// 遍历每个Sheet
			for (int s = 0; s < sheetCount; s++) {
				Sheet sheet = workbook.getSheetAt(s);
				int rowCount = sheet.getPhysicalNumberOfRows(); // 获取总行数
				// 遍历每一行
				for (int r = 0; r < rowCount; r++) {

					Row row = sheet.getRow(r);
					// int cellCount = row.getPhysicalNumberOfCells(); // 获取总列数
					int cellCount = row.getLastCellNum() + 1;
					String[] excelData = new String[cellCount];
					// 遍历每一列
					for (int c = 0; c < cellCount; c++) {
						Cell cell = row.getCell(c);
						if (cell != null) {

							int cellType = cell.getCellType();
							String cellValue = null;
							switch (cellType) {
							case Cell.CELL_TYPE_STRING: // 文本
								cellValue = cell.getStringCellValue().trim();
								break;
							case Cell.CELL_TYPE_NUMERIC: // 数字、日期
								if (DateUtil.isCellDateFormatted(cell)) {
									cellValue = fmt.format(cell.getDateCellValue()); // 日期型
								} else {
									cellValue = String.valueOf((int) cell.getNumericCellValue()); // 数字
								}
								break;
							case Cell.CELL_TYPE_BOOLEAN: // 布尔型
								cellValue = String.valueOf(cell.getBooleanCellValue());
								break;
							case Cell.CELL_TYPE_BLANK: // 空白
								cellValue = cell.getStringCellValue();
								break;
							case Cell.CELL_TYPE_ERROR: // 错误
								cellValue = "";
								break;
							case Cell.CELL_TYPE_FORMULA: // 公式
								cellValue = "";
								break;
							default:
								cellValue = "";
							}
							// System.out.print(cellValue + " ");
							excelData[c] = cellValue;
						} else {
							// System.out.print(" " + " ");
							excelData[c] = "";
						}

					}
					// System.out.println();
					list.add(excelData);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
