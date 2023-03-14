package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.FindNewOrderServiceImpl;
import com.github.RuSichPT.WBtelegrambot.service.SendBotService;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.URL;

@AllArgsConstructor
public class TestCommand implements Command {
    private final SendBotService sendBotService;

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        String message = "test command";
        try {
            URL url = new URL("https://basket-10.wb.ru/vol1374/part137435/137435068/images/big/1.jpg");
            sendBotService.sendPhoto(chatId, url, FindNewOrderServiceImpl.MESSAGE);
        } catch (IOException e) {
            sendBotService.sendMessage(chatId, "url");
        }
    }
}
