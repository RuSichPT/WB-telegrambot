package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageServiceImpl;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPricesImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CommandContainerTest {

    protected final SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageServiceImpl.class);
    protected final WbClientPrices wbClientPrices = Mockito.mock(WbClientPricesImpl.class);
    private final CommandContainer commandContainer = new CommandContainer(sendBotMessageService, wbClientPrices);

    @Test
    public void shouldBeTheSameNumberCommand() {
        Assertions.assertEquals(commandContainer.size(), CommandName.values().length);
    }
}
