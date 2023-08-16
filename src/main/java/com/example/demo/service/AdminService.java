package com.example.demo.service;

import com.example.demo.repository.AdminMyBatisRepository;
import com.example.demo.vo.EducationVO;
import com.example.demo.vo.LectureVO;
import com.example.demo.vo.TrainingRequestVO;
import com.example.demo.vo.UsersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminMyBatisRepository repository;

    public List<UsersVO> getSearchUserList(HashMap<String, Object>map){
        return repository.getSearchUserList(map);
    }

    public List<UsersVO> getTotalUserList(HashMap<String, Object> map) {
        return repository.getTotalUserList(map);
    }

    public int getSearchTotalUser(HashMap<String, Object>map){
        return repository.getSearchTotalUser(map);
    }
    public int getTotalUser(){
        return repository.getTotalUser();
    }

    public int checkId(String id){
        return repository.checkId(id);
    }

    public int insertUser(UsersVO u){
        return repository.insertUser(u);
    }

    public int updateUser(UsersVO u){
        return repository.updateUser(u);
    }
    public int deleteUser(int userno) {
        return repository.deleteUser(userno);
    }

    //****************************************위얼스 학교 ************************

    //강연관리

    public int getTotalLecture(){
        return repository.getTotalLecture();
    }

    public List<LectureVO> getTotalLectureList(HashMap<String, Object> map) {
        return repository.getTotalLectureList(map);
    }

    public List<EducationVO> getTotalEducationList(HashMap<String, Object> map) {
        return repository.getTotalEducationList(map);
    }

    public List<TrainingRequestVO> getTotalTrainingRequestList(HashMap<String, Object> map) {
        return repository.getTotalTrainingRequestList(map);
    }




}