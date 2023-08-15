package com.example.demo.repository;

import com.example.demo.db.AdminDBManager;
import com.example.demo.db.SchoolDBManager;
import com.example.demo.vo.EducationVO;
import com.example.demo.vo.UsersVO;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Repository
public class AdminMyBatisRepository {

	public static int pageSize =10;
	public static int totalRecord;
	public static int totalPage;

	public List<UsersVO> getSearchUserList(HashMap<String, Object> map) {
		totalRecord = AdminDBManager.getTotalUser();
		totalPage = (int)Math.ceil(totalRecord / (double)pageSize);
		System.out.println("TotalUserList 전체레코드 : "+ totalRecord);
		System.out.println("TotalUserList 전체페이지 : "+ totalPage);
		return AdminDBManager.getSearchUserList(map);
	}

	public List<UsersVO> getTotalUserList(HashMap<String, Object> map) {
		totalRecord = AdminDBManager.getTotalUser();
		totalPage = (int)Math.ceil(totalRecord / (double)pageSize);
		System.out.println("TotalUserList 전체레코드 : "+ totalRecord);
		System.out.println("TotalUserList 전체페이지 : "+ totalPage);
		return AdminDBManager.getTotalUserList(map);
	}

	public int getSearchTotalUser(){
		return AdminDBManager.getSearchTotalUser();
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
}
