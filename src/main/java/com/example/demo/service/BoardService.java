package com.example.demo.service;

import java.util.HashMap;
import java.util.List;

import org.apache.tomcat.util.buf.StringCache;

import com.example.demo.vo.BoardVO;

public interface BoardService {
	
	//실천하기 게시글 목록 불러오기 - MB
	public List<BoardVO> findAll(HashMap<String, Object> map);
	
	//실천하기 게시글 페이징 처리하기 위한 전체 레코드 수 불러오기 - MB
	public int getTotalRecord();
	
	//게시글 하나의 정보 가져오기
	public BoardVO findByBoardno(int boardno);
	
	//-----------------------------------------------------
	
	//실천하기 게시글 등록 위한 다음 게시글 번호 불러오기 - JPA
	public int getNextNo();

	//실천하기 게시글 등록
	public void insert(BoardVO b);

	//실천하기 게시글 수정
	public void update(String title, String content, int boardno);
	
	//실천하기 게시글 삭제
	public void delete(int boardno);
}
