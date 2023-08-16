package com.example.demo.repository;

import com.example.demo.db.AdminDBManager;
import com.example.demo.db.SchoolDBManager;
import com.example.demo.vo.EducationVO;
import com.example.demo.vo.LectureVO;
import com.example.demo.vo.TrainingRequestVO;
import com.example.demo.vo.UsersVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class AdminMyBatisRepository {

	public static int pageSize =10;
	public static int totalRecord;
	public static int totalPage;



	public List<UsersVO> getSearchUserList(HashMap<String, Object> map) {
		totalRecord = AdminDBManager.getSearchTotalUser(map);
		totalPage = (int)Math.ceil(totalRecord / (double)pageSize);
		return AdminDBManager.getSearchUserList(map);
	}

	public List<UsersVO> getTotalUserList(HashMap<String, Object> map) {
		totalRecord = AdminDBManager.getTotalUser();
		totalPage = (int)Math.ceil(totalRecord / (double)pageSize);
		return AdminDBManager.getTotalUserList(map);
	}

	public int getSearchTotalUser(HashMap<String, Object>map){
		return AdminDBManager.getSearchTotalUser(map);
	}
	public int getTotalUser(){
		return AdminDBManager.getTotalUser();
	}

	public int checkId(String id){
		return AdminDBManager.checkId(id);
	}

	public int insertUser(UsersVO u){
		return AdminDBManager.insertUser(u);
	}

	public int updateUser(UsersVO u){
		return AdminDBManager.updateUser(u);
	}
	public int deleteUser(int userno){
		return AdminDBManager.deleteUser(userno);
	}


	//*******************************위얼스 학교 *****************************

	//강연관리

	public int getTotalLecture(){
		return AdminDBManager.getTotalLecture();
	}

	public List<LectureVO> getTotalLectureList(HashMap<String, Object> map) {
		totalRecord = SchoolDBManager.getTotalLecture();
		totalPage = (int)Math.ceil(totalRecord / (double)pageSize);
		return AdminDBManager.getTotalLectureList(map);
	}

	public List<EducationVO> getTotalEducationList(HashMap<String, Object> map) {
		totalRecord = SchoolDBManager.getTotalEducation();
		totalPage = (int)Math.ceil(totalRecord / (double)pageSize);
		return AdminDBManager.getTotalEducationList(map);
	}

	public List<TrainingRequestVO> getTotalTrainingRequestList(HashMap<String, Object> map) {
		totalRecord = AdminDBManager.getTotalTrainingRequest();
		totalPage = (int)Math.ceil(totalRecord / (double)pageSize);
		return AdminDBManager.getTotalTraningRequestList(map);
	}
}
