package com.github.RuSichPT.WBtelegrambot.wbclient;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfo;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class WbClientPricesImpl implements WbClientPrices {

    private final String token;

    private final String priceInfoPath;

    public WbClientPricesImpl(@Value("${wb.api.path}") String priceInfopath, @Value("${wb.standart.token}") String token) {
        this.priceInfoPath = priceInfopath + "/public/api/v1/info";
        this.token = token;
    }

    @Override
    public List<PriceInfo> getPriceInfo(Integer quantity) {

        HashMap<String,String> headersMap = new HashMap<>();
        headersMap.put("accept","application/json");
        headersMap.put("Authorization", token);

        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("quantity",quantity);

        return Unirest.get(priceInfoPath)
                .headers(headersMap)
                .queryString(queryMap)
                .asObject(new GenericType<List<PriceInfo>>() {})
                .getBody();

    }
}
