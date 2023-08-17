package com.example.demo.controller;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.repository.TrainingRequestMyBatisRepository;
import com.example.demo.service.TrainingRequestService;
import com.example.demo.vo.TrainingRequestVO;
import com.example.demo.vo.UsersVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;

@Controller
@Setter
public class TrainingRequestController {
	
	@Autowired
	private TrainingRequestService rs;
	
	@GetMapping(value={"/school/trainingRequest/list/{pageNUM}", "/school/trainingRequest/list/{category}/{search}/{pageNUM}"})
	public ModelAndView list(
			@PathVariable(name = "category", required = false) String category, 
	        @PathVariable(name = "search", required = false) String search, 
			 @PathVariable(name = "pageNUM") int pageNUM,
	         HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/school/trainingRequest/list");
		//user 정보
		HttpSession session = request.getSession();
		UsersVO u = (UsersVO)session.getAttribute("u");
		//페이징 계산
				int start = (pageNUM- 1)* TrainingRequestMyBatisRepository.pageSize+1;
				int end = start+ TrainingRequestMyBatisRepository.pageSize -1;
				System.out.println("교육 신청 start페이지 : " + start);
				System.out.println("교육 신청 end페이지 : " + end);
				
				//전체목록 구할 때 ( userno, start, end 를 map으로 보내기)
				HashMap<String, Object> map = new HashMap<>();
				map.put("category", category);
			    map.put("search", search);
				map.put("start", start);
				map.put("end", end);
		//(만약 로그인 상태라면)
		if (u != null) {
			int userno = u.getUserno();
			session.setAttribute("userno", userno);
			List<TrainingRequestVO> requestList = rs.findAllTrainingRequest(map);
			mav.addObject("requestList",requestList);
		}else {
			mav.addObject("requestList",rs.findAllTrainingRequest(map));
		}
		mav.addObject("totalPage",TrainingRequestMyBatisRepository.totalPage);
		return mav;
	}
	
	@GetMapping("/school/trainingRequest/detail/{reqNO}")
	public ModelAndView findByNoTrainingRequest(@PathVariable("reqNO") int reqNO) {
		System.out.println("강의요청 상세페이지 reqNO : "+reqNO);
		ModelAndView mav = new ModelAndView("/school/trainingRequest/detail");
		mav.addObject("r",rs.findByNoTrainingRequest(reqNO));
		
		return mav;
	}
	
	//insert 강의요청 추가
		@GetMapping("/school/trainingRequest/insert")
		public String insertTrainingRequest() {
			return "/school/trainingRequest/insert";
		}
		@PostMapping("/school/trainingRequest/insert")
		public ModelAndView insertTrainingRequest(TrainingRequestVO r, HttpServletRequest request) {
			ModelAndView mav = new ModelAndView("redirect:/school/trainingRequest/list/1");
			
			HttpSession session = request.getSession();
			UsersVO u = (UsersVO)session.getAttribute("u");
			System.out.println("trainingRequest insert : "+u);
			int userno = u.getUserno();
			System.out.println("userno : "+userno);
			String path = request.getServletContext().getRealPath("school");
			System.out.println( "trainingRequest path : "+path);
			String reqFile = null;
			MultipartFile uploadFile = r.getUploadFile();
			reqFile = uploadFile.getOriginalFilename();
			if(reqFile != null && !reqFile.equals("")) {
				try {
					FileOutputStream fos = new FileOutputStream(path+"/"+reqFile);
					FileCopyUtils.copy(uploadFile.getBytes(),fos);
					fos.close();
				} catch (Exception e) {
					System.out.println("trainingRequest upload 예외발생 :"+e.getMessage());
				}
			}else {
				reqFile = "";
			}
			r.setUserNo(userno);
			r.setReqFile(reqFile);
			
			rs.insertTrainingRequest(r);
			
			return mav;
		}
}
