package com.github.RuSichPT.WBtelegrambot.wbclient;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.*;
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

    private final String wbToken;

    public WbClientPricesTest(@Value("${wb.api.path}") String WB_API_PATH, @Value("${wb.standart.test.token}") String wbToken) {
        this.wbClientPrices = new WbClientPricesImpl(WB_API_PATH);
        this.wbToken = wbToken;
    }

    @Test
    public void shouldCorrectlyGetPrices() {
        //given
        Integer quantity = 0;

        //when
        HttpResponse<List<PriceInfoGet>> httpResponse = wbClientPrices.getPriceInfo(quantity, wbToken);
        List<PriceInfoGet> priceInfoList = httpResponse.getBody();

        //then
        Assertions.assertEquals(HttpStatus.OK, httpResponse.getStatus());
        Assertions.assertNotNull(priceInfoList);
    }

    @Test
    public void shouldCorrectlyGetNewOrders() {
        //given

        //when
        HttpResponse<Orders> httpResponse = wbClientPrices.getNewOrders(wbToken);
        Orders orders = httpResponse.getBody();

        //then
        Assertions.assertEquals(HttpStatus.OK, httpResponse.getStatus());
        Assertions.assertNotNull(orders);
    }

    @Test
    public void shouldCorrectlyGetOrders() {
        //given
        OrderRequestArgs requestArgs = OrderRequestArgs.builder()
                .limit(10)
                .next(0L)
                .build();

        //when
        HttpResponse<Orders> httpResponse = wbClientPrices.getOrders(requestArgs, wbToken);
        Orders orders = httpResponse.getBody();

        //then
        Assertions.assertEquals(HttpStatus.OK, httpResponse.getStatus());
        Assertions.assertNotNull(orders);
    }

    @Test
    public void shouldCorrectlyPutPrices() {
        //given
        PriceInfoSet price = new PriceInfoSet(1234567, 1000);

        //when
        HttpResponse<JsonNode> httpResponse = wbClientPrices.setPriceInfo(price, wbToken);

        //then
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
        Assertions.assertEquals(ERROR_MESSAGE, httpResponse.getBody().getObject().get("errors").toString());
    }

    @Test
    public void shouldCorrectlyPutDiscounts() {
        //given
        Discount discount = new Discount(1234567, 15);

        //when
        HttpResponse<JsonNode> httpResponse = wbClientPrices.setDiscount(discount, wbToken);

        //then
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
        Assertions.assertEquals(ERROR_MESSAGE, httpResponse.getBody().getObject().get("errors").toString());
    }
}