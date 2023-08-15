package com.example.demo.db;

import com.example.demo.vo.LectureVO;
import com.example.demo.vo.OrdersDetailGoodsVO;
import com.example.demo.vo.OrdersVO;
import com.example.demo.vo.UsersVO;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class AdminDBManager extends DBManager {

    public static List<UsersVO> getSearchUserList(HashMap<String, Object>map){
        System.out.println("getSearchUserList작동");
        System.out.println("맵에들어있는 값은 뭔데??" + map);
        SqlSession session = sqlSessionFactory.openSession();
        List<UsersVO> userList = session.selectList("admin.getSearchUserList", map);
        System.out.println("AdminDBManger의 List확인" + userList);
        session.close();
        return userList;
    }
    public static List<UsersVO> getTotalUserList(HashMap<String, Object>map) {

        System.out.println("getTotalUserList의 AdminDBManager작동");

        SqlSession session = sqlSessionFactory.openSession();
        List<UsersVO> userList = session.selectList("admin.getTotalUserList", map);
        System.out.println("AdminDBManger의 List확인" + userList);
        session.close();
        return userList;
    }

    public static int getSearchTotalUser(){
        int n = 0;
        SqlSession session = sqlSessionFactory.openSession();
        n = session.selectOne("admin.getSearchTotalUser");
        return n;
    }
    public static int getTotalUser(){
        int n = 0;
        SqlSession session = sqlSessionFactory.openSession();
        n = session.selectOne("admin.getTotalUser");
        System.out.println("AdminDBMANGER 활성화 : getTotalUser : " + n);
        return n;
    }

    public static int checkId(String id){
        int re = -1; // 기본값 설정
        SqlSession session = sqlSessionFactory.openSession();
        String result = session.selectOne("admin.checkId", id); // Integer로 받기
        System.out.println(result);
        if (result != null) { // null 체크
            return 1;
        }
        return re;
    }



    public static int insertUser(UsersVO u){
        int re = -1;
        SqlSession session = sqlSessionFactory.openSession();
        re = session.insert("admin.insertUser", u);
        session.commit();
        session.close();
        return re;
    }

    public static int updateUser(UsersVO u){
        int re = -1;
        SqlSession session = sqlSessionFactory.openSession();
        re = session.update("admin.updateUser", u);
        session.commit();
        session.close();
        return re;
    }
    public static int deleteUser(int userno){

        int re = -1;
        System.out.println();
        SqlSession session =sqlSessionFactory.openSession();
        re = session.delete("admin.deleteUser", userno);
        session.commit();
        session.close();
        return re;
    }
}