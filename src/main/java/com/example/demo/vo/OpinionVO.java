package com.example.demo.vo;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "opinion")
@ToString
public class OpinionVO {
	
	@Id
	private int opinionNO;
	private int eduNO;
	private int goodsNo;
	private int lecNO;

	private String ID;
	private String opinionName;
	private String opinionContent;
	private Date opinionDate;
	private boolean opinionSecret;
	private String opinionPwd;
	private String opinionStatus;
	private int reviewScore;
	private int answerNo;
	private String type;
}

