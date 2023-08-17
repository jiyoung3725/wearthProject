package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.db.OrdersDBManager;
import com.example.demo.vo.OrdersDetailGoodsVO;
import com.example.demo.vo.OrdersDetailVO;
import com.example.demo.vo.OrdersVO;
@Repository
public class OrdersMybatisRepository {
	
	// 주문 목록 조회
	public static List<OrdersVO> findByUserNo(int userno) {
		return OrdersDBManager.findByUserNo(userno);
	}
	// 주문 조회
	public static OrdersVO findOrdersByOrdersNo(int ordersno) {
		return OrdersDBManager.findOrdersByOrdersNo(ordersno); 
	}
	// 주문 상세 목록 조회
	public static List<OrdersDetailGoodsVO> findByOrdersNo(int ordersno) {
		return OrdersDBManager.findByOrdersNo(ordersno);
	}
	
	// 전체 주문 목록 조회
		public List<OrdersVO> findAll(){
			return OrdersDBManager.findAll();
		}
		
		// 주문 수정
		public Integer updateOrder(OrdersVO order) {
			return OrdersDBManager.updateOrders(order);
		}
		// 주문 삭제
		public Integer deleteOrder(int ordersno) {
			return OrdersDBManager.deleteOrders(ordersno);
		}
		
}
