package com.example.demo.vo;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "lecture")
public class LectureVO {
	
	@Id
	private int lecNO;
	private String lecName;
	
	@ManyToOne
	@JoinColumn(name = "id", insertable = true, updatable = true)
	private UsersVO users;
	
	private String lecContent;
	private String lecStatus;
	private String lecDate;
	private String lecAddr;
	private String lecturer;
	private String lecApp;
	private String lecPhone;
	private int lecPrice;
	
	//@Transient
	//private MultipartFile uploadFile;
	
	private String lecFile;
	private String lecDetailFile;
	private Date lecSysdate;
}
