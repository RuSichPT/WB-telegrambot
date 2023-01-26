package com.github.RuSichPT.WBtelegrambot.wbclient;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Discount;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoGet;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoSet;
import kong.unirest.GenericType;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class WbClientPricesImpl implements WbClientPrices {

    private final String apiPath;

    private final HashMap<String, String> headersMap;

    public WbClientPricesImpl(@Value("${wb.api.path}") String apiPath, @Value("${wb.standart.token}") String token) {
        this.apiPath = apiPath + "/public/api/v1/";
        this.headersMap = new HashMap<>();
        this.headersMap.put("accept", "application/json");
        this.headersMap.put("Authorization", token);
    }

    @Override
    public List<PriceInfoGet> getPriceInfo(Integer quantity) {
        String path = apiPath + "/info";

        headersMap.put("accept", "application/json");

        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("quantity", quantity);

        return Unirest.get(path)
                .headers(headersMap)
                .queryString(queryMap)
                .asObject(new GenericType<List<PriceInfoGet>>() {
                })
                .getBody();

    }

    @Override
    public HttpResponse<JsonNode> setPriceInfo(PriceInfoSet priceInfoSet) {
        String path = apiPath + "/prices";

        headersMap.put("accept", "/");
        headersMap.put("Content-Type", "application/json");

        String str = "[" + priceInfoSet.getJsonNode() + "]";
        return Unirest.post(path)
                .headers(headersMap)
                .body(str)
                .asJson();
    }

    @Override
    public HttpResponse<JsonNode> setDiscount(Discount discount) {
        String path = apiPath + "/updateDiscounts";

        headersMap.put("accept", "text/plain");
        headersMap.put("Content-Type", "application/json");

        String str = "[" + discount.getJsonNode() + "]";
        return Unirest.post(path)
                .headers(headersMap)
                .body(str)
                .asJson();
    }
}
