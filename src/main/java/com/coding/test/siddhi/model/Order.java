package com.coding.test.siddhi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Order {
    private Integer amount;
    private String currency;
    private OrderType type;
    private String targetCurrency;
    private OrderStatus status;
}
