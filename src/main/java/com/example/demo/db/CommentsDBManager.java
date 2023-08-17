package com.example.demo.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.example.demo.vo.CommentsVO;

public class CommentsDBManager extends DBManager{

	//boardno를 전달하여 select에서 where boardno=boardno인 댓글만 불러오기
	public static List<CommentsVO> findAll(int boardno){
		SqlSession session = sqlSessionFactory.openSession();
		List<CommentsVO> list = session.selectList("comments.findAll", boardno);
		session.close();
		return list;
	}

	public static int getTotalRecord() {
		int totalRecord;
		SqlSession session = sqlSessionFactory.openSession();
		totalRecord = session.selectOne("comments.getTotalRecord");
        session.close();
		return totalRecord;
	}
	
}
