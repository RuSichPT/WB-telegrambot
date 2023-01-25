package com.github.RuSichPT.WBtelegrambot.wbclient.dto;

import kong.unirest.JsonNode;
import lombok.Data;

@Data
public class PriceInfoSet {
    private Integer nmId;
    private Integer price;

    public PriceInfoSet(Integer nmId, Integer price) {
        this.nmId = nmId;
        this.price = price;
    }

    public JsonNode getJsonNode()
    {
        String str = String.format("{\"nmId\":%s,\"price\": %s}",this.getNmId(),this.getPrice());
        return new JsonNode(str);
    }
}
