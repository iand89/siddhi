package com.coding.test.siddhi.model;

import com.coding.test.siddhi.repository.Trade;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TradeResponse {
    List<Trade> buyers;
    List<Trade> sellers;
}
