package com.github.RuSichPT.WBtelegrambot.wbclient;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoSet;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoGet;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class WbClientPricesTest {

    private final WbClientPrices wbClientPrices;

    public WbClientPricesTest(@Value("${wb.api.path}") String WB_API_PATH, @Value("${wb.standart.token}") String token) {
        this.wbClientPrices = new WbClientPricesImpl(WB_API_PATH, token);
    }

    @Test
    public void shouldCorrectlyGetPrices() {
        //given
        Integer quantity = 0;

        //when
        List<PriceInfoGet> priceInfoList = wbClientPrices.getPriceInfo(quantity);

        //then
        Assertions.assertNotNull(priceInfoList);
    }

    @Test
    public void shouldCorrectlyPutPrices() {
        //given
        String error = "[\"данных номенклатур не было в выгруженном с портала шаблоне: [1234567], добавление строк в шаблон запрещено\"]";
        PriceInfoSet price = new PriceInfoSet(1234567,1000);

        //when
        HttpResponse<JsonNode> httpResponse = wbClientPrices.setPriceInfo(price);

        //then
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,httpResponse.getStatus());
        Assertions.assertEquals(error,httpResponse.getBody().getObject().get("errors").toString());
    }

}