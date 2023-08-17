package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.vo.ApplicationVO;
import com.example.demo.vo.VolunteerVO;

import jakarta.transaction.Transactional;

@Repository
public interface ApplicationJpaRepository extends JpaRepository<ApplicationVO, Integer> {
   
   // 신청 목록 조회
   //public List<ApplicationVO> findByUserno(int userno);
   
   // 신청 취소
   @Transactional
   @Modifying
   @Query(value = "update application set a_status = '신청 취소' where appno = ?1", nativeQuery = true)
   public int cancelApplication(int appno);
   
   //봉사 신청
   @Transactional
   @Modifying
   @Query(value = "insert into application a(a.appno, a.userno, a_status, a_date, a_consent, a.volunteerno, a.phone) values(seq_app.nextval,:#{#a.userno},'신청 완료',sysdate,'Y',:#{#a.volunteerno},:#{#a.phone})", nativeQuery = true)
   public int volunteerApply(ApplicationVO a);

}
