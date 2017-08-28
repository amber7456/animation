package com.poster;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/poster")
public class PosterController {

	@Autowired
	private PosterService posterService;

	@RequestMapping("/getPoster/{id}") //
	public void getPoster(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("image/gif");
		try {
			OutputStream out = response.getOutputStream();
			byte[] b = posterService.getPosterById(id);
			out.write(b);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
