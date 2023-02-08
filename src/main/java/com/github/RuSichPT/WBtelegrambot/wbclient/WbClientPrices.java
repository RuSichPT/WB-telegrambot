package com.github.RuSichPT.WBtelegrambot.wbclient;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.*;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;

import java.util.List;

public interface WbClientPrices {
    HttpResponse<List<PriceInfoGet>> getPriceInfo(Integer quantity, String wbToken);

    HttpResponse<Orders> getNewOrders(String wbToken);

    HttpResponse<Orders> getOrders(OrderRequestArgs requestArgs, String wbToken);

    HttpResponse<JsonNode> setPriceInfo(PriceInfoSet price, String wbToken);

    HttpResponse<JsonNode> setDiscount(Discount discount, String wbToken);
}
