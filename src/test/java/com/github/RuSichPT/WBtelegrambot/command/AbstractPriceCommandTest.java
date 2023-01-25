package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageServiceImpl;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPricesImpl;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractPriceCommandTest {

    protected final SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageServiceImpl.class);
    protected final WbClientPrices wbClientPrices = Mockito.mock(WbClientPricesImpl.class);

    protected final Long chatId = 1234567824356L;

    protected Update getUpdate(String command)
    {
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(command);
        update.setMessage(message);

        return update;
    }
}
