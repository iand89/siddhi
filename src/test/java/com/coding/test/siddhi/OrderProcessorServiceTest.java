package com.coding.test.siddhi;

import com.coding.test.siddhi.model.Order;
import com.coding.test.siddhi.model.OrderStatus;
import com.coding.test.siddhi.model.OrderType;
import com.coding.test.siddhi.repository.AuthTradeRepo;
import com.coding.test.siddhi.repository.Trade;
import com.coding.test.siddhi.repository.TradeRepository;
import com.coding.test.siddhi.service.CurrencyConverter;
import com.coding.test.siddhi.service.OrderProcessorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class OrderProcessorServiceTest {

    @InjectMocks
    OrderProcessorService orderProcessorService;

    @Mock
    TradeRepository tradeRepository;

    @Mock
    AuthTradeRepo authTradeRepo;

    @Spy
    CurrencyConverter currencyConverter = new CurrencyConverter();

    @Test
    public void testPlaceOrder_Partial(){
        Order order = new Order(50, "GBP", OrderType.SELL, "USD", OrderStatus.PENDING);
        List<Trade> sellerList = new ArrayList<>();
        List<Trade> buyerList = new ArrayList<>();
        buyerList.add(new Trade(100, "USD", 20, OrderStatus.PENDING.toString(), OrderType.BUY.toString()));

        Mockito.when(tradeRepository.findByOrderStatusAndType(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(buyerList, sellerList);

        orderProcessorService.placeOrder(order);
    }

    @Test
    public void testPlaceOrder_Full(){
        Order order = new Order(50, "GBP", OrderType.SELL, "USD", OrderStatus.PENDING);
        List<Trade> sellerList = new ArrayList<>();
        List<Trade> buyerList = new ArrayList<>();
        buyerList.add(new Trade(100, "USD", 20, OrderStatus.PENDING.toString(), OrderType.BUY.toString()));
        buyerList.add(new Trade(99, "USD", 80, OrderStatus.PENDING.toString(), OrderType.BUY.toString()));
        sellerList.add(new Trade(101, "GBP", 50, OrderStatus.PENDING.toString(), OrderType.SELL.toString()));
        Mockito.when(tradeRepository.findByOrderStatusAndType(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(buyerList, sellerList);

        orderProcessorService.placeOrder(order);
    }

    @Test
    public void testPlaceOrder_BUY_Partial(){
        Order order = new Order(50, "GBP", OrderType.BUY, "USD", OrderStatus.PENDING);
        List<Trade> sellerList = new ArrayList<>();
        List<Trade> buyerList = new ArrayList<>();
        sellerList.add(new Trade(100, "USD", 20, OrderStatus.PENDING.toString(), OrderType.BUY.toString()));

        Mockito.when(tradeRepository.findByOrderStatusAndType(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(sellerList, buyerList);

        orderProcessorService.placeOrder(order);
    }

    @Test
    public void testPlaceOrder_BUY_Full(){
        Order order = new Order(50, "GBP", OrderType.BUY, "USD", OrderStatus.PENDING);
        List<Trade> sellerList = new ArrayList<>();
        List<Trade> buyerList = new ArrayList<>();
        sellerList.add(new Trade(100, "USD", 20, OrderStatus.PENDING.toString(), OrderType.SELL.toString()));
        sellerList.add(new Trade(99, "USD", 80, OrderStatus.PENDING.toString(), OrderType.SELL.toString()));
        buyerList.add(new Trade(101, "GBP", 50, OrderStatus.PENDING.toString(), OrderType.BUY.toString()));
        Mockito.when(tradeRepository.findByOrderStatusAndType(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(sellerList, buyerList);

        orderProcessorService.placeOrder(order);
    }
}
