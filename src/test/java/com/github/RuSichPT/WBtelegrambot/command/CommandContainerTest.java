package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotService;
import com.github.RuSichPT.WBtelegrambot.service.SendBotServiceImpl;
import com.github.RuSichPT.WBtelegrambot.service.TelegramUserService;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPricesImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CommandContainerTest {

    protected final SendBotService sendBotService = Mockito.mock(SendBotServiceImpl.class);
    protected final WbClientPrices wbClientPrices = Mockito.mock(WbClientPricesImpl.class);
    protected final TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
    private final CommandContainer commandContainer = new CommandContainer(sendBotService, wbClientPrices, telegramUserService);

    @Test
    public void shouldBeTheSameNumberCommand() {
        Assertions.assertEquals(commandContainer.size(), CommandName.values().length);
    }
}
