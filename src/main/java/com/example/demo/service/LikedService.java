package com.example.demo.service;

import java.util.HashMap;
import java.util.List;

import org.apache.tomcat.util.buf.StringCache;

import com.example.demo.vo.BoardVO;
import com.example.demo.vo.LikedVO;

public interface LikedService {
	
	//-----------------------------------------------------
	
	//좋아요 추가를 위한 다음 게시글 번호 불러오기 - JPA
	public int getNextNo();

	//좋아요 추가
	public void insert(LikedVO l);

	//좋아요 취소
	public void delete(int boardno, int userno);
	
	//좋아요 여부 확인
	public int checkLiked(HashMap<String, Integer> map);
	
	//좋아요 수 카운트
	public int countLiked(int boardno);
}
