package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.vo.FilesVO;

import jakarta.transaction.Transactional;

public interface FilesJpaRepository extends JpaRepository<FilesVO, Integer> {
	
	//다음 파일 번호 부여
	@Query("select nvl(max(fileno), 0)+1 from FilesVO")
	public int getNextNo();

	//실천하기 게시글 내 파일 첨부
	@Modifying
	@Query(value = "insert into Files f(f.fileno, f.boardno, f.fname) values(:#{#f.fileno},:#{#f.boardno},:#{#f.fname})", nativeQuery = true)
	@Transactional
	public void insertInBoard(FilesVO f);
	
	//실천하기 게시글 내 파일 삭제
	@Modifying
	@Query(value = "delete from Files f where f.boardno=?1", nativeQuery = true)
	@Transactional
	public void deleteInBoard(int boardno);
	
	
	//봉사하기 게시글 내 파일 첨부
	@Modifying
	@Query(value = "insert into FilesVO f(f.fileno, f.volunteerno, f.fname) values(:#{#f.fileno},:#{#f.volunteerno},:#{#f.fname})", nativeQuery = true)
	@Transactional
	public void insertInVolunteer(FilesVO f);

	
}
