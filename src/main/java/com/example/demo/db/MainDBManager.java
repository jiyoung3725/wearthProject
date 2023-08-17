package com.example.demo.db;


import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.example.demo.vo.GoodsVO;

public class MainDBManager extends DBManager {
	
	// 주문 개수 조회
	public static List<GoodsVO> getPopularGoods() {
		SqlSession session = sqlSessionFactory.openSession();
		List<GoodsVO> list = session.selectList("goods.getPopularGoods");
		session.close();
		return list;
	}

}
