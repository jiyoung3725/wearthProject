package com.example.demo.db;


import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.example.demo.vo.BoardVO;


public class BoardDBManager extends DBManager{

	public static List<BoardVO> findAll(HashMap<String, Object> map){
		SqlSession session = sqlSessionFactory.openSession();
		List<BoardVO> list = session.selectList("board.findAll", map);
		session.close();
		return list;
	}

	public static int getTotalRecord() {
		System.out.println("getTotalRecord() 정상 작동");
		int totalRecord;
		SqlSession session = sqlSessionFactory.openSession();
		totalRecord = session.selectOne("board.getTotalRecord");
		session.close();
		return totalRecord;
	}
	
	public static BoardVO findByBoardno(int boardno) {
		System.out.println("findByBoardno() 정상 작동");
		BoardVO b;
		SqlSession session = sqlSessionFactory.openSession();
		b = session.selectOne("board.findByBoardno", boardno);
		session.close();
		return b;
	}
}