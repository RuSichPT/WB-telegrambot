package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.service.TelegramUserService;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoSet;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.JsonNode;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class SetPriceCommand extends AbstractWbCommand {
    private final WbClientPrices wbClientPrices;

    public static final String MESSAGE1 = "Чтобы поменять цену товара введите данные в виде: \n"
            + CommandName.SET_PRICE.getCommandName()
            + " номенклатура = цена\n\n"
            + "Пример:\n"
            + CommandName.SET_PRICE.getCommandName() + " 123456789 = 10256\n\n"
            + "Текущие цены на товары: \n";

    public static final String MESSAGE2 = "Цена товара %s успешно изменена на %s";
    public static final String MESSAGE3 = "Не удалось изменить цену!";

    public SetPriceCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, WbClientPrices wbClientPrices) {
        super(sendBotMessageService, telegramUserService);
        this.wbClientPrices = wbClientPrices;
    }

    @Override
    public void executeWbCommand(Update update) {
        String command = update.getMessage().getText();
        String[] comStrings = command.split(SPACE);
        String message = MESSAGE3;

        int COMMAND_LENGTH = 4;

        TelegramUser user = telegramUserService.findUserByChatId(update.getMessage().getChatId()).get();

        if (command.equalsIgnoreCase(CommandName.SET_PRICE.getCommandName())) {
            message = MESSAGE1 + wbClientPrices.getPriceInfo(0, user.getWbToken()).getBody().stream()
                    .map(p -> (String.format("%s = %s\n", p.getNmId(), p.getPrice())))
                    .collect(Collectors.joining());
        } else if (comStrings.length == COMMAND_LENGTH) {
            if (comStrings[0].equalsIgnoreCase(CommandName.SET_PRICE.getCommandName())) {
                if (comStrings[2].equalsIgnoreCase("=") && isNumeric(comStrings[3])) {
                    PriceInfoSet price = new PriceInfoSet(Integer.parseInt(comStrings[1]), Integer.parseInt(comStrings[3]));
                    HttpResponse<JsonNode> httpResponse = wbClientPrices.setPriceInfo(price, user.getWbToken());

                    if (httpResponse.getStatus() == HttpStatus.OK) {
                        message = String.format(MESSAGE2, price.getNmId(), price.getPrice());
                    }
                }
            }
        }
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), message);
    }
}