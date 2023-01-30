package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Order;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.OrderRequestArgs;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Orders;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import org.apache.http.HttpException;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class GetNumOrdersCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final WbClientPrices wbClientPrices;

    public static final String GET_NUM_ORDERS_COMMAND1 = "За все время было сделано заказов: %s";
    public static final String GET_NUM_ORDERS_COMMAND2 = "Не удалось получить количество заказов";
    public static final int MAX_LIMIT = 1000;

    public GetNumOrdersCommand(SendBotMessageService sendBotMessageService, WbClientPrices wbClientPrices) {
        this.sendBotMessageService = sendBotMessageService;
        this.wbClientPrices = wbClientPrices;
    }

    @Override
    public void execute(Update update) {
        String command = update.getMessage().getText();

        int sum = 0;
        Long next = 0L;
        String message;
        try {
            do {
                OrderRequestArgs args = OrderRequestArgs.builder()
                        .limit(MAX_LIMIT)
                        .next(next)
                        .build();

                HttpResponse<Orders> httpResponse = wbClientPrices.getOrders(args);

                if (httpResponse.getStatus() != HttpStatus.OK)
                    throw new HttpException();

                Orders orders = httpResponse.getBody();
                List<Order> ordersList = orders.getOrders();
                if (ordersList.size() < MAX_LIMIT)
                    next = 0L;
                else
                    next = Long.valueOf(orders.getNext());

                sum += ordersList.size();

            } while (next != 0L);

            message = String.format(GET_NUM_ORDERS_COMMAND1, sum);
        } catch (HttpException e) {
            message = GET_NUM_ORDERS_COMMAND2;
        }

        sendBotMessageService.sendMessage(update.getMessage().getChatId(), message);
    }
}
