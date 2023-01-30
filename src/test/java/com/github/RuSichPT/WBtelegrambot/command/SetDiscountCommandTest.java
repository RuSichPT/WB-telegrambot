package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Discount;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SetDiscountCommandTest extends AbstractPriceCommandTest {
    private final SetDiscountCommand setDiscountCommand = new SetDiscountCommand(sendBotMessageService, wbClientPrices);

    private Discount discount;

    @BeforeEach
    public void init() {
        Mockito.when(httpResponse.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(httpResponse.getBody()).thenReturn(priceInfoGetList);
        Mockito.when(wbClientPrices.getPriceInfo(0)).thenReturn(httpResponse);

        discount = new Discount(12345678, 15);
        HttpResponse<JsonNode> httpResponse = Mockito.mock(HttpResponse.class);
        Mockito.when(httpResponse.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(wbClientPrices.setDiscount(discount)).thenReturn(httpResponse);
    }

    @Test
    public void shouldCorrectlyExecuteCommand1() {
        //given
        String command = "/setdiscount";
        String answer = SetDiscountCommand.SET_DISCOUNT_MESSAGE1
                + String.format("%s = %s%%\n", priceInfoGetList.get(0).getNmId(), priceInfoGetList.get(0).getDiscount());

        executeAndVerify(command, answer);
    }

    @Test
    public void shouldCorrectlyExecuteCommand2() {
        //given
        String command = String.format("/setdiscount %s = %s", discount.getNm(), discount.getDiscount());
        String answer = String.format(SetDiscountCommand.SET_DISCOUNT_MESSAGE2, discount.getNm(), discount.getDiscount());

        executeAndVerify(command, answer);
    }

    @Test
    public void shouldCorrectlyExecuteCommand3() {
        //given
        String command = "/setdiscount dsfg";

        executeAndVerify(command, SetDiscountCommand.SET_DISCOUNT_MESSAGE3);
    }

    @Test
    public void shouldCorrectlyExecuteCommand4() {
        //given
        String command = String.format("/setdiscount %s %s", discount.getNm(), discount.getDiscount());

        executeAndVerify(command, SetDiscountCommand.SET_DISCOUNT_MESSAGE3);
    }

    private void executeAndVerify(String command, String answer) {
        //when
        setDiscountCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, answer);
    }
}
