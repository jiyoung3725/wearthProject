package com.example.demo.service;



import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.LikedJpaRepository;
import com.example.demo.repository.LikedMybatisRepository;
import com.example.demo.vo.LikedVO;

@Service
public class LikedServiceImpl implements LikedService {

	@Autowired
	private LikedJpaRepository lJPA;
	
	@Autowired
	private LikedMybatisRepository lMB;
	
	@Override
	public int getNextNo() {
		return lJPA.getNextNo();
	}

	@Override
	public void insert(LikedVO l) {
		lJPA.insert(l);
		
	}

	@Override
	public void delete(int boardno, int userno) {
		lJPA.delete(boardno, userno);
	}
	
	//좋아요 여부 확인
	@Override
	public int checkLiked(HashMap<String, Integer> map) {
		return lMB.checkLiked(map);
	}
	
	//좋아요 수 카운트
	@Override
	public int countLiked(int boardno) {
		return lMB.countLiked(boardno);
	}

}
