package com.example.demo.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.db.DBManager;
import com.example.demo.db.OpinionDBManager;
import com.example.demo.db.SchoolDBManager;
import com.example.demo.vo.OpinionVO;

@Repository
public class OpinionMyBatisRepository {
	
	//강의, 교육 전체 목록 반환
	public List<OpinionVO> findAllEducationOpinion(int eduNO){
		return OpinionDBManager.findAllEducationOpinion(eduNO);
	}
	public List<OpinionVO> findAllLectureOpinion(int lecNO){
		return OpinionDBManager.findAllLectureOpinion(lecNO);
	}
	
	//강의, 교육 해당 게시글의 문의 글의 상세 조회
	public OpinionVO findByNoEducationOpinion(HashMap<String, Object> map) {
		return OpinionDBManager.findByNoEducationOpinion(map);
	}
	public OpinionVO findByNoLectureOpinion(HashMap<String, Object> map) {
		return OpinionDBManager.findByNoLectureOpinion(map);
	}

}
