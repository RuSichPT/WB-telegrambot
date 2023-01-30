package com.github.RuSichPT.WBtelegrambot.wbclient;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.*;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;

import java.util.List;

public interface WbClientPrices {
    HttpResponse<List<PriceInfoGet>> getPriceInfo(Integer quantity);

    HttpResponse<Orders> getNewOrders();

    HttpResponse<Orders> getOrders(OrderRequestArgs requestArgs);

    HttpResponse<JsonNode> setPriceInfo(PriceInfoSet price);

    HttpResponse<JsonNode> setDiscount(Discount discount);
}
