package com.example.demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.module.FindException;
import java.util.HashMap;

import org.apache.ibatis.io.ResolverUtil.IsA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.naming.SelfNaming;
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

import com.example.demo.service.BoardService;
import com.example.demo.service.CommentsService;
import com.example.demo.service.FilesService;
import com.example.demo.service.LikedService;
import com.example.demo.vo.BoardVO;
import com.example.demo.vo.FilesVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;

@Controller
@Setter
public class BoardController {
	
	@Autowired
	private BoardService bs;
	
	@Autowired
	private FilesService fs;
	
	@Autowired
	private CommentsService cs;
	
	@Autowired
	private LikedService ls;
	
	//페이징 처리를 위한 변수 추가
	public int pageSize = 8;
	public int totalRecord;
	public int totalPage;
	
	//실천하기 게시글 목록 + 페이징 처리
	@GetMapping(value = {"/board/list", "/board/list/{pageNum}"})
	public String boardList(Model model, @PathVariable(required = false) Integer pageNum) {
		System.out.println("process : BoardController-------------------------------------");
		
		//페이징 처리
		totalRecord = bs.getTotalRecord();
		totalPage = (int)Math.ceil(totalRecord/(double)pageSize);
		if (pageNum == null) {
			pageNum = 1;
		}
		System.out.println("totalRecord:" + totalRecord);
		System.out.println("totalPage:"+totalPage);
		System.out.println("현재페이지:"+pageNum);
		int start = (pageNum-1)*pageSize+1;	//앞페이지번호*한페이지레코드 +1이 현재 페이지의 첫번째 게시글 번호
		int end = start+pageSize-1; //현재 페이지의 첫번째 게시글번호 + 페이지사이즈(8) -1하면 마지막 게시글 번호
		System.out.println("현재페이지 첫 게시글 번호:"+start);
		System.out.println("현재페이지 마지막 게시글 번호:"+end);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		
		
		model.addAttribute("list", bs.findAll(map));
		model.addAttribute("totalPage", totalPage);		//전체 페이지 수 만큼 페이징 버튼 띄우기 위해서
		
		return "board/list";
	}
	
	
	//실천하기 게시글 등록 - JPA
	@GetMapping("/board/insert")
	public String insert(Model model, HttpSession session) {
		System.out.println("boardInsert Controller---------------------------------");
		model.addAttribute("boardno", bs.getNextNo());
		System.out.println("게시글 번호:"+bs.getNextNo());
		return "board/insert";
	}
	
	//실천하기 게시글 등록 완료 - JPA
	//사진 업로드 하지 않을 경우 default로 보여줄 값 설정해야함
	//file이 null 일 때 특정 이미지 불러오도록 설정할 예정
	@PostMapping("/board/insert")
	public ModelAndView insert(BoardVO b, FilesVO f, HttpServletRequest request) {
		System.out.println("insert POST Controller---------------------------------");		
		String path = request.getServletContext().getRealPath("/board");
		System.out.println("path:"+path);
		String fname = null;
		MultipartFile uploadFile = f.getUploadFile();
		fname = uploadFile.getOriginalFilename();
		System.out.println("fname: "+fname);
		
		if(fname != null && !fname.equals("")) {	//첨부파일이 있는 경우
			try {
				FileOutputStream fos = new FileOutputStream(path+"/"+fname);				
				FileCopyUtils.copy(uploadFile.getBytes(), fos);
				fos.close();
				
				f.setFileno(fs.getNextNo());	//파일 번호 부여
				System.out.println("파일번호 : "+fs.getNextNo());
				f.setBoardno(b.getBoardno());	//input hidden으로 게시글 번호 담겨있음..
				System.out.println("f.getBoardno:"+f.getBoardno());
				f.setFname(fname);

				bs.insert(b);	//게시글 업로드
				fs.insertInBoard(f);	//게시글의 첨부파일 업로드
				System.out.println("******************파일업로드 완료*******************");
			}catch (Exception e) {
				bs.delete(b.getBoardno());
				System.out.println("파일 업로드 예외발생:"+e.getMessage());	
			}
		}else {	//첨부파일이 없는 경우
			bs.insert(b);
		}
	
		ModelAndView mav = new ModelAndView("redirect:/board/list/1");	//게시글 작성 완료시 1페이지로 이동
		return mav;
	}
	
