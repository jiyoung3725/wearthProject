package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.vo.EducationVO;
import com.example.demo.vo.TrainingRequestVO;

import jakarta.transaction.Transactional;
import lombok.Setter;
@Repository
public interface EducationJpaRepository extends JpaRepository<EducationVO, Integer>{
	
	@Modifying
    @Query(value = "INSERT INTO education e (eduNO, eduName, eduContent, eduApp, eduAddr, eduDate, educator, eduPhone, eduSysdate, eduStatus, eduFile, eduDetailFile, type) "
            + "VALUES (seq_education.nextval, :#{#e.eduTitle}, :#{#e.eduContent}, :#{#e.eduApp}, :#{#e.eduAddr}, :#{#e.eduDate}, :#{#e.educator}, :#{#e.eduPhone}, sysdate, :#{#e.eduStatus}, :#{#e.eduFile}, :#{#e.eduDetailFile},'교육')", nativeQuery = true)
    @Transactional
    public void insertEducationApplication(EducationVO e);
	
}
