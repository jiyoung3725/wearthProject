package com.example.demo.vo;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "trainingRequest")
public class TrainingRequestVO {
	
	@Id
	private int reqNO;
	private int userno;
	private String reqTitle;
	private String reqContent;
	private String reqStatus;
	private String reqApp;
	private String reqAddr;
	private String reqDate;
	private String reqCompany;
	private String reqPhone;
	@Transient
	private MultipartFile uploadFile;
	private String reqFile; 
	private Date reqSysDate;
	private String type;
	private int userNo;
	public int getReqNO() {
		return reqNO;
	}
	public void setReqNO(int reqNO) {
		this.reqNO = reqNO;
	}
	public int getUserno() {
		return userno;
	}
	public void setUserno(int userno) {
		this.userno = userno;
	}
	public String getReqTitle() {
		return reqTitle;
	}
	public void setReqTitle(String reqTitle) {
		this.reqTitle = reqTitle;
	}
	public String getReqContent() {
		return reqContent;
	}
	public void setReqContent(String reqContent) {
		this.reqContent = reqContent;
	}
	public String getReqStatus() {
		return reqStatus;
	}
	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}
	public String getReqApp() {
		return reqApp;
	}
	public void setReqApp(String reqApp) {
		this.reqApp = reqApp;
	}
	public String getReqAddr() {
		return reqAddr;
	}
	public void setReqAddr(String reqAddr) {
		this.reqAddr = reqAddr;
	}
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public String getReqCompany() {
		return reqCompany;
	}
	public void setReqCompany(String reqCompany) {
		this.reqCompany = reqCompany;
	}
	public String getReqPhone() {
		return reqPhone;
	}
	public void setReqPhone(String reqPhone) {
		this.reqPhone = reqPhone;
	}
	public MultipartFile getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	public String getReqFile() {
		return reqFile;
	}
	public void setReqFile(String reqFile) {
		this.reqFile = reqFile;
	}
	public Date getReqSysDate() {
		return reqSysDate;
	}
	public void setReqSysDate(Date reqSysDate) {
		this.reqSysDate = reqSysDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	
	

}
