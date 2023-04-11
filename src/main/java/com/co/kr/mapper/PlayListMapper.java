package com.co.kr.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.domain.PlayListDomain;

@Mapper
public interface PlayListMapper {
	public List<PlayListDomain> getPlayList();
	public void insertPlayList(PlayListDomain playListDomain);
	public void deletePlayList(Map<String, Object> map);
	public PlayListDomain detailPlayList(Map<String, Object> map);
	public void updatePlayList(PlayListDomain playListDomain);
	public void updateRanking();
	
}
