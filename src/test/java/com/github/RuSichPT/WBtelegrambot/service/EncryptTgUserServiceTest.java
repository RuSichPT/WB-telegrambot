package com.github.RuSichPT.WBtelegrambot.service;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EncryptTgUserServiceTest {

    private final EncryptTgUserService encryptTgUserService;

    public EncryptTgUserServiceTest(@Value("${encrypt.password}") int password) {
        encryptTgUserService = new EncryptTgUserServiceImpl(password);
    }


    @Test
    public void shouldCorrectlyEncrypting() {
        //given
        TelegramUser telegramUser = new TelegramUser(12456L, "myName");
        telegramUser.setWbToken("sdjsljgsdg;adsgla4ggak");

        TelegramUser encryptedTgUser = new TelegramUser(telegramUser);

        //when
        encryptTgUserService.code(encryptedTgUser);
        encryptTgUserService.decode(encryptedTgUser);

        //then
        Assertions.assertEquals(telegramUser, encryptedTgUser);
    }
}
