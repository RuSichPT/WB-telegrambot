package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Orders;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import org.apache.http.HttpException;
import org.telegram.telegrambots.meta.api.objects.Update;

public class GetNewOrdersCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final WbClientPrices wbClientPrices;

    public static final String GET_NUM_ORDERS_COMMAND1 = "Новых заказов: %s\n";
    public static final String GET_NEW_ORDERS_COMMAND2 = "Не удалось получить количество заказов";

    public GetNewOrdersCommand(SendBotMessageService sendBotMessageService, WbClientPrices wbClientPrices) {
        this.sendBotMessageService = sendBotMessageService;
        this.wbClientPrices = wbClientPrices;
    }

    @Override
    public void execute(Update update) {

        String command = update.getMessage().getText();

        String message;
        try {
            HttpResponse<Orders> httpResponse = wbClientPrices.getNewOrders();

            if (httpResponse.getStatus() != HttpStatus.OK)
                throw new HttpException();

            message = String.format(GET_NUM_ORDERS_COMMAND1, httpResponse.getBody().getOrders().size());

        } catch (HttpException e) {
            message = GET_NEW_ORDERS_COMMAND2;
        }

        sendBotMessageService.sendMessage(update.getMessage().getChatId(), message);
    }
}
