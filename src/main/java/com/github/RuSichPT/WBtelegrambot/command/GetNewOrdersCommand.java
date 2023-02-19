package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.service.TelegramUserService;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Orders;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import org.apache.http.HttpException;
import org.telegram.telegrambots.meta.api.objects.Update;

public class GetNewOrdersCommand extends AbstractWbCommand {

    private final WbClientPrices wbClientPrices;

    public static final String MESSAGE1 = "Новых заказов: %s\n";
    public static final String MESSAGE2 = "Не удалось получить количество заказов";

    public GetNewOrdersCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, WbClientPrices wbClientPrices) {
        super(sendBotMessageService, telegramUserService);
        this.wbClientPrices = wbClientPrices;
    }

    @Override
    public void executeWbCommand(Update update) {
        String command = update.getMessage().getText();
        String message;
        try {
            TelegramUser user = telegramUserService.findUserByChatId(update.getMessage().getChatId()).get();
            HttpResponse<Orders> httpResponse = wbClientPrices.getNewOrders(user.getWbToken());

            if (httpResponse.getStatus() != HttpStatus.OK)
                throw new HttpException();

            message = String.format(MESSAGE1, httpResponse.getBody().getOrders().size());

        } catch (HttpException e) {
            message = MESSAGE2;
        }

        sendBotMessageService.sendMessage(update.getMessage().getChatId(), message);
    }
}
