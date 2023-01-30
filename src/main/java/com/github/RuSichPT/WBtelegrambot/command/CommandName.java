package com.github.RuSichPT.WBtelegrambot.command;

/**
 * Enumeration for {@link Command}'s.
 */
public enum CommandName {
    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    NO(""),
    GET_PRICE("/getpriceinfo"),
    SET_PRICE("/setprice"),
    SET_DISCOUNT("/setdiscount"),
    GET_NUM_ORDERS("/getnumorders");

    private final String commandName;

    CommandName(String command) {
        this.commandName = command;
    }

    public String getCommandName() {
        return commandName;
    }
}
