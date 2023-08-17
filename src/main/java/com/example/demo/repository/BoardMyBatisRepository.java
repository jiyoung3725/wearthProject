package com.example.demo.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.db.BoardDBManager;
import com.example.demo.db.DBManager;
import com.example.demo.db.SchoolDBManager;
import com.example.demo.vo.BoardVO;
import com.example.demo.vo.EducationVO;

@Repository
public class BoardMyBatisRepository {

	public List<BoardVO> findAll(HashMap<String, Object> map){
		return BoardDBManager.findAll(map);
	}

	//페이징 처리를 위해 전체 게시글 수 불러오기
	public int getTotalRecord() {
		return BoardDBManager.getTotalRecord();
	}
	
	//게시글 하나의 정보 가져오기
	public BoardVO findByBoardno(int boardno) {
		return BoardDBManager.findByBoardno(boardno);
	}
}
