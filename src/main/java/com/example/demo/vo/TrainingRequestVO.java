package com.example.demo.vo;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
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
}
