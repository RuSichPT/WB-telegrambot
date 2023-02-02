package com.github.RuSichPT.WBtelegrambot.service;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindNewOrderServiceImpl implements FindNewOrderService {

    private final SendBotMessageService sendBotMessageService;
    private final WbClientPrices wbClientPrices;
    private final TelegramUserService telegramUserService;

    public static final String MESSAGE = "Пришел новый заказ!";

    public FindNewOrderServiceImpl(SendBotMessageService sendBotMessageService, WbClientPrices wbClientPrices, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.wbClientPrices = wbClientPrices;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void findNewOrders() {

        int numNewOrders = wbClientPrices.getNewOrders().getBody().getOrders().size();

        if (numNewOrders != 0){

            List<TelegramUser> telegramUsers = telegramUserService.findAll();

            for (var tu:
                    telegramUsers) {
                if (tu.getNumNewOrders() != numNewOrders){
                    tu.setNumNewOrders(numNewOrders);
                    telegramUserService.saveUser(tu);

                    sendBotMessageService.sendMessage(tu.getChatId(), MESSAGE);
                }
            }
        }
    }
}
