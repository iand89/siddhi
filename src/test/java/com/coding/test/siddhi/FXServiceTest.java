package com.coding.test.siddhi;

import com.coding.test.siddhi.model.Order;
import com.coding.test.siddhi.model.TradeResponse;
import com.coding.test.siddhi.service.FXService;
import com.coding.test.siddhi.service.OrderProcessorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class FXServiceTest {
    @Mock
    OrderProcessorService orderProcessorService;

    @InjectMocks
    FXService fxService;


    @Test
    public void testCreateOrder(){
        Order order = Mockito.mock(Order.class);
        Mockito.when(orderProcessorService.placeOrder(order)).thenReturn(0);
        Integer response = fxService.createOrder(order);
        assertTrue(response == 0);
    }

    @Test
    public void testGetOrder(){
        Integer id =0;
        TradeResponse tradeResponse = new TradeResponse();
        Mockito.when(orderProcessorService.getOrder(id)).thenReturn(tradeResponse);
        assertTrue(fxService.getOrder(id).equals(tradeResponse));
    }
}
