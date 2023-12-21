package com.github.RuSichPT.WBtelegrambot.wbclient.dto;

import lombok.Data;

import java.util.Currency;
import java.util.Set;

@Data
public class Order {
    private Integer id;
    private String rid;
    private String createdAt;
    private Integer warehouseId;
    private String supplyId;
    private String[] prioritySc;
    private String[] offices;
    private Address address;
    private User user;
    private String[] skus;
    private Integer price;
    private Integer convertedPrice;
    private Integer currencyCode;
    private Integer convertedCurrencyCode;
    private String orderUid;
    private String deliveryType;
    private Integer nmId;
    private Integer chrtId;
    private String article;
    private Boolean isLargeCargo;

    private Currency getCurrency() {
        Set<Currency> currencies = Currency.getAvailableCurrencies();

        for (Currency c :
                currencies) {
            if (c.getNumericCode() == currencyCode) {
                return c;
            }
        }

        throw new IllegalArgumentException();
    }

    public String toStringShort() {
        String s = "Номенклатура товара: %s\n"
                + "Артикул: %s\n"
                + "Цена: %s " + getCurrency() + "\n";

        return String.format(s,nmId, article, price / 100L);
    }
}
