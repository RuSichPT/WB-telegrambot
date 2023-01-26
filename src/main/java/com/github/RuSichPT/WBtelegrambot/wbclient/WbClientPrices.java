package com.github.RuSichPT.WBtelegrambot.wbclient;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Discount;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoGet;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoSet;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;

import java.util.List;

public interface WbClientPrices {
    List<PriceInfoGet> getPriceInfo(Integer quantity);

    HttpResponse<JsonNode> setPriceInfo(PriceInfoSet price);

    HttpResponse<JsonNode> setDiscount(Discount discount);
}
