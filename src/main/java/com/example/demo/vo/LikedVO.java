package com.example.demo.vo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "liked")
public class LikedVO {

	@Id
	private int likedNo;
	private int userNo;
	private int boardNo;
	private int lecNO;
	private int goodsNo;
	private int eduNO;
	private Date likedDate;
	public int getLikedNo() {
		return likedNo;
	}
	public void setLikedNo(int likedNo) {
		this.likedNo = likedNo;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public int getLecNO() {
		return lecNO;
	}
	public void setLecNO(int lecNO) {
		this.lecNO = lecNO;
	}
	public int getGoodsNo() {
		return goodsNo;
	}
	public void setGoodsNo(int goodsNo) {
		this.goodsNo = goodsNo;
	}
	public int getEduNO() {
		return eduNO;
	}
	public void setEduNO(int eduNO) {
		this.eduNO = eduNO;
	}
	public Date getLikedDate() {
		return likedDate;
	}
	public void setLikedDate(Date likedDate) {
		this.likedDate = likedDate;
	}
	
}
