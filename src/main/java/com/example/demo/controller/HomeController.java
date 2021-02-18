package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.services.services;

@Controller
public class HomeController {
	
	@Autowired
	services service;
	
	@RequestMapping("home")
	public String home(Model mode) {
		mode.addAttribute("model",service.getModel());
		mode.addAttribute("total",service.getTotal());
		//System.out.println("yep..........................................................................................................");
		return "home";
	}
}
