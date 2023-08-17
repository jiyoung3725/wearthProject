package com.example.demo.db;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.vo.BoardVO;
import com.example.demo.vo.EducationVO;
import com.example.demo.vo.LectureVO;
import com.example.demo.vo.OpinionVO;
import com.example.demo.vo.TrainingRequestVO;
import com.example.demo.vo.VolunteerVO;

import jakarta.servlet.http.HttpServletRequest;

public class VolunteerDBManager extends DBManager{

	//게시글 목록
	public static List<VolunteerVO> findAll(HashMap<String, Object> map){
		SqlSession session = sqlSessionFactory.openSession();
		List<VolunteerVO> list = session.selectList("volunteer.findAll", map);
		session.close();
		System.out.println("VolunteerDBManager 작동 완료---------------------------");
		return list;
	}

	//페이징 처리를 위한 전체 레코드 수 구하기
	public static int getTotalRecord() {
		int totalRecord;
		SqlSession session = sqlSessionFactory.openSession();
		totalRecord = session.selectOne("volunteer.getTotalRecord");
        session.close();
		return totalRecord;
	}
	
	//상세게시글
	public static VolunteerVO findByVolunteerNo(int volunteerno) {
		VolunteerVO v = null;
		SqlSession session = sqlSessionFactory.openSession();
		v = session.selectOne("volunteer.findByVolunteerNo", volunteerno);
		session.close();
		return v;
	}
		

}