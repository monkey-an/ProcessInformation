package com.processinformation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.processinformation.entity.test.TestEntity;
import com.processinformation.service.TestService;

@Controller
@Qualifier("loginController")
public class LoginController {

	private final String viewNamespace="login/";
	
	@Autowired
	@Qualifier("testService")
	private TestService testService;
	
	@RequestMapping("/login.html")
	public ModelAndView loginPage(ModelAndView mv,HttpServletRequest request,HttpServletResponse response)
	{
		List<TestEntity> allTestData=testService.getAllTestData();
		mv.addObject("data", allTestData);
		mv.setViewName(viewNamespace+"login");
		return mv;
	}
}
