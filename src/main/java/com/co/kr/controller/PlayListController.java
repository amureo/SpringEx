package com.co.kr.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.domain.BoardFileDomain;
import com.co.kr.domain.PlayListDomain;
import com.co.kr.service.PlayListService;

@Controller
public class PlayListController {

	@Autowired
	private PlayListService playListService;
	
	@RequestMapping(value = "/playList")
	public ModelAndView openPlayList() {
		ModelAndView mav = new ModelAndView();
		List<PlayListDomain> items = playListService.getPlayList();
		System.out.println("items ==> "+ items);
		mav.addObject("items", items);
		mav.setViewName("board/playList.html");
		return mav; 
	}
	

	@RequestMapping(value = "/playList/upload")
	public String uploadPlayList(PlayListDomain playListDomain, MultipartHttpServletRequest request, HttpServletRequest httpReq) throws IOException, ParseException{
		System.out.println("insert items ==> "+ playListDomain);
		playListService.insertPlayList(playListDomain,request);
		
		return "redirect:/playList";
	}

	@RequestMapping(value = "/playList/delete")
	public String deletePlayList(@RequestParam("idx") String idx) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("idx", Integer.parseInt(idx));
		
		System.out.println("delete idx ==> "+ idx);
		
		playListService.deletePlayList(map);
		
		return "redirect:/playList";
	}
	
	@RequestMapping(value = "/playList/detail")
	public ModelAndView detailPlayList(@RequestParam("idx") String idx){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("idx", Integer.parseInt(idx));
		
		System.out.println("detail idx ==> "+ idx);
		
		ModelAndView mav = new ModelAndView();
		//하나파일 가져오기
		PlayListDomain item=playListService.detailPlayList(map);
		mav.addObject("item",item);
		mav.setViewName("board/playListDetail.html");
		return mav;
	}

	@RequestMapping(value = "/playList/update")
	public String updatePlayList(PlayListDomain playListDomain, MultipartHttpServletRequest request, HttpServletRequest httpReq) throws IOException, ParseException{
		System.out.println("update items ==> "+ playListDomain);
		playListService.updatePlayList(playListDomain,request);
		
		return "redirect:/playList";
	}

}
