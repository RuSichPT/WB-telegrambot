package com.github.RuSichPT.WBtelegrambot.command;

/**
 * Enumeration for {@link Command}'s.
 */
public enum CommandName {
    START("/start", "Начать работу с ботом."),
    STOP("/stop", "Закончить работу с ботом."),
    HELP("/help", "Получить помощь по работе с ботом."),
    NO("", ""),
    GET_PRICE("/getpriceinfo", "Получить информацию о товарах на Wildberries."),
    SET_PRICE("/setprice", "Установить новую цену для товара."),
    SET_DISCOUNT("/setdiscount", "Установить новую скидку для товара."),
    GET_ALL_ORDERS("/getallorders", "Получить количество всех заказов (маркетплейс)."),
    GET_NEW_ORDERS("/getneworders", "Получить количеств новых заказов (маркетплейс)."),
    SET_WB_TOKEN("/setwbtoken", "Установить Wildberries токен"),
    TEST("/test", "");

    private final String commandName;

    private final String description;

    CommandName(String command, String description) {
        this.commandName = command;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCommandName() {
        return commandName;
    }
}
