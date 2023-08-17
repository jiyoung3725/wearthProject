package com.example.demo.vo;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "appandvol")
public class AppAndVolVO {

	@Id
	private int appno;
	private int userNo;
	private String phone;
	private String a_status;
	private Date a_date;
	private int volunteerno;
	private String v_title;
	public int getAppno() {
		return appno;
	}
	public void setAppno(int appno) {
		this.appno = appno;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getA_status() {
		return a_status;
	}
	public void setA_status(String a_status) {
		this.a_status = a_status;
	}
	public Date getA_date() {
		return a_date;
	}
	public void setA_date(Date a_date) {
		this.a_date = a_date;
	}
	public int getVolunteerno() {
		return volunteerno;
	}
	public void setVolunteerno(int volunteerno) {
		this.volunteerno = volunteerno;
	}
	public String getV_title() {
		return v_title;
	}
	public void setV_title(String v_title) {
		this.v_title = v_title;
	}
	public AppAndVolVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AppAndVolVO(int appno, int userNo, String phone, String a_status, Date a_date, int volunteerno,
			String v_title) {
		super();
		this.appno = appno;
		this.userNo = userNo;
		this.phone = phone;
		this.a_status = a_status;
		this.a_date = a_date;
		this.volunteerno = volunteerno;
		this.v_title = v_title;
	}
	
	

}
