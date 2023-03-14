package com.github.RuSichPT.WBtelegrambot.service;

import com.github.RuSichPT.WBtelegrambot.bot.WbTelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SendBotServiceTest {

    private SendBotServiceImpl sendBotService;
    private WbTelegramBot wbTelegramBot;

    @BeforeEach
    public void init() {
        wbTelegramBot = Mockito.mock(WbTelegramBot.class);
        sendBotService = new SendBotServiceImpl(wbTelegramBot);
    }

    @Test
    public void shouldProperlySendMessage() throws TelegramApiException {
        //given
        Long chatId = 123L;
        String message = "test_message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);

        //when
        sendBotService.sendMessage(chatId, message);

        //then
        Mockito.verify(wbTelegramBot).execute(sendMessage);
    }
}
