package com.processinformation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Qualifier("loginController")
public class LoginController {

	private final String viewNamespace="login/";
	
	@RequestMapping("/login.html")
	public ModelAndView loginPage(ModelAndView mv,HttpServletRequest request,HttpServletResponse response)
	{
		mv.setViewName(viewNamespace+"login");
		return mv;
	}
}
