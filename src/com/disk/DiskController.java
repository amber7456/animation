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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.basedao.dbtool.MapBean;
import com.bean.animation.AnimationBean;
import com.bean.disk.DiskBean;
import com.bean.search.SearchBean;
import com.bean.yearlist.YearListBean;
import com.utils.CommonUtils;

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
	public @ResponseBody String diskAdd(  HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession, Model model, DiskBean diskBean)
			throws IOException, SQLException {
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

	// /**
	// * 详情页
	// *
	// * @param animationId
	// * @param request
	// * @param response
	// * @param httpSession
	// * @param model
	// * @return
	// * @throws IOException
	// * @throws SQLException
	// */
	// @RequestMapping(value = "/animationDetail/{animationId}")
	// public ModelAndView animationDetail(@PathVariable String animationId,
	// HttpServletRequest request,
	// HttpServletResponse response, HttpSession httpSession, Model model)
	// throws IOException, SQLException {
	// ModelAndView view = null;
	// // 根据id查询详情，
	// MapBean animation = animationService.getAnimationDetail(animationId);
	// List<MapBean> resourceList =
	// animationService.getResourceById(animationId);
	// model.addAttribute("animation", animation);
	// model.addAttribute("resourceList", resourceList);
	// view = new ModelAndView("animation/animationDetail");
	// return view;
	// }
	//
	// /**
	// *
	// * @param animationId
	// * @param request
	// * @param response
	// * @param httpSession
	// * @param model
	// * @return
	// * @throws IOException
	// * @throws SQLException
	// */
	// @RequestMapping(value = "/toAnimationEdit/{animationId}")
	// public ModelAndView animationEdit(@PathVariable String animationId,
	// HttpServletRequest request,
	// HttpServletResponse response, HttpSession httpSession, Model model)
	// throws IOException, SQLException {
	// ModelAndView view = null;
	// // 根据id查询详情，
	// MapBean animation = animationService.getAnimationDetail(animationId);
	// List<MapBean> resourceList =
	// animationService.getResourceById(animationId);
	// model.addAttribute("animation", animation);
	// model.addAttribute("resourceList", resourceList);
	// view = new ModelAndView("animation/animationEdit");
	// return view;
	// }
	//
	// /**
	// *
	// * @param poster_hq
	// * @param request
	// * @param response
	// * @param httpSession
	// * @param model
	// * @param animation
	// * @return
	// * @throws IOException
	// * @throws SQLException
	// */
	// @RequestMapping(value = "/animationEdit")
	// public @ResponseBody String animationEdit(@RequestParam MultipartFile
	// poster_hq, HttpServletRequest request,
	// HttpServletResponse response, HttpSession httpSession, Model model,
	// AnimationBean animation)
	// throws IOException, SQLException {
	// String result = "OK";
	// try {
	// int r = animationService.animationEdit(animation);
	// if (r > 0) {
	// result = "OK";
	// } else {
	// result = "ERROR";
	// }
	// } catch (Exception e) {
	// result = "ERROR:" + e.getMessage();
	// }
	// return result;
	// }
	//
	// @RequestMapping(value = "/animationDelete")
	// public @ResponseBody String animationDelete(HttpServletRequest request,
	// HttpServletResponse response,
	// HttpSession httpSession, Model model, AnimationBean animation) throws
	// IOException, SQLException {
	// String result = "OK";
	// try {
	// int r = animationService.animationDelete(animation);
	// if (r > 0) {
	// result = "OK";
	// } else {
	// result = "ERROR";
	// }
	// } catch (Exception e) {
	// result = "ERROR:" + e.getMessage();
	// }
	// return result;
	// }

}
