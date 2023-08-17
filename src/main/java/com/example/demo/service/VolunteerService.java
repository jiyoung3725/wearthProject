package com.example.demo.service;

import java.util.HashMap;
import java.util.List;

import com.example.demo.vo.ApplicationVO;
import com.example.demo.vo.VolunteerVO;

public interface VolunteerService {
	
	//봉사 게시글 목록 불러오기 - MB
	public List<VolunteerVO> findAll(HashMap<String, Object> map);
		
	//봉사 게시글 페이징 처리하기 위한 전체 레코드 수불러오기 - MB
	public int getTotalRecord();

	//봉사하기 게시글 상세페이지 - MB
	public VolunteerVO findByVolunteerNo(int volunteerno);

	//봉사 신청 - JPA
	public int volunteerApply(ApplicationVO a);
	
	
//	관리자 페이지 담당
	//실천하기 게시글 등록 위한 다음 게시글 번호 불러오기 -> 관리자 페이
	//실천하기 게시글 수정
	//실천하기 게시글 삭제
	
}
