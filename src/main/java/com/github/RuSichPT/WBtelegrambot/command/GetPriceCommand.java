package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.service.TelegramUserService;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoGet;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class GetPriceCommand extends AbstractWbCommand {

    private final WbClientPrices wbClientPrices;
    public static final String MESSAGE1 =
            "Чтобы получить информацию по номенклатурам, их ценам, скидкам и промокодам введите данные в виде:\n"
                    + CommandName.GET_PRICE.getCommandName() + " filter \n"
                    + "где filter:\n"
                    + "2 - товар с нулевым остатком.\n"
                    + "1 - товар с ненулевым остатком.\n"
                    + "0 - вернётся весь товар.\n\n";

    public static final String MESSAGE2 = "https://www.wildberries.ru/catalog/%s/detail.aspx? \n"
            + "Номенклатура товара: %s\n"
            + "Цена: %s\n"
            + "Скидка: %s%%\n"
            + "Промокод: %s\n\n";

    public static final String MESSAGE3 = "Неправильно указан filter. Возможные значения: \n"
            + "2 - товар с нулевым остатком.\n"
            + "1 - товар с ненулевым остатком.\n"
            + "0 - вернётся весь товар.\n\n";


    public GetPriceCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, WbClientPrices wbClientPrices) {
        super(sendBotMessageService, telegramUserService);
        this.wbClientPrices = wbClientPrices;
    }

    @Override
    public String executeWbCommand(Update update) {
        String command = update.getMessage().getText();
        String[] comStrings = command.split(SPACE);
        String message = MESSAGE3;

        int COMMAND_LENGTH = 2;

        if (command.equalsIgnoreCase(CommandName.GET_PRICE.getCommandName())) {
            message = MESSAGE1;
        } else if (comStrings.length == COMMAND_LENGTH) {
            if (isNumeric(comStrings[1])) {
                int quantity = Integer.parseInt(comStrings[1]);

                TelegramUser user = telegramUserService.findUserByChatId(update.getMessage().getChatId()).get();
                List<PriceInfoGet> priceInfoList = wbClientPrices.getPriceInfo(quantity, user.getWbToken()).getBody();

                message = priceInfoList.stream()
                        .map(pI -> (String.format(MESSAGE2, pI.getNmId(), pI.getNmId(), pI.getPrice(), pI.getDiscount(), pI.getPromoCode())))
                        .collect(Collectors.joining());
            }
        }

        return message;
    }
}
