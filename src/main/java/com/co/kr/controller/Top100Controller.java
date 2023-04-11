package com.co.kr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.domain.Top100ListDomain;
import com.co.kr.service.Top100Service;

@Controller
public class Top100Controller {

	@Autowired
	private Top100Service top100Service;
	
	@RequestMapping(value = "/top100")
	public ModelAndView top100List() { 
		ModelAndView mav = new ModelAndView();
		List<Top100ListDomain> items = top100Service.getTop100List();
		System.out.println("items ==> "+ items);
		mav.addObject("items", items);
		mav.setViewName("board/top100List.html");
		return mav; 
	}
	
}
