package com.coding.test.siddhi.service;

import com.coding.test.siddhi.model.Order;
import com.coding.test.siddhi.model.TradeResponse;
import com.coding.test.siddhi.repository.Trade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FXService {
    private OrderProcessorService orderService;

    public Integer createOrder(Order order){
        Integer result = orderService.placeOrder(order);
        return result;
    }

    public TradeResponse getOrder(Integer id){
        return orderService.getOrder(id);
    }
}
