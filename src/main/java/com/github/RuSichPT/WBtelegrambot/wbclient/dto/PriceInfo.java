package com.github.RuSichPT.WBtelegrambot.wbclient.dto;

import lombok.Data;

@Data
public class PriceInfo {
    private Integer nmId;
    private Double price;
    private Integer discount;
    private Double promoCode;
}
