package com.github.RuSichPT.WBtelegrambot.bot;

import com.github.RuSichPT.WBtelegrambot.command.Command;
import com.github.RuSichPT.WBtelegrambot.command.CommandContainer;
import com.github.RuSichPT.WBtelegrambot.command.CommandName;
import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageServiceImpl;
import com.github.RuSichPT.WBtelegrambot.service.TelegramUserService;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static com.github.RuSichPT.WBtelegrambot.command.CommandName.NO;

@Component
public class WbTelegramBot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";

    private final CommandContainer commandContainer;
    private Command lastCommand;

    private final BotConfig config;

    @Autowired
    public WbTelegramBot(BotConfig config, WbClientPrices wbClientPrices, TelegramUserService telegramUserService) {
        this.config = config;
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), wbClientPrices, telegramUserService);
        createMenu();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();

            String commandName;
            if (message.startsWith(COMMAND_PREFIX)) {
                commandName = message.split(" ")[0].toLowerCase();
            } else {
                commandName = NO.getCommandName();
            }
            lastCommand = commandContainer.findCommand(commandName);
            lastCommand.execute(update);
        } else if (update.hasCallbackQuery()) {
            lastCommand.executeCallback(update);
        }
    }

    @Override
    public String getBotUsername() {
        return config.getUsername();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    private List<BotCommand> getBotCommands() {
        CommandName[] commandNames = CommandName.values();
        List<BotCommand> botCommands = new ArrayList<>(commandNames.length);

        for (CommandName commandName : commandNames) {
            if (commandName != NO) {
                botCommands.add(new BotCommand(commandName.getCommandName(), commandName.getDescription()));
            }
        }

        return botCommands;
    }

    private void createMenu() {
        try {
            this.execute(new SetMyCommands(getBotCommands(), new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            //todo add logging to the project.
            e.printStackTrace();
        }
    }
}
