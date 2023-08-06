package com.example.demo.controller;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.UserInfoService;
import com.example.demo.service.lectureService;
import com.example.demo.vo.LectureVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class LectureController {
	
	@Autowired
	private lectureService ls;
	//LectureMyBatisRepository, LectureJpaRepository
	
	@Autowired
	private UserInfoService us;
	
	//list
	@GetMapping(value={"/school/lecture/list", "/school/lecture/list/{id}"})
	public void list(Model model,
			@PathVariable(name = "id", required = false) String id,
			HttpSession session) {
		
		System.out.println("파라미터 아이디:" +id);
		
		if(id != null) {
			//로그인 한 회원의 상태유지 (로그인 정보 객체로 호출하기 위해 authentication객체 생성)
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User)authentication.getPrincipal();
			String loginId = user.getUsername();
		    System.out.println("객체로그인 아이디 : "+ loginId);
		    // user 객체를 불러와 세션 유지 ( 아이디 외의 여러가지 user 자료 불러올 수 있음)
		    session.setAttribute("user", us.findById(loginId).get());
			model.addAttribute("list",ls.findAllLectureLogin(id));
		}else {
			model.addAttribute("list",ls.findAllLecture());
		}
		session.setAttribute("id", id);
	}
	
	
	//insert
	@GetMapping("/school/lecture/insert")
	public void insert() {}
	@PostMapping("/school/lecture/insert")
	public String insert(LectureVO l) {
		return "redirect:/school/lecture/list";
	}
	
	//update
	@GetMapping("/school/lecture/update/{lecNO}")
	public String update(Model model, @PathVariable("lecNO")int lecNO) {
		model.addAttribute("l",ls.findByNoLecture(lecNO));
		return "/school/lecture/update";
	}
	@PostMapping("/school/lecture/update")
	public String update(LectureVO l) {
		ls.updateLecture(l);
		return "redirect:/school/lecture/list";
	}
	
	//detail
	@GetMapping("/school/lecture/detail/{lecNO}")
	public ModelAndView detail(@PathVariable("lecNO")int lecNO) {
		System.out.println("교육컨트롤러(detail) 글번호 : "+ lecNO);
		ModelAndView mav = new ModelAndView("/school/lecture/detail");
		mav.addObject("l",ls.findByNoLecture(lecNO));
		return mav;
	}
}
