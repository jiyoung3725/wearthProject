package com.example.demo.controller;

import java.io.FileOutputStream;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.repository.UserJpaRepository;
import com.example.demo.repository.UserMyBatisRepository;
import com.example.demo.service.GoodsService;
import com.example.demo.service.MyPageService;
import com.example.demo.vo.AddrVO;
import com.example.demo.vo.CouponVO;
import com.example.demo.vo.GoodsCategoryVO;
import com.example.demo.vo.GoodsVO;
import com.example.demo.vo.LikedVO;
import com.example.demo.vo.OpinionVO;
import com.example.demo.vo.OrdersDetailGoodsVO;
import com.example.demo.vo.OrdersDetailVO;
import com.example.demo.vo.OrdersVO;
import com.example.demo.vo.PaymentVO;
import com.example.demo.vo.UsersVO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;

@Controller
@Setter
public class ShopController {

	@Autowired
	private GoodsService gs;

	@Autowired
	private MyPageService myPageService;

	// 전체상품 조회 및 카테고리별 상품 조회
	@GetMapping(value = { "/shop/shopMain/{categoryNo}", "/shop/shopMain/{categoryNo}/{value}", "/shop/shopMain",
			"/shop/shopMain/{value}","/shop/shopMain/{categoryNo}/{value}/{pageNum}"})
	public ModelAndView findGoods(@PathVariable(required = false) Integer categoryNo,@PathVariable(required = false) String value, @PathVariable(value ="pageNum" ,required = false) Integer pageNum,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/shop/shopMain");
		HttpSession session = (HttpSession) request.getSession();
		session.setAttribute("categoryNo", categoryNo);
		if(value==null ) {
			value="liked";
			session.setAttribute("value", value);
		}
		if(pageNum==null) {
			pageNum=1;
		}
		session.setAttribute("value", value);
		//페이징기능
		int totalRecord=gs.getTotalRecord(categoryNo);
		int pageSize=12;
		int totalPage = (int)Math.ceil(totalRecord/(double)pageSize);
		int start = (pageNum-1)*pageSize+1;
		int end = start+pageSize-1;
		mav.addObject("totalPage", totalPage);
		System.out.println("시작번호 : "+start);
		System.out.println("마지막번호 : "+end);
		if(totalRecord<end) {
			end = totalRecord;
		}
		
		//select 정렬기능
		if (categoryNo == 1 ) {
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("categoryNo", categoryNo);
			map.put("value", value);
			map.put("start", start);
			map.put("end", end);
			mav.addObject("list", gs.findGoods(map));
			
		} else if (categoryNo != 1 ) {
			session.setAttribute("categoryNo", categoryNo);
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("categoryNo", categoryNo);
			map.put("value", value);
			map.put("start", start);
			map.put("end", end);
			mav.addObject("list", gs.findByCategoryNo(map));
		}
		UsersVO user = (UsersVO)session.getAttribute("u");
		if(user!=null) {
		int userNo = user.getUserno();
		session.setAttribute("userNo", userNo);
		}
		return mav;
	}

	// 상품 상세조회 / 리뷰&문의 조회
	@GetMapping("/shop/detail")
	public void detailGoods(@RequestParam Integer goodsNo, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UsersVO user = (UsersVO)session.getAttribute("u");
		if(user !=null) {
		model.addAttribute("id",user.getId());
		model.addAttribute("userNo",user.getUserno());
		}else {
			model.addAttribute("id", null);
		}
		
		model.addAttribute("g", gs.detailGoods(goodsNo));
		model.addAttribute("review", gs.selectShopReview(goodsNo));
		model.addAttribute("qna", gs.selectShopOpinion(goodsNo));
		model.addAttribute("category", gs.findCategory(goodsNo));
	}

	
	//좋아요 버튼 클릭 시 db 추가
	@PostMapping("/insertGoodsLiked")
	@ResponseBody
	public void insertGoodsLiked(HttpServletRequest request,Model model) {
		HashMap<String, Object> map = new HashMap<String,Object>();
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		int goodsNo = Integer.parseInt(request.getParameter("goodsNo"));
		map.put("userNo", userNo);
		map.put("goodsNo", goodsNo);
		model.addAttribute(gs.insertGoodsLiked(map));
	}
	
	//좋아요 취소
	@PostMapping("/deleteGoodsLiked")
	@ResponseBody
	public void deleteGoodsLiked(Model model, HttpServletRequest request) {
		HashMap< String, Object> map = new HashMap<String, Object>();
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		int goodsNo = Integer.parseInt(request.getParameter("goodsNo"));
		map.put("userNo", userNo);
		map.put("goodsNo", goodsNo);
		model.addAttribute(gs.deleteGoodsLiked(map));
	}
	
