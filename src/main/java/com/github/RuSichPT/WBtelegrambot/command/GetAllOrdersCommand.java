package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.service.TelegramUserService;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Order;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.OrderRequestArgs;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Orders;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import org.apache.http.HttpException;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class GetAllOrdersCommand extends AbstractWbCommand {

    private final WbClientPrices wbClientPrices;

    public static final String MESSAGE1 = "За все время было сделано заказов: %s";
    public static final String MESSAGE2 = "Не удалось получить количество заказов";
    public static final int MAX_LIMIT = 1000;

    public GetAllOrdersCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, WbClientPrices wbClientPrices) {
        super(sendBotMessageService, telegramUserService);
        this.wbClientPrices = wbClientPrices;
    }

    @Override
    public String executeWbCommand(Update update) {
        String command = update.getMessage().getText();
        String message;
        try {
            int sum = 0;
            Long next = 0L;
            do {
                OrderRequestArgs args = OrderRequestArgs.builder()
                        .limit(MAX_LIMIT)
                        .next(next)
                        .build();

                TelegramUser user = telegramUserService.findUserByChatId(update.getMessage().getChatId()).get();
                HttpResponse<Orders> httpResponse = wbClientPrices.getOrders(args, user.getWbToken());

                if (httpResponse.getStatus() != HttpStatus.OK)
                    throw new HttpException();

                Orders orders = httpResponse.getBody();
                List<Order> ordersList = orders.getOrders();
                if (ordersList.size() < MAX_LIMIT) {
                    next = 0L;
                } else {
                    next = Long.valueOf(orders.getNext());
                }

                sum += ordersList.size();

            } while (next != 0L);

            message = String.format(MESSAGE1, sum);
        } catch (HttpException e) {
            message = MESSAGE2;
        }

        return message;
    }
}
