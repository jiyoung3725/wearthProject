package com.example.demo.db;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;


public class LikedDBManager extends DBManager{
	
	public static int checkLiked(HashMap<String, Integer> map) {
		SqlSession session = sqlSessionFactory.openSession();
		int cnt = session.selectOne("liked.checkLiked", map);
		session.close();
		return cnt;
	}
	
	public static int countLiked(int boardno) {
		SqlSession session = sqlSessionFactory.openSession();
		int cnt = session.selectOne("liked.countLiked", boardno);
		session.close();
		return cnt;
	}
}