	//유저의 좋아요목록 조회
	@PostMapping("/findLikedGoodsByUserNo")
	@ResponseBody
	public List<LikedVO> findLikedGoodsByUserNo(HttpServletRequest request) {
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		List<LikedVO> list = gs.findLikedGoodsByUserNo(userNo);
		return list;
	}
	
	
	//장바구니 추가
	@RequestMapping("/insertCart")
	@ResponseBody
	public void insertCart(Model model,HttpServletRequest request) {
	int cartCnt = Integer.parseInt(request.getParameter("cnt"));
	int goodsNo = Integer.parseInt(request.getParameter("goodsNo"));
	int userNo = Integer.parseInt(request.getParameter("userNo")) ;
	HashMap<String, Object> map = new HashMap<String, Object>();
	map.put("cartCnt", cartCnt);
	map.put("goodsNo", goodsNo);
	map.put("userNo", userNo);
	System.out.println("맵!!!"+map);
	model.addAttribute(gs.insertCart(map));
	}
	
	//유저별 장바구니 조회
	@GetMapping("/shop/cart/{userNo}")
	public ModelAndView detailCart(@PathVariable int userNo) {
		ModelAndView mav = new ModelAndView("shop/cart");
		mav.addObject("cart",gs.findCartByUserNo(userNo));
		return mav;
	}
	//품절된 장바구니 품목 삭제
	@PostMapping("/deleteCartByGoodsStock")
	@ResponseBody
	public void deleteCartByGoodsStock(Model model, HttpServletRequest request) {
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		model.addAttribute(gs.deleteCartByGoodsStock(userNo));
		System.out.println("품절 삭제 : "+userNo);
		
	}
	
	//선택한 장바구니 품목 삭제
	@PostMapping("/deleteCartByGoodsNo")
	@ResponseBody
	public void deleteCartByGoodsNo(HttpServletRequest request, Model model) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int goodsNo = Integer.parseInt(request.getParameter("goodsNo"));
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		map.put("goodsNo", goodsNo);
		map.put("userNo", userNo);
		model.addAttribute(gs.deleteCartByGoodsNo(map));
		}
	
	
	
	//장바구니 수량변경
	@PostMapping("/updateCartCnt")
	@ResponseBody
	public void updateCartCnt(HttpServletRequest request, Model model) {
		System.out.println("수량변경 컨트롤러 동작함!");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", Integer.parseInt(request.getParameter("userNo")));
		map.put("goodsNo", Integer.parseInt(request.getParameter("goodsNo")));
		map.put("cartCnt", Integer.parseInt(request.getParameter("cartCnt")));
		System.out.println("map:"+map);
		model.addAttribute(gs.updateCartCnt(map));
	}
	
	
	//문의글 등록
	@PostMapping("/shop/insertQNA")
	@ResponseBody
	public void insertShopQNA(HttpServletRequest request) {
		System.out.println("문의 컨트롤러 동작!!!!!!!!!!!!");
		int goodsNo = Integer.parseInt(request.getParameter("goodsNo"));
		HttpSession session = request.getSession();
		UsersVO user = (UsersVO)session.getAttribute("u");
		String id = user.getId();
		String opinionName = request.getParameter("opinionName");
		String opinionContent = request.getParameter("opinionContent");
		String opinionPwd = request.getParameter("opinionPwd");
		System.out.println("아이디"+id);
		System.out.println("상품번호"+goodsNo);
		System.out.println("상품번호"+opinionName);
		System.out.println("상품번호"+opinionContent);
		System.out.println("상품번호"+opinionPwd);
		HashMap<String, Object>map =new HashMap<String,Object>();
		map.put("id", id);
		map.put("goodsNo",goodsNo);
		map.put("opinionName",opinionName);
		map.put("opinionContent",opinionContent);
		map.put("opinionPwd", opinionPwd) ;
		gs.insertShopQNA(map);
		
	}
	
	//문의글 삭제
	@PostMapping("/shop/deleteQNA")
	@ResponseBody
	public void deleteShopQNA() {
		
	}
	
	//문의글 수정
	@PostMapping("/shop/updateQNA")
	@ResponseBody
	public void updateQNA() {
		
	}
	
	//하나만 구매하기 
	@RequestMapping("/shop/order/{goodsNo}/{userNo}/{cnt}")
	public String order(Model model,@PathVariable int goodsNo,@PathVariable int userNo,@PathVariable int cnt, HttpServletRequest request) {
		
		// 서현
		UsersVO u = (UsersVO) request.getSession().getAttribute("u");
		List<CouponVO> coupon_list = myPageService.findCouponByUserno(u.getUserno());
		String formattedPhone = u.getPhone().substring(0, 3) + '-' + u.getPhone().substring(3, 7) + '-'
				+ u.getPhone().substring(7);
		model.addAttribute("coupon_list", coupon_list);
		model.addAttribute("formattedPhone", formattedPhone);
		// 서현
		model.addAttribute("cnt",cnt);
		model.addAttribute("buy",gs.detailGoods(goodsNo));
		model.addAttribute("total", cnt*gs.detailGoods(goodsNo).getGoodsPrice());
		model.addAttribute("count",null);
		Gson json = new Gson();
		String jsonGood = json.toJson(gs.detailGoods(goodsNo));
		System.out.println("jsonGood : "+jsonGood);
		model.addAttribute("jsonGood",jsonGood);
		return "shop/order";
	}
	//여러개 구매하기
	@RequestMapping("/shop/orders/{userNo}")
	public String orders(@PathVariable int userNo,Model model, HttpServletRequest request) {
		// 서현
		UsersVO u = (UsersVO) request.getSession().getAttribute("u");
		List<CouponVO> coupon_list = myPageService.findCouponByUserno(u.getUserno());
		String formattedPhone = u.getPhone().substring(0, 3) + '-' + u.getPhone().substring(3, 7) + '-'
				+ u.getPhone().substring(7);
		model.addAttribute("coupon_list", coupon_list);
		model.addAttribute("total", myPageService.getTotalAmount(u.getUserno()));
		model.addAttribute("formattedPhone", formattedPhone);
		// 서현
		model.addAttribute("products", gs.findCartByUserNo(userNo));
		model.addAttribute("count", 1);
		Gson json = new Gson();
		String jsonGoods = json.toJson(gs.findCartByUserNo(userNo));
		System.out.println("jsonGoods : "+jsonGoods);
		model.addAttribute("jsonGoods",jsonGoods);
		return "shop/order";
	}
	
	
	
	//상품등록화면
	@GetMapping("/shop/insertGoods")
	public String insertGoodsForm() {
		return "shop/insertGoods";
	}
	
	

	@PostMapping("/shop/insertOrder")
	@ResponseBody
	public int insertOrder(OrdersVO o) {
		System.out.println(o);
		myPageService.insertOrder(o);
		int orderno = o.getOrdersNo();
		System.out.println("orderno!!" + orderno);
		return orderno;
	}

	@PostMapping("/shop/insertAddr")
	@ResponseBody
	public int insertAddr(AddrVO a) {
		System.out.println(a);
		myPageService.insertAddr(a);
		int addrno = a.getAddrno();
		return addrno;
	}

	@PostMapping("/shop/insertPayment")
	@ResponseBody
	public int insertPayment(PaymentVO p) {
		System.out.println(p);
		myPageService.insertPayment(p);
		int payno = p.getPayno();
		return payno;
	}
	@PostMapping("/shop/insertDetailOrder")
	@ResponseBody
	public boolean insertDetailOrder(OrdersDetailVO od) {
		myPageService.insertOrdersDetail(od);
		System.out.println("od : " + od);
		return od!=null?true:false;
	}

	@GetMapping("/shop/addr")
	public void getAddr(HttpServletRequest request, Model model) {
		UsersVO u = (UsersVO) request.getSession().getAttribute("u");
		List<AddrVO> addr_list = myPageService.findAddrByUserno(u.getUserno());
		System.out.println("addr 출력 :: " + addr_list);
		model.addAttribute("addr_list", addr_list);
	}
	//상품등록
