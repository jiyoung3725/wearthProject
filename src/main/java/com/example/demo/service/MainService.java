package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db.MainDBManager;
import com.example.demo.repository.EducationJpaRepository;
import com.example.demo.repository.GoodsMybatisRepository;
import com.example.demo.repository.MypageMybatisRepository;
import com.example.demo.vo.EducationVO;
import com.example.demo.vo.GoodsVO;

import lombok.Setter;

@Service
@Setter
public class MainService {
	@Autowired
	private GoodsMybatisRepository goodsRepository;
	@Autowired
	private MypageMybatisRepository myPageRepository;	
	
	public List<GoodsVO> getPopularGoods() {
		return goodsRepository.getPopularGoods();
	}
	
	public List<EducationVO> getPopularEducation(){
		return myPageRepository.getPopularEducation();
	}

}
