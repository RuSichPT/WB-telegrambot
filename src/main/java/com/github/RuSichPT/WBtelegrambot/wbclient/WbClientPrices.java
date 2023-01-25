package com.github.RuSichPT.WBtelegrambot.wbclient;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoSet;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoGet;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;

import java.util.List;

public interface WbClientPrices {
    List<PriceInfoGet> getPriceInfo(Integer quantity);

    HttpResponse<JsonNode> setPriceInfo(PriceInfoSet price);
}
