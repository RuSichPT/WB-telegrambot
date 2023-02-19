package com.github.RuSichPT.WBtelegrambot.command;

import kong.unirest.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.github.RuSichPT.WBtelegrambot.command.CommandName.GET_PRICE;
import static com.github.RuSichPT.WBtelegrambot.command.GetPriceCommand.MESSAGE1;
import static com.github.RuSichPT.WBtelegrambot.command.GetPriceCommand.MESSAGE2;
import static org.mockito.ArgumentMatchers.eq;

public class GetPriceInfoCommandTest extends AbstractWbClientCommandTest {

    private final GetPriceCommand getPriceCommand = new GetPriceCommand(sendBotMessageService, telegramUserService, wbClientPrices);

    @BeforeEach
    public void init() {
        Mockito.when(httpResponseList.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(httpResponseList.getBody()).thenReturn(priceInfoGetList);
        Mockito.when(wbClientPrices.getPriceInfo(eq(0), Mockito.any(String.class))).thenReturn(httpResponseList);

        initTelegramUserService();
    }

    @Test
    public void shouldCorrectlySendMessage1() {
        //given
        String command = GET_PRICE.getCommandName();

        //when
        getPriceCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(eq(chatId), eq(MESSAGE1), Mockito.any());
    }

    @Test
    public void shouldCorrectlySendMessage2() {
        //given
        String command = GET_PRICE.getCommandName();
        String answer = String.format(MESSAGE2,
                priceInfoGetList.get(0).getNmId(),
                priceInfoGetList.get(0).getNmId(),
                priceInfoGetList.get(0).getPrice(),
                priceInfoGetList.get(0).getDiscount(),
                priceInfoGetList.get(0).getPromoCode());

        //when
        getPriceCommand.executeCallback(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, answer);
    }
}
