package com.example.demo.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.db.SchoolDBManager;
import com.example.demo.vo.EducationVO;

@Repository
public class EducationMyBatisRepository {
	public static int pageSize =12;
	public static int totalRecord;
	public static int totalPage;
	
	// 기본 래코드수
	public int getTotalRecordEducation(HashMap<String, Object> map) {
		return SchoolDBManager.getTotalRecordEducation(map);
	}
	
	// 기본 findAll
	public List<EducationVO> findAllEducation(HashMap<String, Object> map){
		totalRecord = SchoolDBManager.getTotalRecordEducation(map);
		totalPage = (int)Math.ceil((double)totalRecord / pageSize);
		System.out.println("교육RepoMB(findAll) 전체레코드 : "+ totalRecord);
		System.out.println("교육RepoMB(findAll) 전체페이지 : "+ totalPage);
		return SchoolDBManager.findAllEducation(map);
	}
	
	public EducationVO findByNoEducation(int eduNO) {
		return SchoolDBManager.findByNoEducation(eduNO);
	}
	
}
