package com.coding.test.siddhi.service;

import com.coding.test.siddhi.model.Order;
import com.coding.test.siddhi.model.OrderStatus;
import com.coding.test.siddhi.model.OrderType;
import com.coding.test.siddhi.model.TradeResponse;
import com.coding.test.siddhi.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderProcessorService {

    private TradeRepository tradeRepository;
    private AuthTradeRepo authTradeRepo;
    private CurrencyConverter currencyConverter;
    private static Integer orderNumber=0;

    public Integer placeOrder(Order order) {
        Trade trade = new Trade(++orderNumber, order.getCurrency(), order.getAmount(), OrderStatus.PENDING.toString(), order.getType().toString());
        tradeRepository.save(trade);

        if(trade.getOrderType().equals(OrderType.SELL.toString())){
            AuthTrade authTradeDefault = new AuthTrade(0, trade.getOrderId(), OrderStatus.PENDING.toString());
            authTradeRepo.save(authTradeDefault);
            List<String> statuses = new ArrayList<>();
            statuses.add(OrderStatus.PENDING.toString());
            statuses.add(OrderStatus.PARTIALLY_MATCHED.toString());
            List<Trade> buyers1 =   tradeRepository.findByOrderStatusAndType(statuses, OrderType.BUY.toString(), trade.getCurrency().equals("GBP")?"USD": "GBP");
            List<Trade> seller1 =   tradeRepository.findByOrderStatusAndType(statuses, OrderType.SELL.toString(), trade.getCurrency());
            Integer totalSellerAmt = 0;
            for (Trade t1 : seller1){
                totalSellerAmt = totalSellerAmt+currencyConverter.calcValue(t1.getAmount(), t1.getCurrency());
            }
            if(!buyers1.isEmpty() && buyers1.get(0).getAmount()>totalSellerAmt){
                List<AuthTrade> authTrades = new ArrayList<>();
                for (Trade t1 : buyers1){
                    t1.setStatus(OrderStatus.PARTIALLY_MATCHED.toString());
                    AuthTrade authTrade = new AuthTrade(trade.getOrderId(), t1.getOrderId(), OrderStatus.PARTIALLY_MATCHED.toString());
                    authTrades.add(authTrade);
                }
                tradeRepository.saveAll(buyers1);
                authTradeRepo.saveAll(authTrades);
            } else if(!buyers1.isEmpty() && buyers1.get(0).getAmount() == totalSellerAmt ){
                List<AuthTrade> authTrades = new ArrayList<>();
                for (Trade t1 : buyers1){
                    t1.setStatus(OrderStatus.FULLY_MATCHED.toString());
                    AuthTrade authTrade = new AuthTrade(trade.getOrderId(), t1.getOrderId(), OrderStatus.FULLY_MATCHED.toString());
                    authTrades.add(authTrade);
                }
                seller1.stream().forEach(f->f.setStatus(OrderStatus.FULLY_MATCHED.toString()));
                tradeRepository.saveAll(buyers1);
                tradeRepository.saveAll(seller1);
                authTradeRepo.saveAll(authTrades);
            }
        } else {
            AuthTrade authTradeDefault = new AuthTrade( trade.getOrderId(), 0, OrderStatus.PENDING.toString());
            authTradeRepo.save(authTradeDefault);
            List<String> statuses = new ArrayList<>();
            statuses.add(OrderStatus.PENDING.toString());
            statuses.add(OrderStatus.PARTIALLY_MATCHED.toString());
            List<Trade> seller1 =   tradeRepository.findByOrderStatusAndType(statuses, OrderType.SELL.toString(), trade.getCurrency().equals("GBP")?"USD": "GBP");
            List<Trade> buyers1 =   tradeRepository.findByOrderStatusAndType(statuses, OrderType.BUY.toString(), trade.getCurrency());
            Integer totalBuyerAmt = 0;
            for (Trade t1 : buyers1){
                totalBuyerAmt = totalBuyerAmt+currencyConverter.calcValue(t1.getAmount(), t1.getCurrency());
            }
            if(!seller1.isEmpty() && seller1.get(0).getAmount()>totalBuyerAmt){
                List<AuthTrade> authTrades = new ArrayList<>();
                for (Trade t1 : seller1){
                    t1.setStatus(OrderStatus.PARTIALLY_MATCHED.toString());
                    AuthTrade authTrade = new AuthTrade(trade.getOrderId(), t1.getOrderId(), OrderStatus.PARTIALLY_MATCHED.toString());
                    authTrades.add(authTrade);
                }
                tradeRepository.saveAll(seller1);
                authTradeRepo.saveAll(authTrades);
            } else if(!seller1.isEmpty() && seller1.get(0).getAmount() == totalBuyerAmt ){

                    List<AuthTrade> authTrades = new ArrayList<>();
                    for (Trade t1 : seller1){
                        t1.setStatus(OrderStatus.FULLY_MATCHED.toString());
                        AuthTrade authTrade = new AuthTrade(trade.getOrderId(), t1.getOrderId(), OrderStatus.FULLY_MATCHED.toString());
                        authTrades.add(authTrade);
                    }
                    buyers1.stream().forEach(f->f.setStatus(OrderStatus.FULLY_MATCHED.toString()));
                    tradeRepository.saveAll(seller1);
                    tradeRepository.saveAll(buyers1);
                    authTradeRepo.saveAll(authTrades);
                }

        }
        return orderNumber;
    }

    public TradeResponse getOrder(Integer id){
        Trade result;
        List<AuthTrade> resp = authTradeRepo.findByOrderId(id);

        List<Trade> buyers = tradeRepository.findAllById(resp.stream().map(f->f.getBuyerOrderId()).collect(Collectors.toList()));
        List<Trade> sellers = tradeRepository.findAllById(resp.stream().map(f->f.getSellerOrderId()).collect(Collectors.toList()));

        TradeResponse response = new TradeResponse(buyers, sellers);

        return response;
    }

}
