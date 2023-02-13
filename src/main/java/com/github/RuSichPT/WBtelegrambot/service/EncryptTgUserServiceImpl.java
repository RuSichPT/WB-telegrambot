package com.github.RuSichPT.WBtelegrambot.service;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EncryptTgUserServiceImpl implements EncryptTgUserService {

    private final int password;

    public EncryptTgUserServiceImpl(@Value("${encrypt.password}") int password) {
        this.password = password;
    }

    @Override
    public void code(TelegramUser telegramUser) {
        crypt(telegramUser);
    }

    @Override
    public void decode(TelegramUser telegramUser) {
        crypt(telegramUser);
    }

    private void crypt(TelegramUser telegramUser) {

        String newUserName = xorString(telegramUser.getUserName());
        String newWbToken = xorString(telegramUser.getWbToken());

        telegramUser.setUserName(newUserName);
        telegramUser.setWbToken(newWbToken);
    }

    private String xorString(String str) {
        char[] chars = str.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] ^ password);
        }
        return new String(chars);
    }
}

