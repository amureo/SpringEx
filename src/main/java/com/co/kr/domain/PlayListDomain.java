package com.co.kr.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName="builder")
public class PlayListDomain {
	private String idx;
	private String ranking;
	private String title;
	private String artist;
	private String youtube;
	
}

