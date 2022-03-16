package com.coding.test.siddhi.service;

import org.springframework.stereotype.Service;

@Service
public class CurrencyConverter {

    public Integer calcValue(Integer amt, String currentCurrency){
        if(currentCurrency.equals("GBP")){
            return amt * 2;
        } else{
            return amt/2;
        }
    }
}
