package com.github.RuSichPT.WBtelegrambot.wbclient;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoSet;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoGet;
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

    private final String token;

    private final String priceInfoPath;
    private final String pricesPath;

    public WbClientPricesImpl(@Value("${wb.api.path}") String priceInfoPath, @Value("${wb.standart.token}") String token) {
        this.priceInfoPath = priceInfoPath + "/public/api/v1/info";
        this.pricesPath = priceInfoPath + "/public/api/v1/prices";
        this.token = token;
    }

    @Override
    public List<PriceInfoGet> getPriceInfo(Integer quantity) {

        HashMap<String,String> headersMap = new HashMap<>();
        headersMap.put("accept","application/json");
        headersMap.put("Authorization", token);

        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("quantity",quantity);

        return Unirest.get(priceInfoPath)
                .headers(headersMap)
                .queryString(queryMap)
                .asObject(new GenericType<List<PriceInfoGet>>() {})
                .getBody();

    }

    @Override
    public HttpResponse<JsonNode> setPriceInfo(PriceInfoSet priceInfoSet) {

        HashMap<String,String> headersMap = new HashMap<>();
        headersMap.put("accept","/");
        headersMap.put("Authorization", token);
        headersMap.put("Content-Type", "application/json");

        String str = "[" + priceInfoSet.getJsonNode() + "]";
        return Unirest.post(pricesPath)
                .headers(headersMap)
                .body(str)
                .asJson();
    }
}
