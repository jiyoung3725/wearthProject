package com.example.demo.db;

import java.util.List;
import org.apache.ibatis.session.SqlSession;

import com.example.demo.vo.OrdersDetailGoodsVO;
import com.example.demo.vo.OrdersDetailVO;
import com.example.demo.vo.OrdersVO;

public class OrdersDBManager extends DBManager {
	
	public static List<OrdersVO> findAll(){
		SqlSession session = sqlSessionFactory.openSession();
		List<OrdersVO> list = session.selectList("orders.findAll");
		session.close();
		return list;
	}
	
	public static Integer updateOrders(OrdersVO order) {
		SqlSession session = sqlSessionFactory.openSession(true);
		Integer re = session.update("orders.updateOrder", order);
		session.close();
		return re;
	}
	
	public static Integer deleteOrders(int ordersno) {
		SqlSession session = sqlSessionFactory.openSession(true);
		Integer re = session.delete("orders.deleteOrder", ordersno);
		session.close();
		return re;
	}
		
	
	public static List<OrdersVO> findByUserNo(int userno) {
		SqlSession session = sqlSessionFactory.openSession();
		List<OrdersVO> list = session.selectList("orders.findListOrders", userno);
		session.close();
		return list;
	}
	public static OrdersVO findOrdersByOrdersNo(int ordersno) {
		SqlSession session = sqlSessionFactory.openSession();
		OrdersVO order = session.selectOne("orders.findOrdersByOrdersNo", ordersno);
		session.close();
		return order;
	}

	public static List<OrdersDetailGoodsVO> findByOrdersNo(int ordersno) {
		SqlSession session = sqlSessionFactory.openSession();
		List<OrdersDetailGoodsVO> list = session.selectList("orders.findListOrdersDetailGoods", ordersno);
		session.close();
		return list;
	}

}