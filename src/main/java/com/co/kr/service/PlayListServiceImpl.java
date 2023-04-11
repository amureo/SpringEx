package com.co.kr.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.co.kr.domain.PlayListDomain;
import com.co.kr.mapper.PlayListMapper;

@Service
@Transactional
public class PlayListServiceImpl implements PlayListService{

	@Autowired
	private PlayListMapper playListMapper;

	@Override
	public List<PlayListDomain> getPlayList(){
		return playListMapper.getPlayList();
	}
	
	@Override
	public void insertPlayList(PlayListDomain playListDomain,MultipartHttpServletRequest multipartHttpServletRequest){
		
		playListMapper.insertPlayList(playListDomain); // 게시글 저장
		
		/*
		List<BoardFileDto> list=fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest); // 업로드된 파일을 서버에 저장하고 파일의 정보 가져옴
		if(CollectionUtils.isEmpty(list)==false) {
			playListMapper.insertBoardFileList(list);
		}
		*/
		
	}

	@Override
	public void deletePlayList(Map<String, Object> map) {
		playListMapper.deletePlayList(map);
		playListMapper.updateRanking(); // 중간 삭제를 대비한 랭킹 업데이트
	}
	
	@Override
	public PlayListDomain detailPlayList(Map<String,Object> map) {
		return playListMapper.detailPlayList(map);
	}
	
	@Override
	public void updatePlayList(PlayListDomain playListDomain,MultipartHttpServletRequest multipartHttpServletRequest){
		
		playListMapper.updatePlayList(playListDomain);
		
		/*
		List<BoardFileDto> list=fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest); // 업로드된 파일을 서버에 저장하고 파일의 정보 가져옴
		if(CollectionUtils.isEmpty(list)==false) {
			playListMapper.insertBoardFileList(list);
		}
		*/
		
	}
}
