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

import static org.apache.commons.lang3.StringUtils.SPACE;

public class SetWbTokenCommand implements Command {
    protected final SendBotMessageService sendBotMessageService;
    protected final TelegramUserService telegramUserService;
    private final WbClientPrices wbClientPrices;

    public static final String MESSAGE1 = "Чтобы начать пользоваться ботом, введите WB токен в виде:\n"
            + CommandName.SET_WB_TOKEN.getCommandName() + " WB токен";
    public static final String MESSAGE2 = "Wildberries токен успешно сохранен! Теперь я буду уведомлять тебя о новых заказах.\n"
            + "Напиши /help чтобы узнать что я еще умею.";
    public static final String MESSAGE3 = "<b>Токен не действительный!</b>\n\n"
            + "Сгенерируйте новый в личном кабинете Wildberries.";
    public static final String MESSAGE4 = "Неправильный вид команды!";

    public SetWbTokenCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, WbClientPrices wbClientPrices) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.wbClientPrices = wbClientPrices;
    }

    @Override
    public void execute(Update update) {
        String command = update.getMessage().getText();
        String[] comStrings = command.split(SPACE);

        int COMMAND_LENGTH = 2;
        String message = MESSAGE4;

        if (command.equalsIgnoreCase(CommandName.SET_WB_TOKEN.getCommandName())) {
            message = MESSAGE1;
        } else if (comStrings.length == COMMAND_LENGTH) {
            message = MESSAGE2;

            HttpResponse<Orders> httpResponse = wbClientPrices.getNewOrders(comStrings[1]);

            try {
                if (httpResponse.getStatus() == HttpStatus.UNAUTHORIZED)
                    throw new HttpException();
            } catch (HttpException e) {
                message = MESSAGE3;
            }

            TelegramUser user = new TelegramUser(update.getMessage().getChatId(), update.getMessage().getFrom().getUserName());
            user.setWbToken(comStrings[1]);
            telegramUserService.saveUser(user);
        }

        sendBotMessageService.sendMessage(update.getMessage().getChatId(), message);
    }

    @Override
    public void executeCallback(Update update) {

    }
}
