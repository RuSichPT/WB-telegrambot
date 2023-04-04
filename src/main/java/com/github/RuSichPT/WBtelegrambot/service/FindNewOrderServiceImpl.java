package com.github.RuSichPT.WBtelegrambot.service;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Order;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Orders;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class FindNewOrderServiceImpl implements FindNewOrderService {

    public static final String HOST = "https://basket-10.wb.ru";

    private final SendBotService sendBotService;
    private final WbClientPrices wbClientPrices;
    private final TelegramUserService telegramUserService;

    public static final String MESSAGE = "Новый заказ!\n";

    public FindNewOrderServiceImpl(SendBotService sendBotService, WbClientPrices wbClientPrices, TelegramUserService telegramUserService) {
        this.sendBotService = sendBotService;
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
                List<Order> orders = httpResponse.getBody().getOrders();
                int numNewOrders = orders.size();

                if (tu.getNumNewOrders() != numNewOrders) {

                    tu.setNumNewOrders(numNewOrders);
                    telegramUserService.saveUser(tu);

                    if (numNewOrders != 0) {
                        notify(tu, orders);
                    }
                }
            }
        }
    }

    private URL getUrlPhoto(int nmId) throws MalformedURLException {
        String id = Integer.toString(nmId);
        StringBuilder vol = new StringBuilder("/vol");
        StringBuilder part = new StringBuilder("/part");
        for (int i = 0; i < 6; i++) {
            if (i < 4)
                vol.append(id.charAt(i));
            part.append(id.charAt(i));
        }
        String urlStr = HOST + vol + part + "/" + nmId + "/images/big/1.jpg";

        return new URL(urlStr);
    }

    private void notify(TelegramUser user, List<Order> orders) {
        Long chatId = user.getChatId();
        String message;
        for (Order o :
                orders) {
            message = MESSAGE + o.toStringShort() + "\n";
            try {
                URL url = getUrlPhoto(o.getNmId());
                sendBotService.sendPhoto(chatId, url, message);
            } catch (IOException e) {
                sendBotService.sendMessage(chatId, message);
            }
        }
    }

}
