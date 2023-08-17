package com.example.demo.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.db.DBManager;
import com.example.demo.db.SchoolDBManager;
import com.example.demo.db.ShopDBManager;
import com.example.demo.vo.OpinionVO;

@Repository
public class OpinionMyBatisRepository {

	public static int pageSize =10;
	public static int totalRecord;
	public static int totalPage;
	
	public List<OpinionVO> findAllOpinion(HashMap<String, Object> map){
		totalRecord = SchoolDBManager.getTotalRecordOpinion();
		totalPage = (int)Math.ceil(totalRecord / (double)pageSize);
		return SchoolDBManager.findAllOpinion(map);
	}
	
	public OpinionVO findByNoOpinion(int opinionNo) {
		return SchoolDBManager.findByNoOpinion(opinionNo);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	//=======================쇼핑========================================
	
	//쇼핑 문의 조회
	public static List<OpinionVO> selectShopOpinion(int goodsNo){
		return ShopDBManager.selectShopOpinion(goodsNo);
	}
	
	//쇼핑 리뷰 조회
	public static List<OpinionVO> selectShopReview(int goodsNo){
		return ShopDBManager.selectShopReview(goodsNo);
	}
	
	//쇼핑 문의글 작성
	public static int insertShopQNA(HashMap<String, Object>map) {
		return ShopDBManager.insertShopQNA(map);
	}
	
	//쇼핑 문의글 삭제
	public static int deleteShopQNA(HashMap<String, Object>map) {
		return ShopDBManager.deleteShopQNA(map);
	}
	
	//쇼핑 문의글 수정
	public static int updateShopQNA(HashMap<String, Object>map) {
		return ShopDBManager.updateShopQNA(map);
	}
}