//	@PostMapping("/shop/insertGoods")
//	public ModelAndView insertGoods(GoodsVO g,HttpServletRequest request) {
//		ModelAndView mav = new ModelAndView("redirect:/shop/insertGoods");
//		String path = request.getServletContext().getRealPath("images/shop");
//		System.out.println("path!"+path);
//		MultipartFile file1 = g.getFile1();
//		MultipartFile file2 = g.getFile2();
//		MultipartFile file3 = g.getFile3();
//		String mainFname =file1.getOriginalFilename();
//		String addFname =file2.getOriginalFilename();
//		String infoFname =file3.getOriginalFilename();
//		try {
//			byte[] data1 = file1.getBytes(); 
//			byte[] data2 = file2.getBytes(); 
//			byte[] data3 = file3.getBytes(); 
//			mainFname = file1.getOriginalFilename();
//			addFname = file2.getOriginalFilename();
//			infoFname = file3.getOriginalFilename();
//			FileOutputStream fos1 = new FileOutputStream(path+"/"+mainFname);
//			FileOutputStream fos2 = new FileOutputStream(path+"/"+addFname);
//			FileOutputStream fos3 = new FileOutputStream(path+"/"+infoFname);
//			fos1.write(data1);
//			fos2.write(data2);
//			fos3.write(data3);
//			fos1.close();
//			fos2.close();
//			fos3.close();
//			g.setMainFname(mainFname);
//			g.setAddFname(addFname);
//			g.setInfoFname(infoFname);
//		}catch(Exception e){
//			System.out.println("uploadFile 예외발생"+e.getMessage());
//		}
//		g.setMainFname(mainFname);
//		g.setAddFname(addFname);
//		g.setInfoFname(infoFname);
//		try {
//			
//		}catch (Exception e) {
//			System.out.println("등록 실패"+e.getMessage());
//		}	
//		return mav;
//		
//	}
	
	
	
}

