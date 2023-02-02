package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.RuSichPT.WBtelegrambot.command.CommandName.*;

/**
 * Help {@link Command}.
 */
public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public final static String HELP_MESSAGE = String.format("✨<b>Доcтупные команды</b>✨\n\n"

                    + "<b>Начать\\закончить работу с ботом</b>\n"
                    + "%s - начать работу со мной\n"
                    + "%s - приостановить работу со мной\n\n"

                    + "<b>Работа с товарами</b>\n"
                    + "%s - получить  информацию по номенклатурам, их ценам, скидкам и промокодам.\n"
                    + "%s - изменить цену товара.\n"
                    + "%s - изменить скидку товара.\n\n"

                    + "<b>Работа с заказами</b>\n"
                    + "%s - получить общее число заказов.\n"
                    + "%s - получить число новых заказов.\n\n"

                    + "%s - получить помощь в работе со мной\n",
            START.getCommandName(), STOP.getCommandName(),
            GET_PRICE.getCommandName(), SET_PRICE.getCommandName(), SET_DISCOUNT.getCommandName(),
            GET_NUM_ORDERS.getCommandName(), GET_NEW_ORDERS.getCommandName(),
            HELP.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), HELP_MESSAGE);
    }
}