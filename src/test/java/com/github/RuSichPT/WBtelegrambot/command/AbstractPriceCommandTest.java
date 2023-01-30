package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageServiceImpl;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPricesImpl;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoGet;
import kong.unirest.HttpResponse;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static java.util.Collections.singletonList;

public abstract class AbstractPriceCommandTest {

    protected final SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageServiceImpl.class);
    protected final WbClientPrices wbClientPrices = Mockito.mock(WbClientPricesImpl.class);
    protected final HttpResponse<List<PriceInfoGet>> httpResponse = Mockito.mock(HttpResponse.class);

    protected final List<PriceInfoGet> priceInfoGetList = createPriceInfoGetList();
    protected final Long chatId = 1234567824356L;

    protected Update getUpdate(String command) {
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(command);
        update.setMessage(message);

        return update;
    }

    private List<PriceInfoGet> createPriceInfoGetList() {
        PriceInfoGet priceInfoGet = new PriceInfoGet();
        priceInfoGet.setNmId(123456);
        priceInfoGet.setPrice(10256d);
        priceInfoGet.setDiscount(53);
        priceInfoGet.setPromoCode(123d);

        return singletonList(priceInfoGet);
    }
}
