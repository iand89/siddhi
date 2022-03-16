package com.coding.test.siddhi;

import com.coding.test.siddhi.controller.FXController;
import com.coding.test.siddhi.model.Order;
import com.coding.test.siddhi.model.TradeResponse;
import com.coding.test.siddhi.service.FXService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class FXControllerTest {

    @Mock
    private FXService fxService;

    @InjectMocks
    private FXController fxController;

    @Test
    public void createOrder(){
        Order order = new Order();
        ResponseEntity<Integer> result =  fxController.createOrder(order);
        assertTrue(result.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    public void getOrder(){
        ResponseEntity<TradeResponse> result =  fxController.getOrder(0);
        assertTrue(result.getStatusCode().equals(HttpStatus.OK));
    }
}
