package com.github.RuSichPT.WBtelegrambot.wbclient.dto;

import kong.unirest.JsonNode;
import lombok.Data;

@Data
public class Discount {
    private Integer discount;
    private Integer nm;

    public Discount(Integer nm, Integer discount) {
        this.discount = discount;
        this.nm = nm;
    }

    public JsonNode getJsonNode() {
        String str = String.format("{\"discount\":%s,\"nm\": %s}", this.getDiscount(), this.getNm());
        return new JsonNode(str);
    }
}
