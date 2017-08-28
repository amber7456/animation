package com.search;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.basedao.dbtool.MapBean;
import com.bean.search.SearchBean;
import com.bean.yearlist.YearListBean;
import com.utils.CommonUtils;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param httpSession
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	@RequestMapping(value = "/toBasicSearch")
	public ModelAndView toBasicSearch(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession,
			Model model) throws IOException, SQLException {
		ModelAndView view = null;
		view = new ModelAndView("search/basicSearch");
		String nowYear = CommonUtils.getNowStr("YYYY");
		model.addAttribute("nowYear", nowYear);
		
		SearchBean searchBean = new SearchBean();
		searchBean.setStartYear(nowYear);
		searchBean.setSearchType("year");
		
		List<MapBean> animationList = searchService.searchAnimationByYear(searchBean);
		YearListBean  yearListBean = searchService.clearUp(animationList, searchBean);
		
		model.addAttribute("YearList", yearListBean);//  
		model.addAttribute("searchBean", searchBean);
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
	@RequestMapping(value = "/basicSearch")
	public ModelAndView basicSearch(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession,
			Model model, SearchBean searchBean) throws IOException, SQLException {
		ModelAndView view = null;
		
		YearListBean yearListBean = null;
		if (searchBean.getSearchType().equals("name")) {
			List<MapBean> animationList = searchService.searchAnimationByName(searchBean);
			yearListBean = searchService.clearUp(animationList, searchBean);
			view = new ModelAndView("search/basicSearch_name");
		} else {
			List<MapBean> animationList = searchService.searchAnimationByYear(searchBean);
			yearListBean = searchService.clearUp(animationList, searchBean);
			view = new ModelAndView("search/basicSearch_year");
		}
		String nowYear = CommonUtils.getNowStr("YYYY");
		model.addAttribute("nowYear", nowYear);
		model.addAttribute("searchBean", searchBean);
		model.addAttribute("YearList", yearListBean);
		return view;
	}

	@RequestMapping(value = "/toAdvSearch")
	public ModelAndView toAdvSearch(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession,
			Model model) throws IOException {
		ModelAndView view = null;
		view = new ModelAndView("search/advSearch");
		String nowYear = CommonUtils.getNowStr("YYYY");
		model.addAttribute("nowYear", nowYear);
		SearchBean searchBean = new SearchBean();
		searchBean.setAnimation_name("");
		searchBean.setAnimation_type("ALL");
		searchBean.setEndYear(nowYear);
		searchBean.setStartYear(nowYear);
		model.addAttribute("searchBean", searchBean);// 初始化查询条件
		return view;
	}

	@RequestMapping(value = "/advSearch")
	public ModelAndView advSearch(SearchBean searchBean, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession, Model model) throws IOException, SQLException {
		ModelAndView view = null;
		view = new ModelAndView("search/advSearch_data");
		List<MapBean> animationList = searchService.advSearch(searchBean);
		model.addAttribute("nowYear", CommonUtils.getNowStr("YYYY"));
		model.addAttribute("searchBean", searchBean);
		model.addAttribute("animationList", animationList);
		return view;
	}

}
