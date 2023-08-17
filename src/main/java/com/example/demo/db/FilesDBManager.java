package com.example.demo.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.example.demo.vo.BoardVO;
import com.example.demo.vo.FilesVO;

public class FilesDBManager extends DBManager{

	public static FilesVO findFile() {
		SqlSession session = sqlSessionFactory.openSession();
		FilesVO f = session.selectOne("files.findFile");
		session.close();
		return f;
	}
	
	public static int getTotalRecord() {
		int totalRecord;
		SqlSession session = sqlSessionFactory.openSession();
		totalRecord = session.selectOne("files.getTotalRecord");
		session.close();
		return totalRecord;
	}
	
	public static List<FilesVO> findByBoardno(int boardno) {
		List<FilesVO> list;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("files.findByBoardno", boardno);
		System.out.println("DBMANAGER:"+list+"***********************");
		session.close();
		return list;
	}
	
	
	
}
