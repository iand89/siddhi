package com.coding.test.siddhi.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(AuthTradeId.class)
public class AuthTrade {
    @Id
    private Integer buyerOrderId;

    @Id
    private Integer sellerOrderId;

    private String status;
}
