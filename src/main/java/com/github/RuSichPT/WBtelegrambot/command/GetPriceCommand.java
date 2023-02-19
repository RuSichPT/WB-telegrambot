package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.service.TelegramUserService;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfoGet;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetPriceCommand extends AbstractWbCommand {

    private final WbClientPrices wbClientPrices;
    public static final String MESSAGE1 =
            "Выберите фильтер для команды:\n\n";

    public static final String MESSAGE2 = "https://www.wildberries.ru/catalog/%s/detail.aspx? \n"
            + "Номенклатура товара: %s\n"
            + "Цена: %s\n"
            + "Скидка: %s%%\n"
            + "Промокод: %s\n\n";

    public static final String CALLBACK_MESSAGE1 = "0";
    public static final String CALLBACK_MESSAGE2 = "1";
    public static final String CALLBACK_MESSAGE3 = "2";


    public GetPriceCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, WbClientPrices wbClientPrices) {
        super(sendBotMessageService, telegramUserService);
        this.wbClientPrices = wbClientPrices;
    }

    @Override
    public void executeWbCommand(Update update) {
        String command = update.getMessage().getText();

        if (command.equalsIgnoreCase(CommandName.GET_PRICE.getCommandName())) {
            sendBotMessageService.sendMessage(update.getMessage().getChatId(), MESSAGE1, createButtons());
        }
    }

    @Override
    public void executeCallback(Update update) {

        int quantity = Integer.parseInt(update.getCallbackQuery().getData());
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        TelegramUser user = telegramUserService.findUserByChatId(chatId).get();
        List<PriceInfoGet> priceInfoList = wbClientPrices.getPriceInfo(quantity, user.getWbToken()).getBody();

        String message = priceInfoList.stream()
                .map(pI -> (String.format(MESSAGE2, pI.getNmId(), pI.getNmId(), pI.getPrice(), pI.getDiscount(), pI.getPromoCode())))
                .collect(Collectors.joining());

        sendBotMessageService.sendMessage(chatId, message);
    }

    private List<List<InlineKeyboardButton>> createButtons() {
        InlineKeyboardButton button0 = new InlineKeyboardButton("Весь товар");
        button0.setCallbackData(CALLBACK_MESSAGE1);
        InlineKeyboardButton button1 = new InlineKeyboardButton("Товар с ненулевым остатком");
        button1.setCallbackData(CALLBACK_MESSAGE2);
        InlineKeyboardButton button2 = new InlineKeyboardButton("Товар с нулевым остатком");
        button2.setCallbackData(CALLBACK_MESSAGE3);

        List<InlineKeyboardButton> row0 = new ArrayList<>();
        row0.add(button0);

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(button1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(button2);

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(row0);
        buttons.add(row1);
        buttons.add(row2);

        return buttons;
    }


}
