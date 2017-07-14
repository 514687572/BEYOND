package com.stip.net.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stip.net.service.ISysUserAuthorityService;

@Controller
@Scope("request")
@RequestMapping("/stip/security")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, Exception.class })
public class AuthorityController extends AbstractController {
	@Resource
	private ISysUserAuthorityService sysUserAuthorityService;

	/**
	 * request -- userId  /  response elements
	 * */
	@RequestMapping(value = "/getElementsByUserId.do", method = { RequestMethod.GET, RequestMethod.HEAD })
	public @ResponseBody Map<String, Object> test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		return userId == null ? null : sysUserAuthorityService.selectElementsByUserIds(userId);
	}
	
	
}
