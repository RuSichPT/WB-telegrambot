package com.github.RuSichPT.WBtelegrambot.command;

import kong.unirest.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetPriceCommandTest extends AbstractPriceCommandTest {

    private final GetPriceCommand getPriceCommand = new GetPriceCommand(sendBotMessageService, wbClientPrices);

    @BeforeEach
    public void init() {
        Mockito.when(httpResponse.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(httpResponse.getBody()).thenReturn(priceInfoGetList);
        Mockito.when(wbClientPrices.getPriceInfo(0)).thenReturn(httpResponse);
    }

    @Test
    public void shouldCorrectlyExecuteCommand1() {
        //given
        String command = "/getpriceinfo";
        String answer = GetPriceCommand.GET_PRICE_COMMAND1;

        executeAndVerify(command, answer);
    }

    @Test
    public void shouldCorrectlyExecuteCommand2() {
        //given
        String command = "/getpriceinfo 0";
        String answer = String.format(GetPriceCommand.GET_PRICE_COMMAND2,
                priceInfoGetList.get(0).getNmId(),
                priceInfoGetList.get(0).getPrice(),
                priceInfoGetList.get(0).getDiscount(),
                priceInfoGetList.get(0).getPromoCode());

        executeAndVerify(command, answer);
    }

    @Test
    public void shouldCorrectlyExecuteCommand3() {
        //given
        String command = "/getpriceinfo p";
        String answer = "Неправильно указан filter. Возможные значения: \n"
                + "2 - товар с нулевым остатком.\n"
                + "1 - товар с ненулевым остатком.\n"
                + "0 - вернётся весь товар.\n\n";

        executeAndVerify(command, answer);
    }

    private void executeAndVerify(String command, String answer) {
        //when
        getPriceCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, answer);
    }
}
