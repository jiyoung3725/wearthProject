package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.vo.OrdersDetailVO;

import jakarta.transaction.Transactional;

@Repository
public interface OrdersDetailJpaRepository extends JpaRepository<OrdersDetailVO, Integer> {
	
	public List<OrdersDetailVO> findByOrdersNo(int ordersno);
	@Transactional
	@Modifying
	@Query(value = "insert into ordersdetail(ordersdetailno, userno, ordersno, goodsno,  detailcnt) values "
			+ "(seq_ordersdetail.nextval,:#{#o.userNo},:#{#o.ordersNo}, :#{#o.goodsNo}, :#{#o.detailCnt} )", nativeQuery = true)
	public void insertOrdersDetialGoods(OrdersDetailVO o);
	
}
