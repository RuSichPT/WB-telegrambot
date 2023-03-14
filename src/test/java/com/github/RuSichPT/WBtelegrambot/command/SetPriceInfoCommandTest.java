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
import java.util.List;

import static com.github.RuSichPT.WBtelegrambot.command.CommandName.SET_PRICE;
import static com.github.RuSichPT.WBtelegrambot.command.SetPriceCommand.*;

public class SetPriceInfoCommandTest extends AbstractWbClientCommandTest {

    private final SetPriceCommand setPriceCommand = new SetPriceCommand(sendBotService, telegramUserService, wbClientPrices);
    private PriceInfoGet priceInfoGet;
    private PriceInfoSet priceInfoSet;

    @BeforeEach
    public void init() {
        priceInfoGet = new PriceInfoGet();
        priceInfoGet.setNmId(125468);
        HttpResponse<List<PriceInfoGet>> listHttpResponse = Mockito.mock(HttpResponse.class);
        Mockito.when(listHttpResponse.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(listHttpResponse.getBody()).thenReturn(Collections.singletonList(priceInfoGet));
        Mockito.when(wbClientPrices.getPriceInfo(Mockito.eq(0), Mockito.any(String.class))).thenReturn(listHttpResponse);

        priceInfoSet = new PriceInfoSet(12345678, 10569);
        HttpResponse<JsonNode> httpResponse = Mockito.mock(HttpResponse.class);
        Mockito.when(httpResponse.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(wbClientPrices.setPriceInfo(Mockito.eq(priceInfoSet), Mockito.any(String.class))).thenReturn(httpResponse);

        initTelegramUserService();
    }

    @Test
    public void shouldCorrectlySendMessage1() {
        //given
        String command = SET_PRICE.getCommandName();
        String answer = MESSAGE1
                + String.format("%s = %s\n", priceInfoGet.getNmId(), priceInfoGet.getDiscount());

        executeAndVerify(setPriceCommand, command, answer);
    }

    @Test
    public void shouldCorrectlySendMessage2() {
        //given
        String command = String.format(SET_PRICE.getCommandName() + " %s = %s", priceInfoSet.getNmId(), priceInfoSet.getPrice());
        String answer = String.format(MESSAGE2, priceInfoSet.getNmId(), priceInfoSet.getPrice());

        executeAndVerify(setPriceCommand, command, answer);
    }

    @Test
    public void shouldCorrectlySendMessage3_1() {
        //given
        String command = SET_PRICE.getCommandName() + " dsfg";

        executeAndVerify(setPriceCommand, command, MESSAGE3);
    }

    @Test
    public void shouldCorrectlySendMessage4_2() {
        //given
        String command = String.format(SET_PRICE.getCommandName() + " %s %s", priceInfoSet.getNmId(), priceInfoSet.getPrice());

        executeAndVerify(setPriceCommand, command, MESSAGE3);
    }
}
