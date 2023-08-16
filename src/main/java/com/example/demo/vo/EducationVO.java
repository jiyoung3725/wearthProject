package com.example.demo.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "education")
public class EducationVO {
	
	
	@Id
	private int eduNO;
	private String eduName;
	
	private String eduContent;
	private String eduStatus;
	private String eduApp;
	private String eduAddr;
	private String educator;
	private String eduPhone;
	
	// 기본 file, 상세 페이지file MultipartFile 추가
//	MultipartFile uploadFile;
//	MultipartFile uploadDetailFile;
	private String eduFile;
	private String eduDetailFile; 
	private String eduSysdate;
	private boolean liked;
	
	 // 좋아요 상태 설정 메소드
    public void setLiked(boolean liked) {
        this.liked = liked;
    }
	// EducationVO 클래스에 isLiked 메소드 추가
	public boolean isLiked(List<Integer> likedEduNOs) {
	    return likedEduNOs.contains(this.eduNO);
	}
}
