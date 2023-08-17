package com.example.demo.controller;

import java.io.FileOutputStream;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.db.OpinionDBManager;
import com.example.demo.repository.EducationMyBatisRepository;
import com.example.demo.repository.OpinionMyBatisRepository;
import com.example.demo.repository.TrainingRequestMyBatisRepository;
import com.example.demo.service.EducationService;
import com.example.demo.service.UserInfoService;
import com.example.demo.service.lectureService;
import com.example.demo.vo.EducationVO;
import com.example.demo.vo.LectureVO;
import com.example.demo.vo.LikedVO;
import com.example.demo.vo.OpinionVO;
import com.example.demo.vo.TrainingRequestVO;
import com.example.demo.vo.UsersVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
import lombok.Setter;

@Controller
@Setter
public class EducationController {
	
	@Autowired
	private EducationService es;
	//EducationMyBatisRepository, EducationJpaRepository
	@Autowired
	private UserInfoService us;
	@Autowired
	private lectureService ls;
	
	// list
	@GetMapping(value={ "/school/education/list/{pageNUM}", "/school/education/list/{category}/{search}/{pageNUM}"})
	public ModelAndView list( 
			@PathVariable(name = "category", required = false) String category, 
	        @PathVariable(name = "search", required = false) String search, 
	        @PathVariable(name = "pageNUM") int pageNUM,
	         HttpServletRequest request){
		
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
	    
	    // 목록
	    HttpSession session = request.getSession();
	    UsersVO u = (UsersVO)session.getAttribute("u");
		System.out.println("u : "+u);
		
		//(만약 로그인 상태라면)
		if (u != null) {
	        int userno = u.getUserno();
	        System.out.println("userno: " + userno);
	        session.setAttribute("userno", u.getUserno());
	        
	        // 좋아요한 항목 정보 가져오기 
		    List<Integer> likedEduNOs = new ArrayList();
	        // 좋아요한 항목 정보 가져와서 리스트에 저장
	        likedEduNOs = es.findLikedEducationNos(userno);
	        List<EducationVO> educationList = es.findAllEducation(map);
	        // 좋아요한 항목인지 확인하여 상태 설정
	   	    for (EducationVO edu : educationList) {
	   	    	if (likedEduNOs.contains(edu.getEduNO())) {
	   	    		 edu.setLiked(edu.isLiked(likedEduNOs));
	   	        }
	   	    }
	   	  mav.addObject("list", educationList);
	    }else {
	    	mav.addObject("list", es.findAllEducation(map));
	    }	 
	    mav.addObject("totalPage", EducationMyBatisRepository.totalPage);
	    
	    return mav;
	}
	
