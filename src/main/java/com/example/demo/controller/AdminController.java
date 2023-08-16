package com.example.demo.controller;

import com.example.demo.repository.AdminMyBatisRepository;
import com.example.demo.repository.EducationMyBatisRepository;
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
    public String adminUserList(Model model, @PathVariable("pageNUM") int pageNUM,
                                @RequestParam(value = "u_name", required = false) String u_name,
                                @RequestParam(value = "id", required = false) String id,
                                @RequestParam(value = "age", required = false) String age,
                                @RequestParam(value = "phone", required = false) String phone,
                                @RequestParam(value = "residence", required = false) String residence,
                                @RequestParam(value = "gender", required = false) String gender){

        int start = (pageNUM - 1) * AdminMyBatisRepository.pageSize + 1;
        int end = start + AdminMyBatisRepository.pageSize - 1;

        HashMap<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("end", end);

        System.out.println("이름좀보자" + u_name);
        System.out.println("컨트롤러에서 검색기능 동작됨");
        if (u_name != null || id != null || age != null || phone != null || residence != null || gender != null) {

            map.put("u_name", u_name);
            map.put("id", id);
            map.put("age", age);
            map.put("phone", phone);
            map.put("residence", residence);
            map.put("gender", gender);

            model.addAttribute("userList", adminService.getSearchUserList(map));
            model.addAttribute("totalUser", adminService.getSearchTotalUser(map));
            model.addAttribute("totalPage", AdminMyBatisRepository.totalPage);
            System.out.println("totalUser몇명이야?" + adminService.getSearchTotalUser(map));
        } else {
            System.out.println("adminUserList의 컨트롤러 작동");
            model.addAttribute("totalUser", adminService.getTotalUser());
            model.addAttribute("userList", adminService.getTotalUserList(map));
            model.addAttribute("totalPage", AdminMyBatisRepository.totalPage);
        }
        return "admin/User/UserList";
    }

//    @GetMapping("/adminUserList/search/{pageNUM}")
//    public String adminSearchUserList(Model model, @PathVariable("pageNUM") int pageNUM,
//                                @RequestParam(value = "u_name", required = false) String u_name,
//                                @RequestParam(value = "id", required = false) String id,
//                                @RequestParam(value = "age", required = false) String age,
//                                @RequestParam(value = "phone", required = false) String phone,
//                                @RequestParam(value = "residence", required = false) String residence,
//                                @RequestParam(value = "gender", required = false) String gender) {
//
//        int start = (pageNUM - 1) * AdminMyBatisRepository.pageSize + 1;
//        int end = start + AdminMyBatisRepository.pageSize - 1;
//
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("start", start);
//        map.put("end", end);
//
//        System.out.println("이름좀보자" + u_name);
//        System.out.println("컨트롤러에서 검색기능 동작됨");
//        map.put("u_name", u_name);
//        map.put("id", id);
//        map.put("age", age);
//        map.put("phone", phone);
//        map.put("residence", residence);
//        map.put("gender", gender);
//
//        model.addAttribute("userList", adminService.getSearchUserList(map));
//        model.addAttribute("totalPage", AdminMyBatisRepository.totalPage);
//        model.addAttribute("totalUser",adminService.getSearchTotalUser());
//        return "success";
//    }


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

    @GetMapping("/adminLectureList/{pageNUM}")
    public String adminLectureList(Model model, @PathVariable("pageNUM") int pageNUM){

        int start = (pageNUM - 1) * AdminMyBatisRepository.pageSize + 1;
        int end = start + AdminMyBatisRepository.pageSize - 1;

        HashMap<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("end", end);

        System.out.println("컨트롤러에서 검색기능 동작됨");
        System.out.println("adminLectureList작동 컨트롤러 작동");
        model.addAttribute("totalleu", adminService.getTotalLecture());
        model.addAttribute("lectureList", adminService.getTotalLectureList(map));

        model.addAttribute("totalPage", AdminMyBatisRepository.totalPage);
        return "admin/School/SchoolLecture";
    }

    @GetMapping(value = {"/adminEducationList/{pageNUM}"})
    public String adminEducationList(Model model, @PathVariable("pageNUM") int pageNUM){

        int start = (pageNUM - 1) * EducationMyBatisRepository.pageSize + 1;
        int end = start + EducationMyBatisRepository.pageSize - 1;

        HashMap<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("end", end);

        System.out.println("컨트롤러에서 검색기능 동작됨");
        System.out.println("adminEducationList의 컨트롤러 작동");
        model.addAttribute("totaledu", educationService.getTotalEducation());
        model.addAttribute("educationList", adminService.getTotalEducationList(map));
        model.addAttribute("totalPage", AdminMyBatisRepository.totalPage);
        return "admin/School/SchoolEducation";
    }


    @GetMapping("/adminSchooltrainingRequest")
    public String admintrainingRequest(){
        return "admin/School/SchooltrainingRequest";
    }
    //Shopping

}
