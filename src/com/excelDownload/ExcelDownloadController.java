package com.excelDownload;

import java.io.File;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import java.awt.Color;

import jxl.format.Colour;
import jxl.write.WritableCellFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.basedao.dbtool.MapBean;
import com.bean.search.SearchBean;
import com.search.SearchService;
import com.utils.CommonUtils;

@Controller
public class ExcelDownloadController {

	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/excelDownloadByYear")
	public void excelDownloadByYear(HttpServletRequest request, HttpServletResponse response, Model model,
			SearchBean searchBean) throws Exception {
		List<MapBean> animationList = searchService.searchAnimationByYear(searchBean);
		List<MapBean> resourceList = searchService.searchResourceByYear(searchBean);
		File file = createEXCEL(searchBean.getStartYear() + "年番组列表.xls", animationList, resourceList);
		CommonUtils.downLoadFile(request, response, file.getName(), file);
	}

	// excelDownloadByName
	@RequestMapping(value = "/excelDownloadByName")
	public void excelDownloadByName(HttpServletRequest request, HttpServletResponse response, Model model,
			SearchBean searchBean) throws Exception {
		List<MapBean> animationList = searchService.searchAnimationByName(searchBean);
		List<MapBean> resourceList = searchService.searchResourceByName(searchBean);
		File file = createEXCEL("检索'" + searchBean.getAnimation_name() + "'列表.xls", animationList, resourceList);
		CommonUtils.downLoadFile(request, response, file.getName(), file);
	}

//	@RequestMapping(value = "/excelDownloadAdv")
//	public void excelDownloadAdv(HttpServletRequest request, HttpServletResponse response, Model model,
//			SearchBean searchBean) throws Exception {
//		List<MapBean> animationList = searchService.advSearch(searchBean);
//		List<MapBean> resourceList = searchService.searchResourceByAdv(searchBean);
//		StringBuffer fileName = new StringBuffer(searchBean.getStartYear() + "年至" + searchBean.getEndYear() + "年"
//				+ CommonUtils.getValueByKey("TYPE" + searchBean.getAnimation_type()));
//		if (searchBean.getAnimation_name() != null && searchBean.getAnimation_name().trim().length() > 0) {
//			fileName.append("检索'" + searchBean.getAnimation_name() + "'");
//		}
//		fileName.append("列表.xls");
//		File file = createEXCEL(fileName.toString(), animationList, resourceList);
//		CommonUtils.downLoadFile(request, response, file.getName(), file);
//	}

