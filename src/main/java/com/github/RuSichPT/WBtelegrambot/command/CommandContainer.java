package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.service.TelegramUserService;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import com.google.common.collect.ImmutableMap;

import static com.github.RuSichPT.WBtelegrambot.command.CommandName.*;

/**
 * Container of the {@link Command}s, which are using for handling telegram commands.
 */
public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService, WbClientPrices wbClientPrices, TelegramUserService telegramUserService) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(GET_PRICE.getCommandName(), new GetPriceCommand(sendBotMessageService, telegramUserService, wbClientPrices))
                .put(SET_PRICE.getCommandName(), new SetPriceCommand(sendBotMessageService, telegramUserService, wbClientPrices))
                .put(SET_DISCOUNT.getCommandName(), new SetDiscountCommand(sendBotMessageService, telegramUserService, wbClientPrices))
                .put(GET_ALL_ORDERS.getCommandName(), new GetAllOrdersCommand(sendBotMessageService, telegramUserService, wbClientPrices))
                .put(GET_NEW_ORDERS.getCommandName(), new GetNewOrdersCommand(sendBotMessageService, telegramUserService, wbClientPrices))
                .put(SET_WB_TOKEN.getCommandName(), new SetWbTokenCommand(sendBotMessageService, telegramUserService, wbClientPrices))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command findCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }

    public int size() {
        return commandMap.size();
    }
}
