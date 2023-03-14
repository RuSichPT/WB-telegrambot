package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotService;
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

    public CommandContainer(SendBotService sendBotService, WbClientPrices wbClientPrices, TelegramUserService telegramUserService) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotService, telegramUserService))
                .put(STOP.getCommandName(), new StopCommand(sendBotService, telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotService))
                .put(NO.getCommandName(), new NoCommand(sendBotService))
                .put(GET_PRICE.getCommandName(), new GetPriceCommand(sendBotService, telegramUserService, wbClientPrices))
                .put(SET_PRICE.getCommandName(), new SetPriceCommand(sendBotService, telegramUserService, wbClientPrices))
                .put(SET_DISCOUNT.getCommandName(), new SetDiscountCommand(sendBotService, telegramUserService, wbClientPrices))
                .put(GET_ALL_ORDERS.getCommandName(), new GetAllOrdersCommand(sendBotService, telegramUserService, wbClientPrices))
                .put(GET_NEW_ORDERS.getCommandName(), new GetNewOrdersCommand(sendBotService, telegramUserService, wbClientPrices))
                .put(SET_WB_TOKEN.getCommandName(), new SetWbTokenCommand(sendBotService, telegramUserService, wbClientPrices))
                .put(TEST.getCommandName(), new TestCommand(sendBotService))
                .build();

        unknownCommand = new UnknownCommand(sendBotService);
    }

    public Command findCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }

    public int size() {
        return commandMap.size();
    }
}
