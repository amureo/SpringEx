package com.co.kr.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName="builder")
public class PlayListFileDomain {
	private Integer idx;
	
	private String upOriginalFileName;
	private String upNewFileName; //동일 이름 업로드 될 경우
	private String upFilePath;
	private Integer upFileSize;
}
