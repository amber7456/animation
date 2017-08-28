package com.statistics;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bean.search.SearchBean;
import com.utils.CommonUtils;

@Controller
public class StatisticsController {

	@Autowired
	private StatisticsService statisticsService;

 
	@RequestMapping(value = "/toStatistics")
	public ModelAndView toBasicSearch(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession,
			Model model) throws IOException, SQLException {
		ModelAndView view = null;
		view = new ModelAndView("statistics/statistics");
		String nowYear = CommonUtils.getNowStr("YYYY");
		model.addAttribute("nowYear", nowYear);
		return view;
	}

 
	@RequestMapping(value = "/getStatisticsForLine")
	@ResponseBody
	public Map<String, Object> getStatisticsForLine(HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession, SearchBean searchBean) throws IOException, SQLException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap = statisticsService.getStatisticsForLine(searchBean);
		return modelMap;
	}
	
	@RequestMapping(value = "/getStatisticsForPie")
	@ResponseBody
	public Map<String, Object> getStatisticsForPie(HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession, SearchBean searchBean) throws IOException, SQLException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap = statisticsService.getStatisticsForPie(searchBean);
		return modelMap;
	}
	
	@RequestMapping(value = "/getStatisticsForBD")
	@ResponseBody
	public Map<String, Object> getStatisticsForBD(HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession, SearchBean searchBean) throws IOException, SQLException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap = statisticsService.getStatisticsForBD(searchBean);
		return modelMap;
	}
	
}
