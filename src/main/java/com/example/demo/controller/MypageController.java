package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.repository.AddrJpaRepository;
import com.example.demo.repository.CouponJpaRepository;
import com.example.demo.repository.MypageMybatisRepository;
import com.example.demo.repository.PaymentMybatisRepository;
import com.example.demo.repository.ReviewMyBatisRepository;

import com.example.demo.repository.UserJpaRepository;

import com.example.demo.service.MyPageService;
import com.example.demo.vo.AddrVO;
import com.example.demo.vo.CouponVO;
import com.example.demo.vo.GoodsVO;
import com.example.demo.vo.OpinionVO;
import com.example.demo.vo.OrdersDetailGoodsVO;

import com.example.demo.vo.OrdersDetailVO;
import com.example.demo.vo.UsersVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;

@Controller
@Setter
public class MypageController {

	@Autowired
	private MyPageService myPageService;

	@Autowired
	private AddrJpaRepository addrRepository;

	@Autowired
	private CouponJpaRepository couponRepository;

	@GetMapping("/mypage/shopping/list")
	public String shopping(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UsersVO u = (UsersVO) session.getAttribute("u");
		if (u == null) {
			return "/userinfo/login";
		} else {
			int userno = u.getUserno();
			model.addAttribute("order_list", myPageService.findOrdersByUserno(userno));
			return "/mypage/shopping/list";
		}
	}

	@GetMapping("/mypage/shopping/detail")
	public String detail_shopping(int ordersno, Model model) {
		List<OrdersDetailGoodsVO> list = myPageService.findOrdersDetailGoodsByOrdersNo(ordersno);
		model.addAttribute("detail_list", list);
		model.addAttribute("order", myPageService.findOrderByOrdersNo(ordersno));
		model.addAttribute("payment", myPageService.findPaymentByOrdersNo(ordersno));
		return "/mypage/shopping/detail";
	}

	@GetMapping("/mypage/shopping/liked")
	public void likedGoods(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UsersVO u = (UsersVO) session.getAttribute("u");
		List<GoodsVO> goods = myPageService.findLikedGoodsByUserno(u.getUserno());
		model.addAttribute("g", goods);

	}

	@PostMapping("/mypage/writeReview")
	public int writeReview(int goodsno, String opinionName, String opinionContent, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UsersVO u = (UsersVO) session.getAttribute("u");
		OpinionVO review = new OpinionVO();
		review.setGoodsNo(goodsno);
		review.setOpinionName(opinionName);
		review.setOpinionContent(opinionContent);
		review.setId(u.getNickname());
		return myPageService.insertReview(review);
	}

	@PostMapping("/mypage/shopping/delete")
	public String deleteOrder(int orderno) {
		myPageService.deleteOrder(orderno);
		return "redirect:/mypage/shopping/list";
	}

	@GetMapping("/mypage/act/list")
	public void act() {
	}

	@GetMapping("/mypage/edu/list")
	public void edu() {
	}

	@GetMapping("/mypage/edu/detail")
	public void detail_edu() {
	}

	@GetMapping("/mypage/update")
	public void updateForm() {
	}

	@GetMapping("/shop/addr")
	public void getAddr(HttpServletRequest request, Model model) {
		UsersVO u = (UsersVO) request.getSession().getAttribute("u");
		List<AddrVO> addr_list = addrRepository.findByUserNo(u.getUserno());
		System.out.println("addr 출력 :: " + addr_list);
		model.addAttribute("addr_list", addr_list);
	}

	// 결제페이지 조회
	@GetMapping("shop/order")
	public String order(HttpServletRequest request, Model model) {
		UsersVO u = (UsersVO) request.getSession().getAttribute("u");
		List<CouponVO> coupon_list = couponRepository.findByUserNo(u.getUserno());
		System.out.println(coupon_list);
		model.addAttribute("coupon_list", coupon_list);
		return "shop/order";
	}

}
