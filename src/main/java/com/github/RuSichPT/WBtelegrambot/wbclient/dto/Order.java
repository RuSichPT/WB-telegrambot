package com.github.RuSichPT.WBtelegrambot.wbclient.dto;

import lombok.Data;

import java.util.Currency;
import java.util.Set;

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
