package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.vo.TrainingRequestVO;

import jakarta.transaction.Transactional;

public interface TrainingRequestJpaRepository extends JpaRepository<TrainingRequestVO, Integer> {
	// 강의요청 TrainingRequest 신청 insert
	 @Modifying
	    @Query(value = "INSERT INTO TRAININGREQUEST (reqNO, reqTitle, reqContent, reqApp, reqAddr, reqDate, reqCompany, reqPhone, reqSysdate, userno, reqStatus, reqFile, type) "
	            + "VALUES (seq_trainingRequest.nextval,:#{#r.reqTitle}, :#{#r.reqContent}, :#{#r.reqApp}, :#{#r.reqAddr}, :#{#r.reqDate}, :#{#r.reqCompany}, :#{#r.reqPhone}, sysdate, :#{#r.userno}, '접수중', :#{#r.reqFile}, '강의요청')", nativeQuery = true)
	    @Transactional
	    void insertTrainingRequest(TrainingRequestVO r);

	    @Modifying
	    @Query(value = "INSERT INTO TRAININGREQUEST (reqNO, reqTitle, reqContent, reqApp, reqAddr, reqDate, reqCompany, reqPhone, reqSysdate, userno, reqStatus, reqFile, type) "
	            + "VALUES (seq_trainingRequest.nextval, :#{#r.reqTitle}, :#{#r.reqContent}, :#{#r.reqApp}, :#{#r.reqAddr}, :#{#r.reqDate}, :#{#r.reqCompany}, :#{#r.reqPhone}, sysdate, :#{#r.userno}, :#{#r.reqStatus}, :#{#r.reqFile}, '교육')", nativeQuery = true)
	    @Transactional
	    void insertEducationApplication(TrainingRequestVO r);
}
