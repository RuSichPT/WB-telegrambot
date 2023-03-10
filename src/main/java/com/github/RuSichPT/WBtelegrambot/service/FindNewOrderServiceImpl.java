package com.github.RuSichPT.WBtelegrambot.service;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Order;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Orders;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindNewOrderServiceImpl implements FindNewOrderService {

    private final SendBotMessageService sendBotMessageService;
    private final WbClientPrices wbClientPrices;
    private final TelegramUserService telegramUserService;

    public static final String MESSAGE = "Новый заказ:\n"
            + "Номенклатура товара: %s\n"
            + "Артикул: %s\n"
            + "Цена: %s\n\n";

    public FindNewOrderServiceImpl(SendBotMessageService sendBotMessageService, WbClientPrices wbClientPrices, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.wbClientPrices = wbClientPrices;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void findNewOrders() {

        List<TelegramUser> telegramUsers = telegramUserService.findAll();

        for (TelegramUser tu :
                telegramUsers) {

            HttpResponse<Orders> httpResponse = wbClientPrices.getNewOrders(tu.getWbToken());

            if (httpResponse.getStatus() == HttpStatus.OK) {

                int numNewOrders = httpResponse.getBody().getOrders().size();

                if (tu.getNumNewOrders() != numNewOrders) {

                    tu.setNumNewOrders(numNewOrders);
                    telegramUserService.saveUser(tu);

                    if (numNewOrders != 0) {
                        List<Order> orders = httpResponse.getBody().getOrders();
                        String message = orders.stream()
                                .map(o -> (String.format(MESSAGE, o.getNmId(), o.getArticle(), o.getPrice())))
                                .collect(Collectors.joining());
                        sendBotMessageService.sendMessage(tu.getChatId(), message);
                    }
                }
            }
        }
    }
}
