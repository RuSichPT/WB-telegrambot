package com.github.RuSichPT.WBtelegrambot.wbclient.dto;

import lombok.Data;

@Data
public class Order {
    Integer id;
    String rid;
    String createdAt;
    Integer warehouseId;
    String supplyId;
    String[] prioritySc;
    String[] offices;
    Address address;
    User user;
    String[] skus;
    Integer price;
    Integer convertedPrice;
    Integer currencyCode;
    Integer convertedCurrencyCode;
    String orderUid;
    String deliveryType;
    Integer nmId;
    Integer chrtId;
    String article;
    Boolean isLargeCargo;
}
