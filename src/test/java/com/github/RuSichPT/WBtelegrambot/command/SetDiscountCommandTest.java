package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Discount;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.github.RuSichPT.WBtelegrambot.command.CommandName.SET_DISCOUNT;
import static com.github.RuSichPT.WBtelegrambot.command.SetDiscountCommand.*;

public class SetDiscountCommandTest extends AbstractWbClientCommandTest {
    private final SetDiscountCommand setDiscountCommand = new SetDiscountCommand(sendBotMessageService, telegramUserService, wbClientPrices);

    private Discount discount;

    @BeforeEach
    public void init() {
        Mockito.when(httpResponseList.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(httpResponseList.getBody()).thenReturn(priceInfoGetList);
        Mockito.when(wbClientPrices.getPriceInfo(Mockito.eq(0), Mockito.any(String.class))).thenReturn(httpResponseList);

        discount = new Discount(12345678, 15);
        HttpResponse<JsonNode> httpResponse = Mockito.mock(HttpResponse.class);
        Mockito.when(httpResponse.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(wbClientPrices.setDiscount(Mockito.eq(discount), Mockito.any(String.class))).thenReturn(httpResponse);

        initTelegramUserService();
    }

    @Test
    public void shouldCorrectlySendMessage1() {
        //given
        String command = SET_DISCOUNT.getCommandName();
        String answer = MESSAGE1
                + String.format("%s = %s%%\n", priceInfoGetList.get(0).getNmId(), priceInfoGetList.get(0).getDiscount());

        executeAndVerify(setDiscountCommand, command, answer);
    }

    @Test
    public void shouldCorrectlySendMessage2() {
        //given
        String command = String.format(SET_DISCOUNT.getCommandName() + " %s = %s", discount.getNm(), discount.getDiscount());
        String answer = String.format(MESSAGE2, discount.getNm(), discount.getDiscount());

        executeAndVerify(setDiscountCommand, command, answer);
    }

    @Test
    public void shouldCorrectlySendMessage3_1() {
        //given
        String command = SET_DISCOUNT.getCommandName() + " dsfg";

        executeAndVerify(setDiscountCommand, command, MESSAGE3);
    }

    @Test
    public void shouldCorrectlySendMessage3_2() {
        //given
        String command = String.format(SET_DISCOUNT.getCommandName() + " %s %s", discount.getNm(), discount.getDiscount());

        executeAndVerify(setDiscountCommand, command, MESSAGE3);
    }
}
