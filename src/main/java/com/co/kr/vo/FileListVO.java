package com.co.kr.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class FileListVO {
	private String title;
	private String content;
	private String isEdit;
	private String seq;
	public void setContent(String string) {
		this.content=string;
	}
	public void setTitle(String string) {
		this.title=string;
	}
	public void setSeq(String bdSeq) {
		this.seq=bdSeq;
	}
	public void setIsEdit(String string) {
		this.isEdit=string;
	}
}
