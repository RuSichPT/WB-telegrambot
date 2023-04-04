package com.github.RuSichPT.WBtelegrambot.service;

import com.github.RuSichPT.WBtelegrambot.bot.WbTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * Implementation of {@link SendBotService} interface.
 */
@Service
public class SendBotServiceImpl implements SendBotService {

    private final WbTelegramBot wbTelegramBot;

    @Autowired
    public SendBotServiceImpl(WbTelegramBot wbTelegramBot) {
        this.wbTelegramBot = wbTelegramBot;
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = createSendMessage(chatId, message);

        executeSendMessage(sendMessage);
    }

    @Override
    public void sendMessage(Long chatId, List<String> messages) {
        if (isEmpty(messages)) return;

        messages.forEach(m -> sendMessage(chatId, m));
    }

    @Override
    public void sendMessage(Long chatId, String message, List<List<InlineKeyboardButton>> listButtons) {

        SendMessage sendMessage = createSendMessage(chatId, message);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(listButtons);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        executeSendMessage(sendMessage);
    }

    @Override
    public void sendPhoto(Long chatId, URL url, String message) throws IOException {
        InputStream stream = url.openStream();

        InputFile photo = new InputFile(stream, "photo");
        SendPhoto sendPhoto = new SendPhoto(String.valueOf(chatId), photo);
        sendPhoto.setCaption(message);

        try {
            wbTelegramBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        finally {
            stream.close();
        }
    }

    private SendMessage createSendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        return sendMessage;
    }

    private void executeSendMessage(SendMessage sendMessage) {
        try {
            wbTelegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            //todo add logging to the project.
            e.printStackTrace();
        }
    }
}
