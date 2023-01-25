package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoSet;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.JsonNode;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class SetPriceCommand implements Command {

    private final WbClientPrices wbClientPrices;
    private final SendBotMessageService sendBotMessageService;
    private final int COMMAND_LENGTH = 4;

    public SetPriceCommand(SendBotMessageService sendBotMessageService, WbClientPrices wbClientPrices) {
        this.sendBotMessageService = sendBotMessageService;
        this.wbClientPrices = wbClientPrices;
    }

    @Override
    public void execute(Update update) {
        String command = update.getMessage().getText();
        String[] comStrings = command.split(SPACE);
        String message = "Не удалось изменить цену!";

        if (command.equalsIgnoreCase(CommandName.SET_PRICE.getCommandName()))
        {
            String str = "Чтобы поменять цену товара введите данные в виде: \n"
                    + CommandName.SET_PRICE.getCommandName()
                    + " номенклатура = цена.\n"
                    + "Доступные номенклатуры: \n";
            message = str + wbClientPrices.getPriceInfo(0).stream()
                    .map(p->(String.format("%s\n",p.getNmId())))
                    .collect(Collectors.joining());
        }
        else if (comStrings.length == COMMAND_LENGTH)
        {
            if (comStrings[0].equalsIgnoreCase(CommandName.SET_PRICE.getCommandName()))
            {
                if (comStrings[2].equalsIgnoreCase("=") && isNumeric(comStrings[3]))
                {
                    PriceInfoSet price = new PriceInfoSet(Integer.valueOf(comStrings[1]), Integer.valueOf(comStrings[3]));
                    HttpResponse<JsonNode> httpResponse = wbClientPrices.setPriceInfo(price);

                    if (httpResponse.getStatus() == HttpStatus.OK)
                    {
                        message = String.format("Цена товара %s успешно изменена на %s", price.getNmId(),price.getPrice());
                    }
                }
            }
        }

        sendBotMessageService.sendMessage(update.getMessage().getChatId(),message);
    }
}
