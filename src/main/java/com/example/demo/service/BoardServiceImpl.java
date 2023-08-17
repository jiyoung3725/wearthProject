package com.example.demo.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.BoardJpaRepository;
import com.example.demo.repository.BoardMyBatisRepository;
import com.example.demo.vo.BoardVO;

import lombok.Setter;

@Service
@Setter
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardJpaRepository abJPA;
	
	@Autowired
	private BoardMyBatisRepository abMB;
	
	//페이징 처리를 위하여 start, end를 map에 넣어 받음
	@Override
	public List<BoardVO> findAll(HashMap<String, Object> map) {
		System.out.println("process : BoardServiceImp--------------------------");
		return abMB.findAll(map);
	}
	//페이징 처리를 위해 전체 게시글 수 불러오기
	@Override
	public int getTotalRecord() {
		return abMB.getTotalRecord();
	}

	//게시글 하나의 정보 가져오기
	@Override
	public BoardVO findByBoardno(int boardno) {
		return abMB.findByBoardno(boardno);
	}
	
	
	//게시글 입력시 다음 게시글 번호 부여
	@Override
	public int getNextNo() {
		return abJPA.getNextNo();
	}
	
	//게시글 등록
	@Override
	public void insert(BoardVO b) {
		abJPA.insert(b);
	}
	//게시글 삭제
	@Override
	public void delete(int boardno) {
		abJPA.delete(boardno);
	}
	
	//게시글 수정
	@Override
	public void update(String title, String content, int boardno) {
		abJPA.update(title, content, boardno);
	}
	
	

}
