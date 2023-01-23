package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfo;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class GetPriceCommand implements Command {

    private final WbClientPrices wbClientPrices;
    private final SendBotMessageService sendBotMessageService;

    public GetPriceCommand(SendBotMessageService sendBotMessageService, WbClientPrices wbClientPrices) {
        this.sendBotMessageService = sendBotMessageService;
        this.wbClientPrices = wbClientPrices;
    }

    @Override
    public void execute(Update update) {

        String[] quantityArray = update.getMessage().getText().split(" ");
        String quantity = "0";

        if (quantityArray.length > 2)
        {
            if (isNumeric(quantityArray[1]))
            {
                quantity = quantityArray[1];
            }
        }

        List<PriceInfo> priceInfoList = wbClientPrices.getPriceInfo(Integer.valueOf(quantity));

        String str = "Номенклатура товара: %s\n" +
                "Цена: %s\n" + "Скидка: %s\n" + "Промокод: %s\n\n";
        String message = priceInfoList.stream()
                .map(pI->(String.format(str,pI.getNmId(),pI.getPrice(),pI.getDiscount(),pI.getPromoCode())))
                .collect(Collectors.joining());

        sendBotMessageService.sendMessage(update.getMessage().getChatId(), message);
    }
}
