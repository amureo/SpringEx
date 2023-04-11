package com.co.kr.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.co.kr.domain.PlayListDomain;

public interface  PlayListService{
	List<PlayListDomain> getPlayList();
	void insertPlayList(PlayListDomain playListDomain,MultipartHttpServletRequest multipartHttpServletRequest);
	void deletePlayList(Map<String, Object> map);
	PlayListDomain detailPlayList(Map<String,Object> map);
	void updatePlayList(PlayListDomain playListDomain,MultipartHttpServletRequest multipartHttpServletRequest);
}
