package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import com.example.demo.db.MypageDBManager;
import com.example.demo.vo.AddrVO;
import com.example.demo.vo.AppAndVolVO;
import com.example.demo.vo.ApplicationVO;
import com.example.demo.vo.BoardVO;
import com.example.demo.vo.CouponVO;
import com.example.demo.vo.EducationVO;
import com.example.demo.vo.GoodsVO;
import com.example.demo.vo.LectureVO;
import com.example.demo.vo.OpinionVO;
import com.example.demo.vo.OrdersDetailGoodsVO;
import com.example.demo.vo.OrdersDetailVO;
import com.example.demo.vo.OrdersVO;
import com.example.demo.vo.PaymentVO;
import com.example.demo.vo.UsersVO;

public interface MyPageService {

	// 주문 페이지수
	public int getTotalPageOrders(HashMap<String, Object> map);

	// 주문 목록 조회
	public List<OrdersVO> findOrdersByUserno(HashMap<String, Object> map);

	// 주문 조회
	public OrdersVO findOrderByOrdersNo(int ordersno);

	// 주문 상세 목록 조회
	public List<OrdersDetailGoodsVO> findOrdersDetailGoodsByOrdersNo(int ordersno);

	// 리뷰 작성
	public void insertReview(OpinionVO o);

	// 결제내역 조회
	public PaymentVO findPaymentByOrdersNo(int ordersno);

	// 쇼핑 취소
	public int deleteOrder(int ordersno);

	// 관심 상품 조회
	public List<GoodsVO> findLikedGoodsByUserno(int usersno);

	// 관심 교육 조회
	public List<EducationVO> findLikedEducationByUserno(int usersno);

	// 관심 강연 조회
	public List<LectureVO> findLikedLectureByUserno(int usersno);

	// 배송지 주소록 가져오기
	public List<AddrVO> findAddrByUserno(int userno);

	// 주문 금액 가져오기
	public long getTotalAmount(int userno);

	// 내가 쓴 글 가져오기
	public List<BoardVO> findMyBoard(HashMap<String, Object> map);

	// 내가 쓴 글 개수 가져오기
	public int getMyBoardTotal(int userno);

	// 봉사 신청 목록 가져오기
	public List<AppAndVolVO> findApplicationByUserno(int userno);

	// 봉사 취소하기
	public int cancelApplication(int appno);

	// 회원 탈퇴하기
	public int deleteByUserno(int userno);
	// 주소입력
	public int insertAddr(AddrVO a);
	// 주문하기
	public int insertOrder(OrdersVO o);
	// 결제하기
	public int insertPayment(PaymentVO p);
	
	// 쿠폰목록 가졍괴
	public List<CouponVO> findCouponByUserno(int userno);
	
	// 주문 상세 저장
	public void insertOrdersDetail(OrdersDetailVO od);
}