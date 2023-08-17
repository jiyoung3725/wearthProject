package com.example.demo.db;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

import com.example.demo.vo.AddrVO;
import com.example.demo.vo.BoardVO;
import com.example.demo.vo.EducationVO;
import com.example.demo.vo.GoodsVO;
import com.example.demo.vo.LectureVO;
import com.example.demo.vo.OrdersDetailGoodsVO;
import com.example.demo.vo.OrdersVO;
import com.example.demo.vo.PaymentVO;

public class MypageDBManager extends DBManager {
	
	// 주문 개수 조회
	public static int getTotalRecord(HashMap<String, Object> map) {
		SqlSession session = sqlSessionFactory.openSession();
		int n = session.selectOne("orders.getTotalRecord", map);
		session.close();
		return n;
	}

	// 주문 목록 조회
	public static List<OrdersVO> findByUserNo(HashMap<String, Object> map) {
		SqlSession session = sqlSessionFactory.openSession();
		List<OrdersVO> list = session.selectList("orders.findListOrders", map);
		session.close();
		return list;
	}

	// 특정 주문 정보 조회
	public static OrdersVO findOrdersByOrdersNo(int ordersno) {
		SqlSession session = sqlSessionFactory.openSession();
		OrdersVO order = session.selectOne("orders.findOrdersByOrdersNo", ordersno);
		session.close();
		return order;
	}

	// 주문 상세 내역 조회
	public static List<OrdersDetailGoodsVO> findListOrdersDetailGoodsByOrdersNo(int ordersno) {
		SqlSession session = sqlSessionFactory.openSession();
		List<OrdersDetailGoodsVO> list = session.selectList("orders.findListOrdersDetailGoods", ordersno);
		session.close();
		return list;
	}

	// 관심 상품 조회
	public static List<GoodsVO> findLikedGoodsByUserno(int usersno) {
		SqlSession session = sqlSessionFactory.openSession();
		List<GoodsVO> list = session.selectList("goods.likedGoods", usersno);
		session.close();
		return list;
	}
	// 관심 교육 조회
	public static List<EducationVO> findLikedEducationByUserNo(int usersno) {
		SqlSession session = sqlSessionFactory.openSession();
		List<EducationVO> list = session.selectList("liked.findLikedEducationByUserNo", usersno);
		session.close();
		return list;
	}
	// 관심 강연 조회
	public static List<LectureVO> findLikedLectureByUserNo(int usersno) {
		SqlSession session = sqlSessionFactory.openSession();
		List<LectureVO> list = session.selectList("liked.findLikedLectureByUserNo", usersno);
		session.close();
		return list;
	}

	// 주문 취소
	public static int deleteOrder(int ordersno) {
		SqlSession session = sqlSessionFactory.openSession(true);
		int re = session.update("orders.cancelOrder", ordersno);
		session.close();
		return re;
	}

	// 전체 관심 상품 조회
	public static long getTotal(int userno) {
		SqlSession session = sqlSessionFactory.openSession();
		long total = session.selectOne("goods.getTotal", userno);
		session.close();
		return total;
	}
	
	// 내가 쓴 글 조회
	public static List<BoardVO> findMyBoard(HashMap<String, Object> map){
		SqlSession session = sqlSessionFactory.openSession();
		List<BoardVO> list = session.selectList("board.findMyBoard", map);
		session.close();
		return list;
	}
	
	// 내가 쓴 글 갯수 조회
	public static int getMyBoardTotal(int userno) {
		SqlSession session = sqlSessionFactory.openSession();
		int total = session.selectOne("board.getMyBoardTotal", userno);
		session.close();
		return total;
	}
	
	// 인기 교육 조회
	public static List<EducationVO> getPopularEducation(){
		SqlSession session = sqlSessionFactory.openSession();
		List<EducationVO> list = session.selectList("education.getPopularEducation");
		session.close();
		return list;
	}
	
	// 주소 insert
	public static int insertAddr(AddrVO a) {
		SqlSession session = sqlSessionFactory.openSession(true);
		int addrno = session.insert("orders.insertAddr",a);
		session.close();
		return addrno;
	}
	// 주문 insert
	public static int insertOrder(OrdersVO o) {
		SqlSession session = sqlSessionFactory.openSession(true);
		int orderno = session.insert("orders.insertOrder",o);
		session.close();
		return orderno;
	}
	// 결제 insert
	public static int insertPayment(PaymentVO p) {
		SqlSession session = sqlSessionFactory.openSession(true);
		int payno = session.insert("orders.insertPayment",p);
		session.close();
		return payno;
	}

}