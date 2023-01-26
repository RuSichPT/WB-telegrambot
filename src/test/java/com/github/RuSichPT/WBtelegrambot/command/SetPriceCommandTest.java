package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoGet;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoSet;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.JsonNode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

public class SetPriceCommandTest extends AbstractPriceCommandTest {

    private final SetPriceCommand setPriceCommand = new SetPriceCommand(sendBotMessageService, wbClientPrices);

    @Test
    public void shouldCorrectlyExecuteCommand1() {
        //given
        String command = "/setprice";
        PriceInfoGet priceInfoGet = new PriceInfoGet();
        priceInfoGet.setNmId(125468);
        String answer = SetPriceCommand.SET_PRICE_MESSAGE1
                + String.format("%s = %s\n", priceInfoGet.getNmId(), priceInfoGet.getDiscount());
        Mockito.when(wbClientPrices.getPriceInfo(0)).thenReturn(Collections.singletonList(priceInfoGet));

        //when
        setPriceCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, answer);
    }

    @Test
    public void shouldCorrectlyExecuteCommand2() {
        //given
        PriceInfoSet price = new PriceInfoSet(12345678, 10569);
        String command = String.format("/setprice %s = %s", price.getNmId(), price.getPrice());
        String answer = String.format(SetPriceCommand.SET_PRICE_MESSAGE2, price.getNmId(), price.getPrice());
        HttpResponse<JsonNode> httpResponse = Mockito.mock(HttpResponse.class);

        Mockito.when(httpResponse.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(wbClientPrices.setPriceInfo(price)).thenReturn(httpResponse);

        //when
        setPriceCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, answer);
    }

    @Test
    public void shouldCorrectlyExecuteCommand3() {
        //given
        PriceInfoSet price = new PriceInfoSet(12345678, 10569);
        String command = "/setprice dsfg";
        String answer = SetPriceCommand.SET_PRICE_MESSAGE3;
        HttpResponse<JsonNode> httpResponse = Mockito.mock(HttpResponse.class);

        Mockito.when(httpResponse.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(wbClientPrices.setPriceInfo(price)).thenReturn(httpResponse);

        //when
        setPriceCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, answer);
    }

    @Test
    public void shouldCorrectlyExecuteCommand4() {
        //given
        PriceInfoSet price = new PriceInfoSet(12345678, 10569);
        String command = String.format("/setprice %s %s", price.getNmId(), price.getPrice());
        String answer = SetPriceCommand.SET_PRICE_MESSAGE3;
        HttpResponse<JsonNode> httpResponse = Mockito.mock(HttpResponse.class);

        Mockito.when(httpResponse.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(wbClientPrices.setPriceInfo(price)).thenReturn(httpResponse);

        //when
        setPriceCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, answer);
    }

}
