package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.AddrVO;

public interface AddrJpaRepository extends JpaRepository<AddrVO, Integer> {
		
	public List<AddrVO> findByUserno(int userno);



}