	//게시글 삭제, 이미지파일 삭제
	@GetMapping("/board/delete/{boardno}")
	public ModelAndView delete(@PathVariable("boardno") int boardno, HttpServletRequest request) {
		System.out.println("board delete 동작-----------------------------------------------");
		ModelAndView mav = new ModelAndView("redirect:/board/list/1");

		//첨부파일 없는 경우
		//여러개로 만들거 감안해서 list로 함
		if(fs.findByBoardno(boardno).size()!=0) {
			System.out.println("첨부파일 개수:"+fs.findByBoardno(boardno).size());
			String path = request.getServletContext().getRealPath("/board");
			String fname = fs.findByBoardno(boardno).get(0).getFname();	//추후 반복문으로 list의 size만큼 돌게 수정
			File file = new File(path+"/"+fname);
			file.delete();
			fs.deleteInBoard(boardno);
			bs.delete(boardno);
			System.out.println(boardno+"번 게시글 삭제 완료 ");
		}else {
			System.out.println("첨부파일 없음");
			bs.delete(boardno);
			System.out.println(boardno+"번 게시글 삭제 완료 ");
		}
		return mav;
	}
	
	//실천하기 게시글 수정 - JPA
	@GetMapping("/board/update/{boardno}")
	public String update(@PathVariable("boardno") Integer boardno, Model model) {
		System.out.println("boardUpdate Controller---------------------------------");
		System.out.println("수정할 게시글:"+bs.findByBoardno(boardno).getBoardno()+"****************");
		model.addAttribute("boardToUpdate", bs.findByBoardno(boardno));
		return "board/update";
	}
	
	//실천하기 게시글 수정 완료 - JPA
	@PostMapping("/board/update/{boardno}")
	public ModelAndView update(@PathVariable("boardno") Integer boardno, BoardVO b, FilesVO f, HttpServletRequest request) {
		System.out.println("boardUpdate POST Controller---------------------------------");		
		String path = request.getServletContext().getRealPath("/board");
		System.out.println("path:"+path);
		String fname = null;
		MultipartFile uploadFile = f.getUploadFile();
		fname = uploadFile.getOriginalFilename();
		System.out.println("fname: "+fname);
		
		if(fname != null && !fname.equals("")) {	//첨부파일이 있는 경우
			try {
				FileOutputStream fos = new FileOutputStream(path+"/"+fname);				
				FileCopyUtils.copy(uploadFile.getBytes(), fos);
				fos.close();
				
				f.setFileno(fs.getNextNo());	//파일 번호 부여
				System.out.println("파일번호 : "+fs.getNextNo());
				f.setBoardno(b.getBoardno());	//input hidden으로 게시글 번호 담겨있음..
				System.out.println("f.getBoardno:"+f.getBoardno());
				f.setFname(fname);
				
				bs.update(b.getB_title(), b.getB_content(), b.getBoardno());	//게시글 수정 반영
				fs.insertInBoard(f);	//게시글의 첨부파일 업로드
				System.out.println("******************파일업로드 완료*******************");
			}catch (Exception e) {
				bs.delete(b.getBoardno());
				System.out.println("파일 업로드 예외발생:"+e.getMessage());	
			}
		}else {	//첨부파일이 없는 경우
			bs.update(b.getB_title(), b.getB_content(), b.getBoardno());
		}
	
		ModelAndView mav = new ModelAndView("redirect:/board/list/1");	//게시글 수정 완료시 1페이지로 이동
		return mav;
	}
	
}
