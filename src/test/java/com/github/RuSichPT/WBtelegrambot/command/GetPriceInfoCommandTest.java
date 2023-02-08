package com.github.RuSichPT.WBtelegrambot.command;

import kong.unirest.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.github.RuSichPT.WBtelegrambot.command.CommandName.GET_PRICE;
import static com.github.RuSichPT.WBtelegrambot.command.GetPriceCommand.*;

public class GetPriceInfoCommandTest extends AbstractWbClientCommandTest {

    private final GetPriceCommand getPriceCommand = new GetPriceCommand(sendBotMessageService, telegramUserService, wbClientPrices);

    @BeforeEach
    public void init() {
        Mockito.when(httpResponseList.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(httpResponseList.getBody()).thenReturn(priceInfoGetList);
        Mockito.when(wbClientPrices.getPriceInfo(Mockito.eq(0), Mockito.any(String.class))).thenReturn(httpResponseList);

        initTelegramUserService();
    }

    @Test
    public void shouldCorrectlySendMessage1() {
        //given
        String command = GET_PRICE.getCommandName();

        executeAndVerify(getPriceCommand, command, MESSAGE1);
    }

    @Test
    public void shouldCorrectlySendMessage2() {
        //given
        String command = GET_PRICE.getCommandName() + " 0";
        String answer = String.format(MESSAGE2,
                priceInfoGetList.get(0).getNmId(),
                priceInfoGetList.get(0).getNmId(),
                priceInfoGetList.get(0).getPrice(),
                priceInfoGetList.get(0).getDiscount(),
                priceInfoGetList.get(0).getPromoCode());

        executeAndVerify(getPriceCommand, command, answer);
    }

    @Test
    public void shouldCorrectlySendMessage3() {
        //given
        String command = GET_PRICE.getCommandName() + " p";

        executeAndVerify(getPriceCommand, command, MESSAGE3);
    }
}
