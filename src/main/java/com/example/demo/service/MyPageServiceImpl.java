package com.example.demo.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.AddrJpaRepository;
import com.example.demo.repository.AppJoinVolJpaRepository;
import com.example.demo.repository.ApplicationJpaRepository;
import com.example.demo.repository.CouponJpaRepository;
import com.example.demo.repository.MypageMybatisRepository;
import com.example.demo.repository.OrdersDetailJpaRepository;
import com.example.demo.repository.PaymentMybatisRepository;
import com.example.demo.repository.ReviewMyBatisRepository;
import com.example.demo.repository.UserJpaRepository;
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

import lombok.Setter;


@Service
@Setter
public class MyPageServiceImpl implements MyPageService {
	@Autowired
	private MypageMybatisRepository mypageRepository;
	@Autowired
	private PaymentMybatisRepository paymentRepository;
	@Autowired
	private ReviewMyBatisRepository reviewRepository;
	@Autowired
	private AddrJpaRepository addrRepository;
	@Autowired
	private ApplicationJpaRepository applicationRepository;
	@Autowired
	private AppJoinVolJpaRepository appandvolRepository;
	@Autowired
	private UserJpaRepository userRepository;
	@Autowired
	private CouponJpaRepository couponRepository;
	@Autowired
	private OrdersDetailJpaRepository ordersDetailRepository;

	// 주문 취소
	@Override
	public int deleteOrder(int ordersno) {
		return mypageRepository.deleteOrder(ordersno);
	}

	@Override
	public OrdersVO findOrderByOrdersNo(int ordersno) {
		return mypageRepository.findOrderByOrdersNo(ordersno);
	}

	@Override
	public List<OrdersDetailGoodsVO> findOrdersDetailGoodsByOrdersNo(int ordersno) {
		return mypageRepository.findOrdersDetailGoodsByOrdersNo(ordersno);
	}
	// 리뷰 쓰기
	@Override
	public void insertReview(OpinionVO o) {
		reviewRepository.insertReview(o);
	}

	@Override
	public PaymentVO findPaymentByOrdersNo(int ordersno) {
		return paymentRepository.findByOrdersNo(ordersno);
	}

	@Override
	public List<GoodsVO> findLikedGoodsByUserno(int usersno) {
		List<GoodsVO> list = mypageRepository.findLikedGoodsByUserno(usersno);
		System.out.println(list);
		return list;
	}

	@Override
	public List<AddrVO> findAddrByUserno(int userno) {
		return addrRepository.findByUserno(userno);
	}
	// 주문 금액 불러오기
	@Override
	public long getTotalAmount(int userno) {
		return mypageRepository.getTotal(userno);
	}
	// 주문 페이지 수
	@Override
	public int getTotalPageOrders(HashMap<String, Object> map) {
		return mypageRepository.getTotalRecordOrders(map);
	}
	// 주문 목록 조회
	@Override
	public List<OrdersVO> findOrdersByUserno(HashMap<String, Object> map) {
		return mypageRepository.findOrdersByUserNo(map);
	}

	// 관심 교육 조회
	@Override
	public List<EducationVO> findLikedEducationByUserno(int usersno) {
		return mypageRepository.findLikedEducationByUserno(usersno);
	}
	
	// 관심 강연 조회
	@Override
	public List<LectureVO> findLikedLectureByUserno(int usersno) {
		return mypageRepository.findLikedLectureByUserno(usersno);
	}

	// 내가 쓴 글
	@Override
	public List<BoardVO> findMyBoard(HashMap<String, Object> map) {
		return mypageRepository.findMyBoard(map);
	}

	@Override
	public int getMyBoardTotal(int userno) {
		return mypageRepository.getMyBoardTotal(userno);
	}

	// 신청 내역 찾기
	@Override
	public List<AppAndVolVO> findApplicationByUserno(int userno) {
		return appandvolRepository.findByUserNo(userno);
	}

	// 신청 취소하기
	@Override
	public int cancelApplication(int appno) {
		return applicationRepository.cancelApplication(appno);
	}

	// 회원 탈퇴하기
	@Override
	public int deleteByUserno(int userno) {
		return userRepository.deleteByUserno(userno);
	}

	@Override
	public int insertOrder(OrdersVO o) {
		return mypageRepository.insertOrder(o);
	}

	@Override
	public int insertPayment(PaymentVO p) {
		return mypageRepository.insertPayment(p);
	}

	@Override
	public int insertAddr(AddrVO a) {
		return mypageRepository.insertAddr(a);
	}

	@Override
	public List<CouponVO> findCouponByUserno(int userno) {
		return couponRepository.findByUserNo(userno);
	}

	@Override
	public void insertOrdersDetail(OrdersDetailVO od) {
		ordersDetailRepository.insertOrdersDetialGoods(od);
	}

}
