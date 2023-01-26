package com.github.RuSichPT.WBtelegrambot.wbclient;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Discount;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoGet;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoSet;
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
    private final String ERROR_MESSAGE = "[\"данных номенклатур не было в выгруженном с портала шаблоне: [1234567],"
            + " добавление строк в шаблон запрещено\"]";

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
        PriceInfoSet price = new PriceInfoSet(1234567, 1000);

        //when
        HttpResponse<JsonNode> httpResponse = wbClientPrices.setPriceInfo(price);

        //then
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
        Assertions.assertEquals(ERROR_MESSAGE, httpResponse.getBody().getObject().get("errors").toString());
    }

    @Test
    public void shouldCorrectlyPutDiscounts() {
        //given
        Discount discount = new Discount(1234567, 15);

        //when
        HttpResponse<JsonNode> httpResponse = wbClientPrices.setDiscount(discount);

        //then
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
        Assertions.assertEquals(ERROR_MESSAGE, httpResponse.getBody().getObject().get("errors").toString());
    }
}