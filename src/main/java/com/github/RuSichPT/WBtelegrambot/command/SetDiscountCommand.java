package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Discount;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.JsonNode;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class SetDiscountCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final WbClientPrices wbClientPrices;

    public static final String SET_DISCOUNT_MESSAGE1 = "Чтобы поменять скидку у товара введите данные в виде: \n"
            + CommandName.SET_DISCOUNT.getCommandName()
            + " номенклатура = скидка\n"
            + "Пример:\n"
            + CommandName.SET_DISCOUNT.getCommandName() + " 123456789 = 15\n\n"
            + "Текущие скидки на товары: \n";
    public static final String SET_DISCOUNT_MESSAGE2 = "Скидка товара %s успешно изменена на %s%%";
    public static final String SET_DISCOUNT_MESSAGE3 = "Не удалось изменить скидку!";

    public SetDiscountCommand(SendBotMessageService sendBotMessageService, WbClientPrices wbClientPrices) {
        this.sendBotMessageService = sendBotMessageService;
        this.wbClientPrices = wbClientPrices;
    }

    @Override
    public void execute(Update update) {
        String command = update.getMessage().getText();
        String[] comStrings = command.split(SPACE);
        String message = SET_DISCOUNT_MESSAGE3;

        int COMMAND_LENGTH = 4;

        if (command.equalsIgnoreCase(CommandName.SET_DISCOUNT.getCommandName())) {
            message = SET_DISCOUNT_MESSAGE1 + wbClientPrices.getPriceInfo(0).stream()
                    .map(p -> (String.format("%s = %s%%\n", p.getNmId(), p.getDiscount())))
                    .collect(Collectors.joining());
        } else if (comStrings.length == COMMAND_LENGTH) {
            if (comStrings[0].equalsIgnoreCase(CommandName.SET_DISCOUNT.getCommandName())) {
                if (comStrings[2].equalsIgnoreCase("=") && isNumeric(comStrings[3])) {
                    Discount discount = new Discount(Integer.parseInt(comStrings[1]), Integer.parseInt(comStrings[3]));
                    HttpResponse<JsonNode> httpResponse = wbClientPrices.setDiscount(discount);

                    if (httpResponse.getStatus() == HttpStatus.OK) {
                        message = String.format(SET_DISCOUNT_MESSAGE2, discount.getNm(), discount.getDiscount());
                    }
                }
            }
        }

        sendBotMessageService.sendMessage(update.getMessage().getChatId(), message);
    }
}
