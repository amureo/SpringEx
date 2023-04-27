package com.co.kr.controller;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.code.Code;
import com.co.kr.domain.BoardFileDomain;
import com.co.kr.domain.PlayListDomain;
import com.co.kr.domain.PlayListView;
import com.co.kr.exception.RequestException;
import com.co.kr.service.PlayListService;

@Controller
public class PlayListController {

	@Autowired
	private PlayListService playListService;
	
	@RequestMapping(value = "/playList")
	public ModelAndView openPlayList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		List<PlayListView> items = playListService.getPlayList();
		System.out.println("items ==> "+ items);
		mav.addObject("items", items);

		// mac
		HttpSession session = request.getSession();
		mav.addObject("macaddress", session.getAttribute("mac")); //맥주소
		
		mav.setViewName("pl/playList.html");
		return mav; 
	}
	

	@RequestMapping(value = "/playList/upload")
	public String uploadPlayList(PlayListDomain playListDomain, MultipartHttpServletRequest request, HttpServletRequest httpReq) throws IOException, ParseException{
		System.out.println("insert items ==> "+ playListDomain);
		playListService.insertPlayList(playListDomain,request,httpReq);
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
	public ModelAndView detailPlayList(@RequestParam("idx") String idx,HttpServletRequest request){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("idx", Integer.parseInt(idx));
		
		System.out.println("detail idx ==> "+ idx);
		
		ModelAndView mav = new ModelAndView();
		

		// mac
		HttpSession session = request.getSession();
		mav.addObject("macaddress", session.getAttribute("mac")); //맥주소
		
		//하나파일 가져오기
		PlayListView item=playListService.detailPlayList(map);
		mav.addObject("item",item);
		mav.setViewName("pl/playListDetail.html");
		return mav;
	}

	@RequestMapping(value = "/playList/update")
	public String updatePlayList(PlayListDomain playListDomain, MultipartHttpServletRequest request, HttpServletRequest httpReq) throws IOException, ParseException{
		System.out.println("update items ==> "+ playListDomain);
		playListService.updatePlayList(playListDomain,request,httpReq);
		
		return "redirect:/playList";
	}

}
