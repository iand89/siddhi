package com.coding.test.siddhi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface AuthTradeRepo extends JpaRepository<AuthTrade, AuthTradeId> {
    @Query("SELECT at FROM AuthTrade at WHERE at.buyerOrderId = :orderId OR at.sellerOrderId = :orderId")
    List<AuthTrade> findByOrderId(@Param("orderId") Integer orderId);
}
