package com.disk;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.basedao.dbtool.MapBean;
import com.bean.disk.DiskBean;

@Controller
public class DiskController {

	@Autowired
	public DiskService diskService;

	@RequestMapping(value = "/toDiskManage")
	public ModelAndView toDiskManage(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession,
			Model model) throws IOException, SQLException {
		ModelAndView view = null;
		view = new ModelAndView("disk/diskManage");
		return view;
	}

	/**
	 * 简单基本检索
	 * 
	 * @param request
	 * @param response
	 * @param httpSession
	 * @param model
	 * @param animation
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	@RequestMapping(value = "/diskSearch")
	public ModelAndView diskSearch(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession,
			Model model, DiskBean diskBean) throws IOException, SQLException {
		ModelAndView view = null;

		List<MapBean> diskList = diskService.getDiskList(diskBean);
		view = new ModelAndView("disk/diskList");

		model.addAttribute("diskList", diskList);
		return view;
	}

	@RequestMapping(value = "/diskAdd")
	public @ResponseBody String diskAdd(HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession, Model model, DiskBean diskBean) throws IOException, SQLException {
		String result = "OK";
		try {
			int r = diskService.diskAdd(diskBean);
			if (r > 0) {
				result = "OK";
			} else {
				result = "ERROR";
			}
		} catch (Exception e) {
			result = "ERROR:" + e.getMessage();
		}
		return result;
	}

	@RequestMapping(value = "/toDiskEdit/{disk_id}")
	public ModelAndView toDiskEdit(@PathVariable String disk_id, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession, Model model, DiskBean diskBean)
			throws IOException, SQLException {
		ModelAndView view = null;

		List<MapBean> diskList = diskService.getDiskById(disk_id);
		model.addAttribute("diskList", diskList);
		view = new ModelAndView("disk/diskEdit");
		return view;
	}

	@RequestMapping(value = "/diskEdit")
	public @ResponseBody String diskEdit(  HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession, Model model,DiskBean diskBean)
			throws IOException, SQLException {
		String result = "OK";
		try {
			int r = diskService.diskEdit(diskBean);
			if (r > 0) {
				result = "OK";
			} else {
				result = "ERROR";
			}
		} catch (Exception e) {
			result = "ERROR:" + e.getMessage();
		}
		return result;
	}

}
