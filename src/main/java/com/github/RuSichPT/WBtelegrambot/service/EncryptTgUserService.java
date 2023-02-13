package com.github.RuSichPT.WBtelegrambot.service;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;

public interface EncryptTgUserService {

    void code(TelegramUser telegramUser);

    void decode(TelegramUser telegramUser);
}
