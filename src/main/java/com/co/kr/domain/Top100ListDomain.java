package com.co.kr.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder(builderMethodName="builder")
public class Top100ListDomain {
	private String idx;
	private String rankedAt;
	private String songId;
	private String title;
	private String ranking;
	private String artist;
	private String album;
	private String youtube;
}


