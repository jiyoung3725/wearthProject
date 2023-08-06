package com.example.demo.controller;

import java.security.Security;
import java.util.HashMap;

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

import com.example.demo.repository.EducationMyBatisRepository;
import com.example.demo.service.EducationService;
import com.example.demo.service.UserInfoService;
import com.example.demo.vo.EducationVO;

import jakarta.servlet.http.HttpSession;
import lombok.Setter;

@Controller
@Setter
public class EducationController {
	
	@Autowired
	private EducationService es;
	//EducationMyBatisRepository, EducationJpaRepository
	
	@Autowired
	private UserInfoService us;
	
	
	// list
	@GetMapping(value={ "/school/education/list/{pageNUM}", "/school/education/list/{category}/{search}/{pageNUM}",
			"/school/education/list/{pageNUM}/{id}","/school/education/list/{category}/{search}/{pageNUM}/{id}"})
	public ModelAndView list( 
			@PathVariable(name = "category", required = false) String category, 
	        @PathVariable(name = "search", required = false) String search, 
	        @PathVariable(name = "pageNUM") int pageNUM,
	        @PathVariable(name = "id", required = false) String id,
	        HttpSession session){
		
		System.out.println("파라미터 아이디:" +id);
	    
		ModelAndView mav = new ModelAndView("/school/education/list");
	    int start = (pageNUM - 1) * EducationMyBatisRepository.pageSize + 1;
	    int end = start + EducationMyBatisRepository.pageSize - 1;
	    System.out.println("현재페이지:"+start);
	    System.out.println("마지막페이지:"+end);
	    HashMap<String, Object> map = new HashMap<>();
	    map.put("category", category);
	    map.put("search", search);
	    map.put("start", start);
	    map.put("end", end);
	    map.put("id", id);
	    
	    // 목록 ( 모든권한 / 회원권한 )
	    if (id != null) {
	    	//로그인 한 회원의 상태유지 (로그인 정보 객체로 호출하기 위해 authentication객체 생성)
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User)authentication.getPrincipal();
			String loginId = user.getUsername();
		    System.out.println("객체로그인 아이디 : "+ loginId);
		    // user 객체를 불러와 세션 유지 ( 아이디 외의 여러가지 user 자료 불러올 수 있음)
		    session.setAttribute("user", us.findById(loginId).get());
	    	
	    	mav.addObject("list", es.findAllEducationLogin(map));
	    } else {
	    	mav.addObject("list", es.findAllEducation(map));
	    } 
	    
	    mav.addObject("totalPage", EducationMyBatisRepository.totalPage);
	    session.setAttribute("id", id);
	    return mav;
	}
	
	// insert에 파일올리기 추가하기
	@GetMapping("/school/education/insert")
	public void insert() {}
	@PostMapping("/school/education/insert")
	public String insert(EducationVO e) {
		return "redirect:/school/education/list";
	}
	
	// update에 파일올리기 추가하기
	@GetMapping("/school/education/update/{eduNO}")
	public String update(Model model, @PathVariable("eduNO")  int eduNO) {
		model.addAttribute("e",es.findByNoEducation(eduNO));
		return "/school/education/update";
	}
	@PostMapping("/school/education/update")
	public String update(EducationVO e) {
		es.updateEducation(e);
		return "redirect:/school/education/list";
	}
	
	//detail
	@GetMapping("/school/education/detail/{eduNO}")
	public ModelAndView detail(@PathVariable("eduNO") int eduNO) {
		System.out.println("교육컨트롤러(detail) 글번호 : "+ eduNO);
		ModelAndView mav = new ModelAndView("/school/education/detail");
			mav.addObject("e",es.findByNoEducation(eduNO));
		return mav;
	}
}
