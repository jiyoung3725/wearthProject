package com.example.demo.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.TrainingRequestJpaRepository;
import com.example.demo.repository.TrainingRequestMyBatisRepository;
import com.example.demo.vo.TrainingRequestVO;

import lombok.Setter;

@Service
@Setter
public class TrainingRequestService {
	@Autowired
	private TrainingRequestJpaRepository request_JPA;
	@Autowired
	private TrainingRequestMyBatisRepository request_MB;
	
	
//MB
	//강의 요청 전체 레코드 수 조회
	public int getTotalRecordTrainingRequest(HashMap<String, Object> map) {
		return request_MB.getTotalRecordTrainingRequest(map);
	}
	// 강의 요청 전체목록 조회
	public List<TrainingRequestVO> findAllTrainingRequest(HashMap<String, Object> map){
		System.out.println(" 강의요청 SERVICE map: "+map);
		return request_MB.findAllTrainingRequest(map);
	}
	// 강의 요청 상세 게시물 조회
	public TrainingRequestVO findByNoTrainingRequest(int reqNO) {
		return request_MB.findByNoTrainingRequest(reqNO);
	}
	
//JPA
	// 교육 신청하기 추가
	public void insertTrainingRequest(TrainingRequestVO r) {
		request_JPA.insertTrainingRequest(r);
	}
		
}
