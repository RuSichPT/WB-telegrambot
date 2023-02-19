package com.github.RuSichPT.WBtelegrambot.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tg_user")
@EqualsAndHashCode
public class TelegramUser {

    @Id
    @Column(name = "chatId")
    private Long chatId;

    @Unique
    @Column(name = "userName")
    private String userName;

    @Column(name = "numNewOrders")
    private Integer numNewOrders = 0;

    @Column(name = "wbToken")
    private String wbToken = "";

    public TelegramUser() {
    }

    public TelegramUser(TelegramUser telegramUser) {
        this.chatId = telegramUser.getChatId();
        this.userName = telegramUser.getUserName();
        this.numNewOrders = telegramUser.getNumNewOrders();
        this.wbToken = telegramUser.getWbToken();
    }

    public TelegramUser(Long chatId, String userName) {
        this.chatId = chatId;
        this.userName = userName;
    }
}
