package com.stip.net.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("request")
@RequestMapping("/yg/validateCode")
public class ResultServlet {
	@RequestMapping(value = "/validate.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String validateC = request.getSession().getAttribute("validateCode").toString().toUpperCase();
		String veryCode = request.getParameter("code").toUpperCase();
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		if (veryCode == null || "".equals(veryCode)) {
			jsonResult.put("result", "null");
		} else {
			if (validateC.equals(veryCode)) {
				jsonResult.put("result", Boolean.TRUE);
			} else {
				jsonResult.put("result", Boolean.FALSE);
			}
		}

		return jsonResult;
	}

	@RequestMapping(value = "/IosValidate.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> IosValidate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String validateC = request.getSession().getAttribute("validateCode").toString().toUpperCase();
		String veryCode = request.getParameter("code").toUpperCase();
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		if (veryCode == null || "".equals(veryCode)) {
			jsonResult.put("result", "null");
		} else {
			if (validateC.equals(veryCode)) {
				jsonResult.put("result", Boolean.TRUE);
			} else {
				jsonResult.put("result", Boolean.FALSE);
			}
		}

		return jsonResult;
	}
}
