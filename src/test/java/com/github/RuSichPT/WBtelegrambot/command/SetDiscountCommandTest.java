package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Discount;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoGet;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.JsonNode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

public class SetDiscountCommandTest extends AbstractPriceCommandTest {
    private final SetDiscountCommand setDiscountCommand = new SetDiscountCommand(sendBotMessageService, wbClientPrices);

    @Test
    public void shouldCorrectlyExecuteCommand1() {
        //given
        String command = "/setdiscount";
        PriceInfoGet priceInfoGet = new PriceInfoGet();
        priceInfoGet.setNmId(125468);
        String answer = SetDiscountCommand.SET_DISCOUNT_MESSAGE1
                + String.format("%s = %s%%\n", priceInfoGet.getNmId(), priceInfoGet.getDiscount());
        Mockito.when(wbClientPrices.getPriceInfo(0)).thenReturn(Collections.singletonList(priceInfoGet));

        //when
        setDiscountCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, answer);
    }

    @Test
    public void shouldCorrectlyExecuteCommand2() {
        //given
        Discount discount = new Discount(12345678, 15);
        String command = String.format("/setdiscount %s = %s", discount.getNm(), discount.getDiscount());
        String answer = String.format(SetDiscountCommand.SET_DISCOUNT_MESSAGE2, discount.getNm(), discount.getDiscount());
        HttpResponse<JsonNode> httpResponse = Mockito.mock(HttpResponse.class);

        Mockito.when(httpResponse.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(wbClientPrices.setDiscount(discount)).thenReturn(httpResponse);

        //when
        setDiscountCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, answer);
    }

    @Test
    public void shouldCorrectlyExecuteCommand3() {
        //given
        Discount discount = new Discount(12345678, 15);
        String command = "/setdiscount dsfg";
        String answer = SetDiscountCommand.SET_DISCOUNT_MESSAGE3;
        HttpResponse<JsonNode> httpResponse = Mockito.mock(HttpResponse.class);

        Mockito.when(httpResponse.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(wbClientPrices.setDiscount(discount)).thenReturn(httpResponse);

        //when
        setDiscountCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, answer);
    }

    @Test
    public void shouldCorrectlyExecuteCommand4() {
        //given
        Discount discount = new Discount(12345678, 15);
        String command = String.format("/setdiscount %s %s", discount.getNm(), discount.getDiscount());
        String answer = SetDiscountCommand.SET_DISCOUNT_MESSAGE3;
        HttpResponse<JsonNode> httpResponse = Mockito.mock(HttpResponse.class);

        Mockito.when(httpResponse.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(wbClientPrices.setDiscount(discount)).thenReturn(httpResponse);

        //when
        setDiscountCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, answer);
    }
}
