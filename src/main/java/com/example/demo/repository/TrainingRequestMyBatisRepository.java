package com.example.demo.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.db.DBManager;
import com.example.demo.db.SchoolDBManager;
import com.example.demo.vo.TrainingRequestVO;

@Repository
public class TrainingRequestMyBatisRepository {

	public static int pageSize =10;
	public static int totalRecord;
	public static int totalPage;
	
// TrainingRequest	
	public int getTotalRecordTrainingRequest(HashMap<String, Object> map)	{
		return SchoolDBManager.getTotalRecordTrainingRequest(map);
	}
	public List<TrainingRequestVO> findAllTrainingRequest(HashMap<String, Object> map){
		totalRecord = SchoolDBManager.getTotalRecordTrainingRequest(map);
		totalPage = (int)Math.ceil(totalRecord / (double)pageSize);
		System.out.println("강의요청 전체레코드 : "+totalRecord );
		System.out.println("강의요청 전체페이지 : "+totalPage );
		return SchoolDBManager.findAllTrainingRequest(map);
	}
	public TrainingRequestVO findByNoTrainingRequest(int reqNo) {
		return SchoolDBManager.findByNoTrainingRequest(reqNo);
	}
	
// EducationApplication
	public int getTotalRecordEducationApplication(int userno)	{
		return SchoolDBManager.getTotalRecordEducationApplication(userno);
	}
	public List<TrainingRequestVO> findAllEducationApplication(HashMap<String, Object> map){
		return SchoolDBManager.findAllEducationApplication(map);
	}
	public TrainingRequestVO findByNoEducationApplication(int reqNo) {
		return SchoolDBManager.findByNoTrainingRequest(reqNo);
	}
}
