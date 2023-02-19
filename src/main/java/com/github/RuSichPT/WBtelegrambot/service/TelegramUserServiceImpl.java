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
    private final EncryptTgUserService encryptTgUserService;

    @Autowired
    public TelegramUserServiceImpl(TelegramUserRepository telegramUserRepository, EncryptTgUserService encryptTgUserService) {
        this.telegramUserRepository = telegramUserRepository;
        this.encryptTgUserService = encryptTgUserService;
    }

    @Override
    public void deleteUserByChatId(Long chatId) {
        telegramUserRepository.deleteById(chatId);
    }

    @Override
    public void saveUser(TelegramUser user) {
        encryptTgUserService.code(user);
        telegramUserRepository.save(user);
    }

    @Override
    public Optional<TelegramUser> findUserByChatId(Long chatId) {
        Optional<TelegramUser> opt = telegramUserRepository.findById(chatId);

        opt.ifPresent(encryptTgUserService::decode);

        return opt;
    }

    @Override
    public List<TelegramUser> findAll() {

        List<TelegramUser> list = telegramUserRepository.findAll();

        for (TelegramUser tgU :
                list) {
            encryptTgUserService.decode(tgU);
        }

        return list;
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
