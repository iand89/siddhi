package com.coding.test.siddhi.controller;

import com.coding.test.siddhi.model.Order;
import com.coding.test.siddhi.model.TradeResponse;
import com.coding.test.siddhi.repository.Trade;
import com.coding.test.siddhi.service.FXService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class FXController {

    private FXService fxService;

    @PutMapping("/createOrder")
    public ResponseEntity<Integer> createOrder(@RequestBody Order order){
        Integer resp = fxService.createOrder(order);
        ResponseEntity<Integer> result = new ResponseEntity(resp, HttpStatus.OK);
        return result;
    }

    @RequestMapping(value="/getOrder/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TradeResponse> getOrder(@PathVariable Integer id){
        TradeResponse resp = fxService.getOrder(id);
        ResponseEntity<TradeResponse> result = new ResponseEntity(resp, HttpStatus.OK);
        return result;
    }
}
