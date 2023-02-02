package com.github.RuSichPT.WBtelegrambot.repository;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class TelegramUserRepositoryIntTest {

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    @Sql(scripts = {"/sql/delete_tg_user.sql","/sql/insert_5_tg_users.sql"})
    @Test
    public void shouldCorrectlyFindByIdTelegramUser() {
        //given
        TelegramUser newUser = new TelegramUser(12345L, "test1");

        //while
        TelegramUser user = telegramUserRepository.findById(newUser.getChatId()).get();

        //then
        Assertions.assertEquals(newUser, user);
    }

    @Sql(scripts = {"/sql/delete_tg_user.sql","/sql/insert_5_tg_users.sql"})
    @Test
    public void shouldCorrectlyFindAllTelegramUser() {
        //given

        //while
        List<TelegramUser> telegramUsers = telegramUserRepository.findAll();

        //then
        Assertions.assertEquals(telegramUsers.size(), 5);
    }

    @Sql(scripts = {"/sql/delete_tg_user.sql"})
    @Test
    public void shouldCorrectlySaveTelegramUser(){
        //given
        TelegramUser newUser = new TelegramUser(54123L, "test");

        //while
        telegramUserRepository.save(newUser);
        TelegramUser user = telegramUserRepository.findById(newUser.getChatId()).get();

        //then
        Assertions.assertEquals(newUser, user);
    }

    @Sql(scripts = {"/sql/delete_tg_user.sql","/sql/insert_5_tg_users.sql"})
    @Test
    public void shouldCorrectlyDeleteTelegramUser(){
        //given
        TelegramUser newUser = new TelegramUser(12345L, "test1");

        //while
        telegramUserRepository.deleteById(newUser.getChatId());

        //then
        Assertions.assertTrue(telegramUserRepository.findById(newUser.getChatId()).isEmpty());
    }
}
