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
	public int getEduNO() {
		return eduNO;
	}
	public void setEduNO(int eduNO) {
		this.eduNO = eduNO;
	}
	public String getEduName() {
		return eduName;
	}
	public void setEduName(String eduName) {
		this.eduName = eduName;
	}
	public String getEduContent() {
		return eduContent;
	}
	public void setEduContent(String eduContent) {
		this.eduContent = eduContent;
	}
	public String getEduStatus() {
		return eduStatus;
	}
	public void setEduStatus(String eduStatus) {
		this.eduStatus = eduStatus;
	}
	public String getEduApp() {
		return eduApp;
	}
	public void setEduApp(String eduApp) {
		this.eduApp = eduApp;
	}
	public String getEduAddr() {
		return eduAddr;
	}
	public void setEduAddr(String eduAddr) {
		this.eduAddr = eduAddr;
	}
	public String getEducator() {
		return educator;
	}
	public void setEducator(String educator) {
		this.educator = educator;
	}
	public String getEduPhone() {
		return eduPhone;
	}
	public void setEduPhone(String eduPhone) {
		this.eduPhone = eduPhone;
	}
	public String getEduFile() {
		return eduFile;
	}
	public void setEduFile(String eduFile) {
		this.eduFile = eduFile;
	}
	public String getEduDetailFile() {
		return eduDetailFile;
	}
	public void setEduDetailFile(String eduDetailFile) {
		this.eduDetailFile = eduDetailFile;
	}
	public String getEduSysdate() {
		return eduSysdate;
	}
	public void setEduSysdate(String eduSysdate) {
		this.eduSysdate = eduSysdate;
	}
	public boolean isLiked() {
		return liked;
	}
	
	
}
