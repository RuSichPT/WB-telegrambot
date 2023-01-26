package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoGet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static java.util.Collections.singletonList;

public class GetPriceCommandTest extends AbstractPriceCommandTest {

    private final GetPriceCommand getPriceCommand = new GetPriceCommand(sendBotMessageService, wbClientPrices);

    @Test
    public void shouldCorrectlyExecuteCommand1() {
        //given
        String command = "/getpriceinfo";
        String answer = GetPriceCommand.GET_PRICE_COMMAND1;

        //when
        getPriceCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, answer);
    }

    @Test
    public void shouldCorrectlyExecuteCommand2() {
        //given
        String command = "/getpriceinfo 0";
        List<PriceInfoGet> priceInfoGetList = getPriceInfoGetList();
        String answer = String.format(GetPriceCommand.GET_PRICE_COMMAND2,
                priceInfoGetList.get(0).getNmId(),
                priceInfoGetList.get(0).getPrice(),
                priceInfoGetList.get(0).getDiscount(),
                priceInfoGetList.get(0).getPromoCode());
        Mockito.when(wbClientPrices.getPriceInfo(0)).thenReturn(priceInfoGetList);

        //when
        getPriceCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, answer);
    }

    @Test
    public void shouldCorrectlyExecuteCommand3() {
        //given
        String command = "/getpriceinfo p";
        String answer = "Неправильно указан filter. Возможные значения: \n"
                + "2 - товар с нулевым остатком.\n"
                + "1 - товар с ненулевым остатком.\n"
                + "0 - вернётся весь товар.\n\n";

        //when
        getPriceCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, answer);
    }

    private List<PriceInfoGet> getPriceInfoGetList() {
        PriceInfoGet priceInfoGet = new PriceInfoGet();
        priceInfoGet.setNmId(123456);
        priceInfoGet.setPrice(10256d);
        priceInfoGet.setDiscount(53);
        priceInfoGet.setPromoCode(123d);

        return singletonList(priceInfoGet);
    }
}
