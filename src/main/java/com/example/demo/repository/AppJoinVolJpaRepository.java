package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.vo.AppAndVolVO;

@Repository
public interface AppJoinVolJpaRepository extends JpaRepository<AppAndVolVO, Integer> {

	public List<AppAndVolVO> findByUserNo(int userno);
}
