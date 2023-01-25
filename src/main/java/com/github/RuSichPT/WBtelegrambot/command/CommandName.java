package com.github.RuSichPT.WBtelegrambot.command;

/**
 * Enumeration for {@link Command}'s.
 */
public enum CommandName {
    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    STAT("/stat"),
    NO(""),
    GET_PRICE("/getpriceinfo"),
    SET_PRICE("/setprice");

    private final String commandName;

    CommandName(String command) {
        this.commandName = command;
    }

    public String getCommandName() {
        return commandName;
    }
}
