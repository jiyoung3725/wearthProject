package com.example.demo.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.db.SchoolDBManager;
import com.example.demo.repository.AddrJpaRepository;
import com.example.demo.repository.CouponJpaRepository;
import com.example.demo.repository.MypageMybatisRepository;
import com.example.demo.repository.PaymentMybatisRepository;
import com.example.demo.repository.ReviewMyBatisRepository;
import com.example.demo.repository.TrainingRequestMyBatisRepository;
import com.example.demo.repository.UserJpaRepository;
import com.example.demo.service.EducationService;
import com.example.demo.service.MyPageService;
import com.example.demo.service.UserInfoService;
import com.example.demo.service.lectureService;
import com.example.demo.vo.AddrVO;
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
import com.example.demo.vo.TrainingRequestVO;
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
	private UserInfoService userInfoService;

	@Autowired
	private AddrJpaRepository addrRepository;

	@Autowired
	private CouponJpaRepository couponRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private EducationService es;
	@Autowired
	private lectureService ls;

	@GetMapping(value = { "/mypage/shopping/list/{pageNum}", "/mypage/shopping/list" })
	public String shopping(Model model, HttpServletRequest request,
			@PathVariable(name = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "dstart", required = false, defaultValue = "19000101") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate dstart,
			@RequestParam(value = "dend", required = false, defaultValue = "99991231") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate dend) {
		// 유저 번호 불러오기
		HttpSession session = request.getSession();
		UsersVO u = (UsersVO) session.getAttribute("u");
		int userno = u.getUserno();

		// 페이징
		if (pageNum == null) {
			pageNum = 1;
		}
		int start = (pageNum - 1) * MypageMybatisRepository.OrderspageSize + 1;
		int end = start + MypageMybatisRepository.OrderspageSize - 1;

		HashMap<String, Object> map = new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		map.put("dstart", dstart);
		map.put("dend", dend);
		map.put("userno", userno);
		List<OrdersVO> order_list = myPageService.findOrdersByUserno(map);
		model.addAttribute("order_list", order_list);
		int totalPage = MypageMybatisRepository.OrderstotalPage;
		model.addAttribute("totalPage", totalPage);
		request.setAttribute("dstart", dstart);
		request.setAttribute("dend", dend);

		return "/mypage/shopping/list";

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
	public void likedGoods(Model model, HttpServletRequest request,
			@PathVariable(name = "pageNum", required = false) Integer pageNum) {
		HttpSession session = request.getSession();
		UsersVO u = (UsersVO) session.getAttribute("u");
		List<GoodsVO> goods = myPageService.findLikedGoodsByUserno(u.getUserno());
		model.addAttribute("g", goods);

	}

	@PostMapping("/mypage/writeReview")
	@ResponseBody
	public void writeReview(int goodsno, String opinionName, String opinionContent, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UsersVO u = (UsersVO) session.getAttribute("u");
		OpinionVO review = new OpinionVO();
		review.setGoodsNo(goodsno);
		review.setType("review");
		review.setOpinionName(opinionName);
		review.setOpinionContent(opinionContent);
		review.setId(u.getNickname());
		myPageService.insertReview(review);
	}

	@PostMapping("/mypage/shopping/delete")
	public String deleteOrder(int orderno) {
		myPageService.deleteOrder(orderno);
		return "redirect:/mypage/shopping/list";
	}


	@GetMapping(value = { "/mypage/board/list/{pageNum}", "/mypage/board/list" })
	public String board(Model model, HttpServletRequest request, @PathVariable(required = false) Integer pageNum) {
		// 유저 번호 불러오기
		HttpSession session = request.getSession();
		UsersVO u = (UsersVO) session.getAttribute("u");
		int userno = u.getUserno();

		// 페이징
		if (pageNum == null) {
			pageNum = 1;
		}
		int start = (pageNum - 1) * MypageMybatisRepository.BoardpageSize + 1;
		int end = start + MypageMybatisRepository.BoardpageSize - 1;

		HashMap<String, Object> map = new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		map.put("userno", userno);
		List<BoardVO> board_list = myPageService.findMyBoard(map);
		int totalPage = MypageMybatisRepository.BoardtotalPage;
		model.addAttribute("board_list", board_list);
		model.addAttribute("totalPage", totalPage);
		return "/mypage/board/list";

	}

	@GetMapping("/mypage/act/list")
	public void act(Model model, HttpServletRequest request) {
		UsersVO u = (UsersVO) request.getSession().getAttribute("u");
		System.out.println("app_list : " + myPageService.findAddrByUserno(u.getUserno()));
		model.addAttribute("app_list", myPageService.findApplicationByUserno(u.getUserno()));

	}

// 교육, 강연 좋아요 목록
	@GetMapping("/mypage/edu/likedList")
	public ModelAndView listLiked(Model model, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/mypage/edu/likedList");
		HttpSession session = request.getSession();
		UsersVO u = (UsersVO) session.getAttribute("u");// (만약 로그인 상태라면)
		if (u != null) {
			int userno = u.getUserno();
			System.out.println("userno: " + userno);
			session.setAttribute("userno", u.getUserno());
			// 교육 좋아요 목록
			List<EducationVO> edulikedList = es.findLikedEducationByUserNo(userno);
			System.out.println("edulikedList" + edulikedList);
			mav.addObject("edulikedList", edulikedList);

			List<LectureVO> leclikedList = ls.findLikedLectureByUserNo(userno);
			System.out.println("likedList" + leclikedList);
			mav.addObject("leclikedList", leclikedList);
		} else {
			mav.setViewName("redirect:/userinfo/login");
		}
		return mav;
	}

//교육, 강연 신청 조회
	// 교육 신청 조회하기
	@GetMapping("/mypage/edu/list/{pageNUM}")
	public ModelAndView findAllEducationApplication(@PathVariable("pageNUM") int pageNUM, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/mypage/edu/list");
		int pageSize = 10;
		int totalRecord;
		int totalPage;

		// user 정보
		HttpSession session = request.getSession();
		UsersVO u = (UsersVO) session.getAttribute("u");
		int userno = u.getUserno();

		// 페이징 계산
		int start = (pageNUM - 1) * pageSize + 1;
		int end = start + pageSize - 1;
		System.out.println("교육 신청 start페이지 : " + start);
		System.out.println("교육 신청 end페이지 : " + end);
		HashMap<String, Object> map = new HashMap<>();
		map.put("userno", userno);
		map.put("start", start);
		map.put("end", end);
		totalRecord = SchoolDBManager.getTotalRecordEducationApplication(userno);
		totalPage = (int) Math.ceil(totalRecord / (double) pageSize);
		System.out.println("교육신청 전체레코드 : " + totalRecord);
		System.out.println("교육신청 전체페이지 : " + totalPage);

		// 전체목록 구할 때 ( userno, start, end 를 map으로 보내기)
		List<TrainingRequestVO> requestList = es.findAllEducationApplication(map);
		mav.addObject("requestList", requestList);
		mav.addObject("totalPage", totalPage);
		return mav;
	}

	// 교육 신청 상세정보 조회하기
	@GetMapping("/mypage/edu/detail/[reqNO]")
	public ModelAndView findByNoEducationApplication(@PathVariable("reqNO") int reqNO) {
		System.out.println("교육신청 상세페이지 reqNO : " + reqNO);
		ModelAndView mav = new ModelAndView("/mypage/edu/detail");
		mav.addObject("r", es.findByNoEducationApplication(reqNO));

		return mav;
	}
	@GetMapping("/mypage/update")
	public void updateForm(Model model, HttpServletRequest request) {
		UsersVO u = (UsersVO) request.getSession().getAttribute("u");
		String formattedPhone = u.getPhone().substring(0, 3) + '-' + u.getPhone().substring(3, 7) + '-'
				+ u.getPhone().substring(7);
		model.addAttribute("formattedPhone", formattedPhone);
	}

	@PostMapping("/mypage/update")
	public String submitUpdateForm(UsersVO newUser, HttpServletRequest request) {
		System.out.println("----updateController------");
		HttpSession session = request.getSession();
		newUser.setUserno(((UsersVO) session.getAttribute("u")).getUserno());
		String pwd = newUser.getPwd();
		System.out.println("newUSer : " + newUser);
		System.out.println("pwd : |" + pwd + "|");
		if (pwd.length() != 0) {
			System.out.println("비밀번호 변경 ~~~");
			userInfoService.changePwd(newUser.getUserno(), encoder.encode(pwd));
		}
		userInfoService.updateUserInfo(newUser);
		UsersVO u = userInfoService.findByEmail(newUser.getEmail()).get();
		session.setAttribute("u", u);
		return "redirect:/mypage/update";
	}


	@PostMapping("/mypage/deleteUser")
	@ResponseBody
	public int deleteUser(int userno) {
		return myPageService.deleteByUserno(userno);
	}

}