	// 生成excel文件
	public File createEXCEL(String fileName, List<MapBean> animationList, List<MapBean> resourceList) {

		File file = new File(fileName);

		// String[] title = { "类型", "名称", "播出时间", "集数", "原作" };// excel工作表的标题
		// 创建excel
		WritableWorkbook workbook;
		try {
			workbook = Workbook.createWorkbook(file);
			WritableSheet sheet = workbook.createSheet("第一页", 0); // 添加第一个工作表
			// 设置列宽
			sheet.setColumnView(0, 15);
			sheet.setColumnView(1, 40);
			sheet.setColumnView(2, 12);

			jxl.write.WritableCellFormat wcsB = new jxl.write.WritableCellFormat();
			// 设置边框
			wcsB.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
			// wcsB.setAlignment(Alignment.FILL);

			// 添加表头"调用日期", "接口名称", "成功次数", "失败次数"
			// for (int i = 0; i < title.length; i++) {
			// // Label(列号,行号 ,内容 )
			// sheet.addCell(new Label(i, 0, title[i], wcsB));
			// }

			// 设置自定义颜色，通过java.awt.Color中decode方法提取16进制颜色值
			Color color1 = Color.decode("#7bbfea"); // 自定义的颜色
			workbook.setColourRGB(Colour.BLUE, color1.getRed(), color1.getGreen(), color1.getBlue());
			WritableCellFormat wcf1 = new WritableCellFormat();// 单元格样式
			wcf1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
			wcf1.setBackground(Colour.BLUE);

			// 设置自定义颜色，通过java.awt.Color中decode方法提取16进制颜色值
			Color color2 = Color.decode("#9FEF4E"); // 自定义的颜色
			workbook.setColourRGB(Colour.GREEN, color2.getRed(), color2.getGreen(), color2.getBlue());
			WritableCellFormat wcf2 = new WritableCellFormat();// 单元格样式
			wcf2.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
			wcf2.setBackground(Colour.GREEN);

			String[] idArr = new String[resourceList.size()];
			for (int i = 0; i < resourceList.size(); i++) {
				idArr[i] = resourceList.get(i).getData().get("ANIMATION_ID");
			}
			int resultString = CommonUtils.getMostFrequentByArrayList(idArr);

			// row是行号，循环添加内容用
			int row = 0;

			for (int i = 0; i < animationList.size(); i++) {
				// Label(列号,行号 ,内容 )
				String ANIMATION_TYPE = CommonUtils
						.getValueByKey("TYPE" + animationList.get(i).getData().get("ANIMATION_TYPE"));
				String ANIMATION_SOURCE = CommonUtils
						.getValueByKey("SOURCE" + animationList.get(i).getData().get("ANIMATION_SOURCE"));
				String ANIMATION_BROADCAST_TIME = animationList.get(i).getData().get("ANIMATION_BROADCAST_TIME")
						.substring(0, 10);

				String time = animationList.get(i).getData().get("ANIMATION_BROADCAST_TIME");
				time = time.substring(0, time.indexOf("-"));
				sheet.addCell(new Label(0, row, time + ANIMATION_TYPE, wcsB));
				sheet.addCell(new Label(1, row, animationList.get(i).getData().get("ANIMATION_NAME"), wcsB));
				sheet.addCell(new Label(2, row, ANIMATION_BROADCAST_TIME, wcsB));
				sheet.addCell(new Label(3, row, animationList.get(i).getData().get("ANIMATION_EPISODE") + "集", wcsB));
				sheet.addCell(new Label(4, row, ANIMATION_SOURCE, wcsB));

				int r = 0;
				for (int j = 0; j < resourceList.size(); j++) {
					if (animationList.get(i).getData().get("ANIMATION_ID")
							.equals(resourceList.get(j).getData().get("ANIMATION_ID"))) {

						WritableCellFormat wcf = null;
						if (resourceList.get(j).getData().get("RESOURCE_TYPE").equals("BD")) {
							wcf = wcf1;
						} else {
							wcf = wcf2;
						}

						sheet.setColumnView(5 + r, 5);
						sheet.addCell(new Label(5 + r, row, resourceList.get(j).getData().get("RESOURCE_TYPE"), wcf));
						r++;

						sheet.setColumnView(5 + r, 5);
						sheet.addCell(new Label(5 + r, row, resourceList.get(j).getData().get("RESOURCE_FORMAT"), wcf));
						r++;

						sheet.setColumnView(5 + r, 7);
						sheet.addCell(new Label(5 + r, row,
								resourceList.get(j).getData().get("RESOURCE_IMAGE_RESOLUTION"), wcf));
						r++;

						sheet.setColumnView(5 + r, 9);
						String sub = "";
						if (!resourceList.get(j).getData().get("RESOURCE_SUB_FORMAT").toUpperCase().equals("NO")) {
							sub = resourceList.get(j).getData().get("RESOURCE_SUB_TYPE")
									+ resourceList.get(j).getData().get("RESOURCE_SUB_FORMAT");
						} else {
							sub = resourceList.get(j).getData().get("RESOURCE_SUB_TYPE");
						}
						sheet.addCell(new Label(5 + r, row, sub, wcf));
						r++;

						sheet.setColumnView(5 + r, 40);
						String dataPath = resourceList.get(j).getData().get("RESOURCE_ADDRESS");
						sheet.addCell(new Label(5 + r, row, dataPath, wcf));
						r++;

					}

				}

				/**
				 * 补齐表格
				 * 
				 * 每条资源信息或产生5列数据
				 */
				for (int j = 0; j < (resultString * 5 + 1) - r; j++) {
					sheet.addCell(new Label(5 + r + j, row, "", wcsB));
				}

				row++;
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

}
