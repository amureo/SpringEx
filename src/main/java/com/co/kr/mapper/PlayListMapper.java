package com.co.kr.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.domain.PlayListDomain;
import com.co.kr.domain.PlayListFileDomain;
import com.co.kr.domain.PlayListView;

@Mapper
public interface PlayListMapper {
	public List<PlayListDomain> getPlayList();
	public void insertPlayList(PlayListDomain playListDomain);
	public void deletePlayList(Map<String, Object> map);
	public PlayListDomain detailPlayList(Map<String, Object> map);
	public void updatePlayList(PlayListDomain playListDomain);
	public void updateRanking();

	//file
	public void insertPlayListFile(PlayListFileDomain playListFileDomain);
	public List<PlayListFileDomain> getAllPlayListFile();
	public PlayListFileDomain getPlayListFile(Map<String, Object> map);
	public void updatePlayListFile(PlayListFileDomain playListFileDomain);
	public void deletePlayListFile(Map<String,Object> map);
	
	// view
	public List<PlayListView> getViewPlayList();
	public PlayListView getOneViewPlayList(Map<String,Object> map);
	
}
