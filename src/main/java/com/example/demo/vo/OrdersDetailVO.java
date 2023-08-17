package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ordersDetail")
public class OrdersDetailVO {

	@Id
	private int ordersDetailNo;
	private int userNo;
	private int ordersNo;
	private int goodsNo;
	private int lecNo;
	private int detailCnt;
	public int getOrdersDetailNo() {
		return ordersDetailNo;
	}
	public void setOrdersDetailNo(int ordersDetailNo) {
		this.ordersDetailNo = ordersDetailNo;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public int getOrdersNo() {
		return ordersNo;
	}
	public void setOrdersNo(int ordersNo) {
		this.ordersNo = ordersNo;
	}
	public int getGoodsNo() {
		return goodsNo;
	}
	public void setGoodsNo(int goodsNo) {
		this.goodsNo = goodsNo;
	}
	public int getLecNo() {
		return lecNo;
	}
	public void setLecNo(int lecNo) {
		this.lecNo = lecNo;
	}
	public int getDetailCnt() {
		return detailCnt;
	}
	public void setDetailCnt(int detailCnt) {
		this.detailCnt = detailCnt;
	}
	@Override
	public String toString() {
		return "OrdersDetailVO [ordersDetailNo=" + ordersDetailNo + ", userNo=" + userNo + ", ordersNo=" + ordersNo
				+ ", goodsNo=" + goodsNo + ", lecNo=" + lecNo + ", detailCnt=" + detailCnt + "]";
	}
	
	
}
