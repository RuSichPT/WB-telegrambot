package com.github.RuSichPT.WBtelegrambot.service;

import com.github.RuSichPT.WBtelegrambot.repository.TelegramUserRepository;
import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelegramUserServiceImpl implements TelegramUserService {

    private final TelegramUserRepository telegramUserRepository;

    @Autowired
    public TelegramUserServiceImpl(TelegramUserRepository telegramUserRepository) {
        this.telegramUserRepository = telegramUserRepository;
    }

    @Override
    public void deleteUserByChatId(Long chatId) {
        telegramUserRepository.deleteById(chatId);
    }

    @Override
    public void saveUser(TelegramUser user) {
        telegramUserRepository.save(user);
    }

    @Override
    public Optional<TelegramUser> findUserByChatId(Long chatId) {
        return telegramUserRepository.findById(chatId);
    }

    @Override
    public List<TelegramUser> findAll() {
        return telegramUserRepository.findAll();
    }

    @Override
    public boolean hasWbToken(Long chatId) {

        Optional<TelegramUser> userOptional = findUserByChatId(chatId);

        if (userOptional.isPresent()) {
            TelegramUser user = userOptional.get();

            return !user.getWbToken().isEmpty();
        }

        return false;
    }
}
