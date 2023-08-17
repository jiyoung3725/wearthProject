package com.example.demo.vo;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "application")
public class ApplicationVO {

	@Id
	private int appno;
	private int userno;
	private String a_status;
	private Date a_date;
	private String a_consent;
	private int reqno;
	private int eduno;
	private int volunteerno;
	private String phone;
	public int getAppno() {
		return appno;
	}
	public void setAppno(int appno) {
		this.appno = appno;
	}
	public int getUserno() {
		return userno;
	}
	public void setUserno(int userno) {
		this.userno = userno;
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
	public String getA_consent() {
		return a_consent;
	}
	public void setA_consent(String a_consent) {
		this.a_consent = a_consent;
	}
	public int getReqno() {
		return reqno;
	}
	public void setReqno(int reqno) {
		this.reqno = reqno;
	}
	public int getEduno() {
		return eduno;
	}
	public void setEduno(int eduno) {
		this.eduno = eduno;
	}
	public int getVolunteerno() {
		return volunteerno;
	}
	public void setVolunteerno(int volunteerno) {
		this.volunteerno = volunteerno;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
}
