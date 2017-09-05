package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.utils.DBUtil;

public class Test3 {

	public static void main(String[] args) throws Exception {
		ArrayList<String[]> listE = readExcel("D://test03.xls");
		// for (int i = 0; i < listE.size(); i++) {
		// System.out.println(listE.get(i)[0]);
		// }

		ArrayList<String> listD = getAnimation();
		// for (int i = 0; i < listD.size(); i++) {
		// System.out.println(listD.get(i));
		// }

		for (int i = 0; i < listE.size(); i++) {
			if (!listD.contains(listE.get(i)[0])) {
				System.out.println(listE.get(i)[0]);
			}
		}

	}

	private static ArrayList<String> getAnimation() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		Connection conn = DBUtil.getConnection();
		String sql = "select ANIMATION_NAME from animation_information ";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int col = rs.getMetaData().getColumnCount() + 1;
			// System.out.println("============================");
			// 获取列名
			ResultSetMetaData metaData = rs.getMetaData();

			while (rs.next()) {

				for (int i = 1; i < col; i++) {
					String columnName = metaData.getColumnName(i);
					// System.out.print(columnName + "\t");
					// System.out.print(rs.getString(i) + "\t");

					if ((i == 2) && (rs.getString(i).length() < 8)) {
						// System.out.print("\t");
					}

					if (columnName.equals("ANIMATION_NAME")) {
						list.add(rs.getString(i));
					}
				}
				// System.out.println("");
			}
			// System.out.println("============================");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection(conn);
		return list;
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
