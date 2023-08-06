package com.example.demo.vo;

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
	
	@ManyToOne
	@JoinColumn(name = "id", insertable = true, updatable = true)
	private UsersVO users;
	
	private String eduContent;
	private String eduStatus;
	private String eduApp;
	private String eduAddr;
	private String educator;
	private String eduPhone;
	
	//@Transient
	//private MultipartFile uploadFile;
	
	private String eduFile;
	private String eduDetailFile;
	private String eduSysdate;
}
