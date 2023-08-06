package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name="ordersdetailgoods")
@Data
public class OrdersDetailGoodsVO {
	private int ordersno;
	private int goodsno;
	private int detailprice;
	private int detailcnt;
	private String goodsname;
	private int goodsprice;
	private int addpoint;
	private int shipprice;
	private String mainfname;
	
}
/*
create or replace view ordersdetailgoods
 as
 select ordersno, o.goodsno, detailprice, detailcnt, goodsname, goodsprice, addpoint, shipprice, mainfname
 from  ordersdetail o, goods g where o.goodsno=g.goodsno;

*/