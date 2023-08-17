package com.example.demo.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.db.MypageDBManager;
import com.example.demo.vo.AddrVO;
import com.example.demo.vo.BoardVO;
import com.example.demo.vo.EducationVO;
import com.example.demo.vo.GoodsVO;
import com.example.demo.vo.LectureVO;
import com.example.demo.vo.OrdersDetailGoodsVO;
import com.example.demo.vo.OrdersVO;
import com.example.demo.vo.PaymentVO;
@Repository
public class MypageMybatisRepository {
	
	public static int OrderspageSize =10;
	public static int OrderstotalRecord;
	public static int OrderstotalPage;
	
	public static int BoardpageSize =8;
	public static int BoardtotalRecord;
	public static int BoardtotalPage;
	
	// 기본 래코드수
	public int getTotalRecordOrders(HashMap<String, Object> map) {
		return MypageDBManager.getTotalRecord(map);
	}
	// 주문 목록 조회
	public List<OrdersVO> findOrdersByUserNo(HashMap<String, Object> map) {
		OrderstotalRecord = getTotalRecordOrders(map);
		OrderstotalPage = (int)Math.ceil((double)OrderstotalRecord / OrderspageSize);
		System.out.println("orders 전체레코드 : "+ OrderstotalRecord);
		System.out.println("orders 전체페이지 : "+ OrderstotalPage);
		return MypageDBManager.findByUserNo(map);
	}
	// 주문 조회
	public OrdersVO findOrderByOrdersNo(int ordersno) {
		return MypageDBManager.findOrdersByOrdersNo(ordersno); 
	}
	// 주문 상세 목록 조회
	public List<OrdersDetailGoodsVO> findOrdersDetailGoodsByOrdersNo(int ordersno) {
		return MypageDBManager.findListOrdersDetailGoodsByOrdersNo(ordersno);
	}
	
	// 주문취소
	public int deleteOrder(int ordersno) {
		return MypageDBManager.deleteOrder(ordersno);
	}
	
	// 관심 상품 조회
	public List<GoodsVO> findLikedGoodsByUserno(int usersno){
		return MypageDBManager.findLikedGoodsByUserno(usersno);
	}
	// 관심 교육 조회
	public List<EducationVO> findLikedEducationByUserno(int usersno){
		return MypageDBManager.findLikedEducationByUserNo(usersno);
	}
	// 관심 강연 조회
	public List<LectureVO> findLikedLectureByUserno(int usersno){
		return MypageDBManager.findLikedLectureByUserNo(usersno);
	}
	
	public long getTotal(int userno) {
		return MypageDBManager.getTotal(userno);
	}
	
	// 내가 쓴 글 조회
	public List<BoardVO> findMyBoard(HashMap<String, Object> map){
		BoardtotalRecord = getMyBoardTotal((Integer)map.get("userno"));
		BoardtotalPage = (int)Math.ceil((double)BoardtotalRecord / BoardpageSize);
		System.out.println("map : "+ map);
		System.out.println("board 전체레코드 : "+ BoardtotalRecord);
		System.out.println("board 전체페이지 : "+ BoardtotalPage);
		System.out.println("board : "+MypageDBManager.findMyBoard(map));
		return MypageDBManager.findMyBoard(map);
	}
	// 내가 쓴 글 갯수 조회
	public int getMyBoardTotal(int userno){
		return MypageDBManager.getMyBoardTotal(userno);
	}
	// 인기 교육 ㅈ회
	public List<EducationVO> getPopularEducation(){
		return MypageDBManager.getPopularEducation();
	}
	
	// 주소 입력
	public int insertAddr(AddrVO a) {
		return MypageDBManager.insertAddr(a);
	}
	// 주문하기
	public int insertOrder(OrdersVO o) {
		return MypageDBManager.insertOrder(o);
	}
	
	// 결제하기
	
	public int insertPayment(PaymentVO p) {
		return MypageDBManager.insertPayment(p);
	}
}
