package com.github.RuSichPT.WBtelegrambot.wbclient;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.*;
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

    private final String apiPathPublic;
    private final String apiPathOrders;

    private final HashMap<String, String> headersMap;

    public WbClientPricesImpl(@Value("${wb.api.path}") String apiPath) {
        apiPathPublic = apiPath + "/public/api/v1";
        apiPathOrders = apiPath + "/api/v3/orders";
        headersMap = new HashMap<>();
    }

    @Override
    public HttpResponse<List<PriceInfoGet>> getPriceInfo(Integer quantity, String wbToken) {
        String path = apiPathPublic + "/info";
        headersMap.put("Authorization", wbToken);
        headersMap.put("accept", "application/json");

        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("quantity", quantity);

        return Unirest.get(path)
                .headers(headersMap)
                .queryString(queryMap)
                .asObject(new GenericType<>() {
                });

    }

    @Override
    public HttpResponse<Orders> getNewOrders(String wbToken) {
        String path = apiPathOrders + "/new";
        headersMap.put("Authorization", wbToken);
        headersMap.put("accept", "application/json");

        return Unirest.get(path)
                .headers(headersMap)
                .asObject(new GenericType<>() {
                });
    }

    @Override
    public HttpResponse<Orders> getOrders(OrderRequestArgs requestArgs, String wbToken) {
        headersMap.put("Authorization", wbToken);
        headersMap.put("accept", "application/json");

        return Unirest.get(apiPathOrders)
                .headers(headersMap)
                .queryString(requestArgs.populateQueries())
                .asObject(new GenericType<>() {
                });
    }

    @Override
    public HttpResponse<JsonNode> setPriceInfo(PriceInfoSet priceInfoSet, String wbToken) {
        String path = apiPathPublic + "/prices";
        headersMap.put("Authorization", wbToken);
        headersMap.put("accept", "/");
        headersMap.put("Content-Type", "application/json");

        String str = "[" + priceInfoSet.getJsonNode() + "]";
        return Unirest.post(path)
                .headers(headersMap)
                .body(str)
                .asJson();
    }

    @Override
    public HttpResponse<JsonNode> setDiscount(Discount discount, String wbToken) {
        String path = apiPathPublic + "/updateDiscounts";
        headersMap.put("Authorization", wbToken);
        headersMap.put("accept", "text/plain");
        headersMap.put("Content-Type", "application/json");

        String str = "[" + discount.getJsonNode() + "]";
        return Unirest.post(path)
                .headers(headersMap)
                .body(str)
                .asJson();
    }
}
