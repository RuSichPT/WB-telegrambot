package com.github.RuSichPT.WBtelegrambot.service;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface TelegramUserService {

    void deleteUserByChatId(Long chatId);

    void saveUser(TelegramUser user);

    Optional<TelegramUser> findUserByChatId(Long chatId);

    List<TelegramUser> findAll();
}
