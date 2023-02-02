package com.github.RuSichPT.WBtelegrambot.repository;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {

}
