package com.co.kr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.co.kr.domain.Top100ListDomain;
import com.co.kr.mapper.Top100Mapper;

@Service
@Transactional
public class Top100ServiceImpl implements Top100Service{

	@Autowired
	private Top100Mapper top100Mapper;
	
	@Override
	public List<Top100ListDomain> getTop100List(){
		return top100Mapper.getTop100List();
	}
}
