package com.coding.test.siddhi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {

    @Query("SELECT t FROM Trade t WHERE t.status IN :statuses and t.orderType = :orderType and t.currency = :currency")
    List<Trade> findByOrderStatusAndType(@Param("statuses") Collection<String> statuses, @Param("orderType") String orderType,@Param("currency") String currency);



}