	// insertAdmin에 파일올리기 추가하기
	@GetMapping("/school/education/insertAdmin")
	public String insert() {
		return "/school/education/insertAdmin";
	}
	@PostMapping("/school/education/insertAdmin")
	public ModelAndView insert(EducationVO e, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("redirect:/school/education/list/1");
		HttpSession session = request.getSession();
		UsersVO u = (UsersVO)session.getAttribute("u");
		
		///여기서부터 다시 수정 ~~!!! 
		
		return mav;
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
	public ModelAndView detail(@PathVariable("eduNO") int eduNO, HttpServletRequest request) {
		System.out.println("교육컨트롤러(detail) 글번호 : "+ eduNO);
		ModelAndView mav = new ModelAndView("/school/education/detail");
		
		HttpSession session = request.getSession();
	    UsersVO u = (UsersVO)session.getAttribute("u");
		System.out.println("Detail u : "+u);
	
		//(만약 로그인 상태라면)
	    if (u != null) {
	        int userno = u.getUserno();
	        System.out.println("userno: " + userno);
	        session.setAttribute("userno", u.getUserno());
	        
	        // 좋아요한 항목 정보 가져오기 
		    List<Integer> likedEduNOs = new ArrayList();
	        // 좋아요한 항목 정보 가져와서 리스트에 저장
	        likedEduNOs = es.findLikedEducationNos(userno);
	        // 좋아요한 항목인지 확인하여 상태 설정
	        EducationVO educationDetail = es.findByNoEducation(eduNO);
	        if (likedEduNOs.contains(educationDetail.getEduNO())) {
	            educationDetail.setLiked(true);
	            mav.addObject("e", educationDetail);
	        }
		}else {
			mav.addObject("e", es.findByNoEducation(eduNO));
		}
	    return mav;
	}
//신청 
	// 교육 신청하기 insert (trainingRequest 테이블 공용)
	@GetMapping("/school/education/insert/{eduNO}")
	public String insertEducationApplication(Model model, @PathVariable("eduNO") int eduNO) {
		model.addAttribute("e",es.findByNoEducation(eduNO));
		model.addAttribute("eduNO",eduNO);
		return "/school/education/insert";
	}
	@PostMapping("/school/education/insert")
	public ModelAndView insertEducationApplication(TrainingRequestVO r, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("redirect:/school/education/list/1");
		
		HttpSession session = request.getSession();
		UsersVO u = (UsersVO)session.getAttribute("u");
		System.out.println("Edu insert u :"+u);
		int userno = u.getUserno();
		System.out.println("userno : "+userno);
		String path = request.getServletContext().getRealPath("school");
		System.out.println( "Edu Request path : "+path);
		String reqFile = null;
		MultipartFile uploadFile = r.getUploadFile();
		reqFile = uploadFile.getOriginalFilename();
		if(reqFile != null && !reqFile.equals("")) {
			try {
				FileOutputStream fos = new FileOutputStream(path+"/"+reqFile);
				FileCopyUtils.copy(uploadFile.getBytes(),fos);
				fos.close();
			} catch (Exception e) {
				System.out.println("Edu File upload 예외발생 :"+e.getMessage());
			}
		}else {
			reqFile = "";
		}
		r.setUserNo(userno);
		r.setReqFile(reqFile);
		
		es.insertEducationApplication(r);
		
		return mav;
	}
	
	
// 좋아요기능
	// 좋아요 추가만 처리하는 컨트롤러 메서드
	@PostMapping("/school/education/liked/insert")
	@ResponseBody
	public ResponseEntity<String> like(@RequestParam int eduNO, HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    UsersVO u = (UsersVO) session.getAttribute("u");
	    int userno = u.getUserno();

	    System.out.println("좋아요에 eduno 파라미터 : "+ eduNO);
	    HashMap<String, Object> map = new HashMap<>();
	    map.put("eduNO", eduNO);
	    map.put("userno", userno);
	  
    es.insertEducationLiked(map);
    return ResponseEntity.ok("좋아요를 추가했습니다.");
	}
	// 좋아요 취소
	@PostMapping("/school/education/liked/delete")
	@ResponseBody
	public ResponseEntity<String> unlike(@RequestParam int eduNO, HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    UsersVO u = (UsersVO) session.getAttribute("u");

	    int userno = u.getUserno();
	    System.out.println("userno : " + userno);
	    HashMap<String, Object> map = new HashMap<>();
	    map.put("eduNO", eduNO);
	    map.put("userno", userno);

	        es.deleteEducationLiked(map);
	        return ResponseEntity.ok("좋아요를 취소했습니다.");
	}
	
	
//리뷰 
	//교육리뷰 조회
	@GetMapping("/school/education/review/list/{eduNO}")
	@ResponseBody
	public List<OpinionVO> findAllEducationReview(@PathVariable(name = "eduNO") int eduNO) {
		System.out.println("교육리뷰조회 eduNO 파라미터값 :"+ eduNO);
	   return es.findAllEducationReview(eduNO);
	}
	//교육리뷰 추가
	@PostMapping("/school/education/review/insert")
	@ResponseBody
	public int insertEducationReview(@RequestParam int eduNO, @RequestParam String opinionContent, HttpServletRequest request) {
	    int re = 0; // 초기값을 0으로 설정
	    HttpSession session = request.getSession();
	    UsersVO u = (UsersVO) session.getAttribute("u");
	    
	    if (u != null) {
	        String ID = u.getNickname();
	        System.out.println("eduNO : " + eduNO);
	        System.out.println("id : " + ID);
	        System.out.println("opinionContent : " + opinionContent);

	        HashMap<String, Object> map = new HashMap<>();
	        map.put("eduNO", eduNO);
	        map.put("ID", ID);
	        map.put("opinionContent", opinionContent);
	        System.out.println("map : " + map);

	        re = es.insertEducationReview(map); // re 값을 실제 처리 결과로 설정
	    }
	    return re;
	}
	
//문의
	//교육 문의 추가
	@PostMapping("/school/education/opinion/insert")
	@ResponseBody
	public int insertEducationOpinion(int eduNO, String opinionName, String opinionContent, String opinionPwd, HttpServletRequest request) {
		int re = -1;
		HttpSession session = request.getSession();
		UsersVO u = (UsersVO) session.getAttribute("u");
		if( u != null) {
			String id = u.getNickname();
			OpinionVO o = new OpinionVO();
			o.setEduNo(eduNO);
			o.setId(id);
			o.setOpinionName(opinionName);
			o.setOpinionContent(opinionContent);
			o.setOpinionPwd(opinionPwd);
			re = es.insertEducationOpinion(o);
			System.out.println("opinion Educaiton : "+ o);
			System.out.println("opinion Educaiton re : "+ re);
		}
		return re;
	}
	//교육 문의 목록
	@GetMapping("/school/education/opinion/list/{eduNO}")
	@ResponseBody
	public List<OpinionVO> listEducationOpinion(
			Model model,
			@PathVariable(name="eduNO") int eduNO, 
			HttpServletRequest request){

		return es.findAllEducationOpinion(eduNO);
	}
	
}
