package com.co.kr.service;


import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.co.kr.code.Code;
import com.co.kr.domain.BoardFileDomain;
import com.co.kr.domain.PlayListDomain;
import com.co.kr.domain.PlayListFileDomain;
import com.co.kr.domain.PlayListView;
import com.co.kr.exception.RequestException;
import com.co.kr.mapper.PlayListMapper;
import com.co.kr.util.CommonUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class PlayListServiceImpl implements PlayListService{

	@Autowired
	private PlayListMapper playListMapper;

	@Override
	public List<PlayListView> getPlayList(){
		return playListMapper.getViewPlayList();
	}
	
	@Override
	public void insertPlayList(PlayListDomain playListDomain,MultipartHttpServletRequest request, HttpServletRequest httpReq){
		
		playListMapper.insertPlayList(playListDomain); // 게시글 저장
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("idx",Integer.parseInt(playListDomain.getIdx()));

		// 파일 처리
		PlayListFileDomain playListFileDomain=fileProcess(map, request, httpReq);
		if(playListFileDomain!=null) {
			playListMapper.insertPlayListFile(playListFileDomain);
		}
	}

	@Override
	public void deletePlayList(Map<String, Object> map) {
		playListMapper.deletePlayList(map);
		playListMapper.updateRanking(); // 중간 삭제를 대비한 랭킹 업데이트
		
		// 파일 삭제 처리
		PlayListFileDomain playListFileDomain=playListMapper.getPlayListFile(map);
		if(playListFileDomain!=null) {
			Path filePath = Paths.get(playListFileDomain.getUpFilePath());
	        try {
	            // 파일 물리삭제
	            Files.deleteIfExists(filePath); // notfound시 exception 발생안하고 false 처리
	            // db 삭제 
	            playListMapper.deletePlayListFile(map);
	        } catch (DirectoryNotEmptyException e) {
				throw RequestException.fire(Code.E404, "디렉토리가 존재하지 않습니다", HttpStatus.NOT_FOUND);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	}
	
	@Override
	public PlayListView detailPlayList(Map<String,Object> map) {
		return playListMapper.getOneViewPlayList(map);
	}
	
	@Override
	public void updatePlayList(PlayListDomain playListDomain,MultipartHttpServletRequest request, HttpServletRequest httpReq){
		playListMapper.updatePlayList(playListDomain);

		Map<String,Object> map=new HashMap<String,Object>();
		map.put("idx",Integer.parseInt(playListDomain.getIdx()));
		map.put("isEdit","eidt");

		// 파일 처리
		PlayListFileDomain playListFileDomain=fileProcess(map, request, httpReq);
		if(playListFileDomain!=null) {
			playListMapper.insertPlayListFile(playListFileDomain);
		}
	}
	
	public PlayListFileDomain fileProcess(Map<String,Object> map,MultipartHttpServletRequest request, HttpServletRequest httpReq) {
		//session 생성
		HttpSession session = httpReq.getSession();

		List<MultipartFile> multipartFiles = request.getFiles("files");
		
		for (MultipartFile multipartFile : multipartFiles) {
			
			if(!multipartFile.isEmpty()) {  // 파일 있을때 
				if(map.get("isEdit")!=null) {
					PlayListFileDomain temp=playListMapper.getPlayListFile(map);
					if(temp!=null) {
						Path filePath = Paths.get(temp.getUpFilePath());
				        try {
				            // 파일 삭제
				            Files.deleteIfExists(filePath); // notfound시 exception 발생안하고 false 처리
				            //삭제 
				            playListMapper.deletePlayListFile(map); //데이터 삭제
				        } catch (DirectoryNotEmptyException e) {
							throw RequestException.fire(Code.E404, "디렉토리가 존재하지 않습니다", HttpStatus.NOT_FOUND);
				        } catch (IOException e) {
				            e.printStackTrace();
				        }
					}
				}
			}
		}
		///////////////////////////// 새로운 파일 저장 ///////////////////////
		
		// 저장 root 경로만들기
		Path rootPath = Paths.get(new File("/Users/gimhyeseong/Documents/SpringExFile/").toString(),"upload", File.separator).toAbsolutePath().normalize();			
		File pathCheck = new File(rootPath.toString());
		// folder chcek
		if(!pathCheck.exists()) pathCheck.mkdirs();
		
		for (MultipartFile multipartFile : multipartFiles) {
			
			if(!multipartFile.isEmpty()) {  // 파일 있을때 

				//확장자 추출
				String originalFileExtension;
				String contentType = multipartFile.getContentType();
				String origFilename = multipartFile.getOriginalFilename();
				
				//확장자 조재안을경우
				if(ObjectUtils.isEmpty(contentType)){
					break;
				}else { // 확장자가 jpeg, png인 파일들만 받아서 처리
					if(contentType.contains("image/jpeg")) {
						originalFileExtension = ".jpg";
					}else if(contentType.contains("image/png")) {
						originalFileExtension = ".png";
					}else {
						break;
					}
				}

				
				//파일명을 업로드한 날짜로 변환하여 저장
				String uuid = UUID.randomUUID().toString();
				String current = CommonUtils.currentTime();
				String newFileName = uuid + current + originalFileExtension;
				
				//최종경로까지 지정
				Path targetPath = rootPath.resolve(newFileName);
				
				File file = new File(targetPath.toString());
				
				
				try {
					//파일복사저장
					multipartFile.transferTo(file);
					// 파일 권한 설정(쓰기, 읽기)
					file.setWritable(true);
					file.setReadable(true);
					//파일 domain 생성 
					PlayListFileDomain playListFileDomain = PlayListFileDomain.builder()
							.idx((int)map.get("idx"))
							.upOriginalFileName(origFilename)
							.upNewFileName("resources/upload/"+newFileName) // WebConfig에 동적 이미지 폴더 생성 했기때문
							.upFilePath(targetPath.toString())
							.upFileSize((int)multipartFile.getSize())
							.build();

					System.out.println("upload ready");
					System.out.println("file => "+playListFileDomain);
					return playListFileDomain;

				} catch (IOException e) {
					throw RequestException.fire(Code.E404, "잘못된 업로드 파일", HttpStatus.NOT_FOUND);
				}
			}

		}

		return null;
	}
}
