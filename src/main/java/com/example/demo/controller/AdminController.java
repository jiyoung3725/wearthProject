package com.example.demo.controller;

import com.example.demo.repository.AdminMyBatisRepository;
import com.example.demo.service.AdminService;
import com.example.demo.service.EducationService;
import com.example.demo.service.VolunteerService;
import com.example.demo.service.lectureService;
import com.example.demo.vo.UsersVO;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@Setter
public class AdminController {


    @Autowired
    private AdminService adminService;

    @Autowired
    private EducationService educationService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private lectureService lectureService;

    @GetMapping("/admin")
    public String admin1(){
        return "admin/Main";
    }


    //*****************************DashBoard*****************************
    @GetMapping("/adminDashBoard")
    public String adminDashBoard(Model model){

        //통계표시
        //회원, 실천하기, 봉사하기, 강연, 교육, 구매, 판매금액, 신고건수, 문의게시판
        model.addAttribute("totalUser", adminService.getTotalUser());
        model.addAttribute("totalVolunteer", volunteerService.getTotalRecord());
        model.addAttribute("totalLecture", lectureService.getTotalLecture());
        model.addAttribute("totalEducation", educationService.getTotalEducation());

        return "admin/DashBoard/DashBoard";
    }

    //*****************************USER***************************//
    // 회원관리, 운영자관리 페이지

    //UserList
    @GetMapping("/adminUserList/{pageNUM}")
    public String adminUserList(Model model, @PathVariable("pageNUM") int pageNUM){

        int start = (pageNUM-1) * AdminMyBatisRepository.pageSize+1;
        int end = start + AdminMyBatisRepository.pageSize-1;

        System.out.println("adminUserList의 컨트롤러 작동");

        HashMap<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("end", end);

        model.addAttribute("totalUser", adminService.getTotalUser());
        model.addAttribute("userList", adminService.getTotalUserList(map));
        model.addAttribute("totalPage", AdminMyBatisRepository.totalPage); // 추가된 부분

        return "admin/User/UserList";
    }

    @DeleteMapping("/deleteUser/{userno}")
    @ResponseBody
    public String deleteUser(@PathVariable int userno) {
        int result = adminService.deleteUser(userno);
        System.out.println("deleteUser : "+ userno + " "+result);
        if (result > 0) {  // 삭제된 행의 수가 0보다 큰 경우
            return "success";  // 성공적으로 사용자가 삭제되면 "success" 반환
        } else {
            return "error";  // 사용자 삭제에 실패하면 "error" 반환
        }
    }
    //02 검색기능 만들기 : 검색은 이름 나이 아이디 핸드폰번호 거주지
    @DeleteMapping("/deleteSelectedUser")
    @ResponseBody
    public String deleteSelectedUser(@RequestBody List<Integer> checkedUserIds) {
        try {
            // 선택된 사용자 ID로 회원 삭제 로직 구현
            for (int userno : checkedUserIds) {
                adminService.deleteUser(userno);
            }
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    //**************************회원추가기능 ************************//
    @PostMapping("/checkId")
    @ResponseBody
    public String checkId(@RequestParam String id) {
        int result = adminService.checkId(id);

        if (result == 1)
            return "success";
        else
            return "fail";
    }

    @PostMapping("/insertUser")
    @ResponseBody
    public String insertUser(@RequestBody UsersVO u){
        int result = adminService.insertUser(u);
        System.out.println("insert됐는지확인하기 result 값은" + result);
        if(result == 1 )
            return "success";
        else
            return "fail";
    }

    @PostMapping("/updateUser")
    @ResponseBody
    public String updateUser(@RequestBody UsersVO u){
        int result = adminService.updateUser(u);
        if(result == 1)
            return "success";
        else
            return "updateUser fail";
    }
    //AdminList

    @GetMapping("/adminAdminList")
    public String adminAdminList(){
        return "admin/User/AdminList";
    }

    //*****************************Act***************************//

    //Act

    //*****************************School***************************//
    //강연관리, 교육관리, 문의게시판

    @GetMapping("/adminSchoolEducation")
    public String adminEducation(){
        return "admin/School/SchoolEducation";
    }

    @GetMapping("/adminSchoolLecture")
    public String adminLecture(){
        return "admin/School/SchoolLecture";
    }
    //Shopping

}
