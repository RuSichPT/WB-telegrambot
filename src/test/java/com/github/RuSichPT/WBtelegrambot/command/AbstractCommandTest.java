package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import com.github.RuSichPT.WBtelegrambot.service.SendBotService;
import com.github.RuSichPT.WBtelegrambot.service.SendBotServiceImpl;
import com.github.RuSichPT.WBtelegrambot.service.TelegramUserService;
import com.github.RuSichPT.WBtelegrambot.service.TelegramUserServiceImpl;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

public class AbstractCommandTest {

    protected final SendBotService sendBotService = Mockito.mock(SendBotServiceImpl.class);
    protected final TelegramUserService telegramUserService = Mockito.mock(TelegramUserServiceImpl.class);

    protected final Long chatId = 1234567824356L;

    protected Update getUpdate(String command) {
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        User user = Mockito.mock(User.class);
        CallbackQuery callbackQuery = Mockito.mock(CallbackQuery.class);

        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(command);
        Mockito.when(message.getFrom()).thenReturn(user);
        Mockito.when(user.getUserName()).thenReturn("username");
        Mockito.when(callbackQuery.getData()).thenReturn("0");
        Mockito.when(callbackQuery.getMessage()).thenReturn(message);

        update.setMessage(message);
        update.setCallbackQuery(callbackQuery);

        return update;
    }

    protected void initTelegramUserService() {
        TelegramUser user = Mockito.mock(TelegramUser.class);
        Optional<TelegramUser> opt = Optional.of(user);

        Mockito.when(telegramUserService.hasWbToken(chatId)).thenReturn(true);
        Mockito.when(telegramUserService.findUserByChatId(chatId)).thenReturn(opt);
        Mockito.when(user.getWbToken()).thenReturn("wb token");
    }

    protected void executeAndVerify(Command commandObj, String command, String answer) {
        //when
        commandObj.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotService).sendMessage(chatId, answer);
    }
}
