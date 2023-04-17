package com.co.kr.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.co.kr.domain.PlayListDomain;
import com.co.kr.domain.PlayListView;

public interface  PlayListService{
	List<PlayListView> getPlayList();
	void insertPlayList(PlayListDomain playListDomain,MultipartHttpServletRequest multipartHttpServletRequest, HttpServletRequest httpReq);
	void deletePlayList(Map<String, Object> map);
	PlayListView detailPlayList(Map<String,Object> map);
	void updatePlayList(PlayListDomain playListDomain,MultipartHttpServletRequest multipartHttpServletRequest, HttpServletRequest httpReq);
}
