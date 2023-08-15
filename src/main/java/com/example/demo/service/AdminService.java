package com.example.demo.service;

import com.example.demo.repository.AdminMyBatisRepository;
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

    public int getSearchTotalUser(){
        return repository.getSearchTotalUser();
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
}