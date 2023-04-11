package com.co.kr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.domain.Top100ListDomain;

@Mapper
public interface Top100Mapper {
	public List<Top100ListDomain> getTop100List();
}
