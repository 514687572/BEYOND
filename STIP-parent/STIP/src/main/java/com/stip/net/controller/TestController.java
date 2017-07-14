package com.stip.net.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stip.net.entity.MallUser;
import com.stip.net.service.ITestService;
import com.stip.net.utils.FileUtils;

@Controller
@Scope("request")
@RequestMapping("/test")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, Exception.class })
public class TestController extends AbstractController {
	@Resource
	private ITestService testService;

	@RequestMapping(value = "/test.do", method = { RequestMethod.GET, RequestMethod.HEAD })
	public @ResponseBody Map<String, Object> test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		testService.select("1");
		return jsonResult;
	}
	
	@RequestMapping("/{telNum}/getUser.do")
	public Map<String, Object> getUser(@PathVariable("telNum") String telNum) throws Exception {
		List<MallUser> userList=testService.selectUserByPhone(telNum);
		
		for(MallUser user:userList){
			System.out.println(user.getUserName()+"--------->");
		}
		
		return jsonResult;
	}
	
	@RequestMapping(value = "/getCode.do", method = RequestMethod.GET)
	public void service(HttpServletRequest req, HttpServletResponse response) throws ServletException, java.io.IOException {
		response.setContentType("application/pdf");
		ServletOutputStream sos = response.getOutputStream();
        FileUtils fu = new FileUtils();
        byte[] buffer = fu.readFileFromBat("Google-Java编程风格指南中文版", "pdf");  
        response.setContentLength((int) buffer.length);  
        sos.write(buffer);
        sos.close();  
	}
	
}
