package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.RuSichPT.WBtelegrambot.command.CommandName.*;

/**
 * Help {@link Command}.
 */
public class HelpCommand implements Command{

    private final SendBotMessageService sendBotMessageService;

    public final static String HELP_MESSAGE = String.format("✨<b>Доcтупные команды</b>✨\n\n"

                    + "<b>Начать\\закончить работу с ботом</b>\n"
                    + "%s - начать работу со мной\n"
                    + "%s - приостановить работу со мной\n\n"

                    + "%s - получить информацию по номенклатурам, их ценам, скидкам и промокодам.\n"
                    + "%s 2 - товар с нулевым остатком.\n"
                    + "%s 1 - товар с ненулевым остатком.\n"
                    + "Если не указывать фильтры, вернётся весь товар.\n\n"

                    + "%s - получить помощь в работе со мной\n",
            START.getCommandName(), STOP.getCommandName(), GET_PRICE_INFO.getCommandName(),
            GET_PRICE_INFO.getCommandName(), GET_PRICE_INFO.getCommandName(),
            HELP.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), HELP_MESSAGE);
    }
}