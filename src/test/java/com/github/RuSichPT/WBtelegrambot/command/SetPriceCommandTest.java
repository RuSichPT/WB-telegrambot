package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoGet;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoSet;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

public class SetPriceCommandTest extends AbstractPriceCommandTest {

    private final SetPriceCommand setPriceCommand = new SetPriceCommand(sendBotMessageService, wbClientPrices);
    private PriceInfoGet priceInfoGet;
    private PriceInfoSet priceInfoSet;

    @BeforeEach
    public void init() {
        priceInfoGet = new PriceInfoGet();
        priceInfoGet.setNmId(125468);
        Mockito.when(wbClientPrices.getPriceInfo(0)).thenReturn(Collections.singletonList(priceInfoGet));

        priceInfoSet = new PriceInfoSet(12345678, 10569);
        HttpResponse<JsonNode> httpResponse = Mockito.mock(HttpResponse.class);
        Mockito.when(httpResponse.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(wbClientPrices.setPriceInfo(priceInfoSet)).thenReturn(httpResponse);
    }

    @Test
    public void shouldCorrectlyExecuteCommand1() {
        //given
        String command = "/setprice";
        String answer = SetPriceCommand.SET_PRICE_MESSAGE1
                + String.format("%s = %s\n", priceInfoGet.getNmId(), priceInfoGet.getDiscount());

        executeAndVerify(command, answer);
    }

    @Test
    public void shouldCorrectlyExecuteCommand2() {
        //given
        String command = String.format("/setprice %s = %s", priceInfoSet.getNmId(), priceInfoSet.getPrice());
        String answer = String.format(SetPriceCommand.SET_PRICE_MESSAGE2, priceInfoSet.getNmId(), priceInfoSet.getPrice());

        executeAndVerify(command, answer);
    }

    @Test
    public void shouldCorrectlyExecuteCommand3() {
        //given
        String command = "/setprice dsfg";

        executeAndVerify(command, SetPriceCommand.SET_PRICE_MESSAGE3);
    }

    @Test
    public void shouldCorrectlyExecuteCommand4() {
        //given
        String command = String.format("/setprice %s %s", priceInfoSet.getNmId(), priceInfoSet.getPrice());

        executeAndVerify(command, SetPriceCommand.SET_PRICE_MESSAGE3);
    }

    private void executeAndVerify(String command, String answer) {
        //when
        setPriceCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, answer);
    }

}
