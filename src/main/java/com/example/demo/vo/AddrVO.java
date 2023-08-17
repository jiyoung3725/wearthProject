package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "addr")
@ToString
public class AddrVO {

	@Id
	private int addrno;
	private int userno;
	private String addrname;
	private String receiver;
	private String phone;
	private String addr;
	private String post;
	private String is_default;
}
