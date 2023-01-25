package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoGet;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class GetPriceCommand implements Command {

    private final WbClientPrices wbClientPrices;
    private final SendBotMessageService sendBotMessageService;
    private final int COMMAND_LENGTH = 2;

    public GetPriceCommand(SendBotMessageService sendBotMessageService, WbClientPrices wbClientPrices) {
        this.sendBotMessageService = sendBotMessageService;
        this.wbClientPrices = wbClientPrices;
    }

    @Override
    public void execute(Update update) {
        String command = update.getMessage().getText();
        String[] comStrings = command.split(SPACE);
        String message = "Неправильно указан filter. Возможные значения: \n"
                + "2 - товар с нулевым остатком.\n"
                + "1 - товар с ненулевым остатком.\n"
                + "0 - вернётся весь товар.\n\n";

        if (command.equalsIgnoreCase(CommandName.GET_PRICE.getCommandName()))
        {
            message = "Чтобы получить информацию по номенклатурам, их ценам, скидкам и промокодам введите данные в виде:\n"
                    + CommandName.GET_PRICE.getCommandName() + " filter \n"
                    + "где filter:\n"
                    + "2 - товар с нулевым остатком.\n"
                    + "1 - товар с ненулевым остатком.\n"
                    + "0 - вернётся весь товар.\n\n";
        }
        else if (comStrings.length == COMMAND_LENGTH)
        {
            if (isNumeric(comStrings[1]))
            {
                int quantity = Integer.parseInt(comStrings[1]);

                List<PriceInfoGet> priceInfoList = wbClientPrices.getPriceInfo(quantity);

                String str = "Номенклатура товара: %s\n" +
                        "Цена: %s\n" + "Скидка: %s\n" + "Промокод: %s\n\n";
                message = priceInfoList.stream()
                        .map(pI->(String.format(str,pI.getNmId(),pI.getPrice(),pI.getDiscount(),pI.getPromoCode())))
                        .collect(Collectors.joining());
            }
        }

        sendBotMessageService.sendMessage(update.getMessage().getChatId(), message);
    }
}