package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.vo.LikedVO;

import jakarta.transaction.Transactional;

@Repository
public interface LikedJpaRepository extends JpaRepository<LikedVO, Integer> {
	
	@Query("select nvl(max(likedNo), 0)+1 from LikedVO")
	public int getNextNo();
	
	@Modifying
	@Query(value = "insert into Liked l(l.likedNo, l.userNo, l.boardNo, l.likedDate) "
			+ "values(:#{#l.likedNo},:#{#l.userNo},:#{#l.boardNo},sysdate)", nativeQuery = true)
	@Transactional
	public void insert(LikedVO l);

	@Modifying
	@Query(value = "delete from Liked l where l.boardno=?1 and l.userno=?2", nativeQuery = true)
	@Transactional
	public void delete(int boardno, int userno);
	
	
}
