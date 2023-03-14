package com.github.RuSichPT.WBtelegrambot.command;

import kong.unirest.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.github.RuSichPT.WBtelegrambot.command.CommandName.SET_WB_TOKEN;
import static com.github.RuSichPT.WBtelegrambot.command.SetWbTokenCommand.*;

public class SetWbTokenCommandTest extends AbstractWbClientCommandTest {

    private final SetWbTokenCommand setWbTokenCommand = new SetWbTokenCommand(sendBotService, telegramUserService, wbClientPrices);

    @BeforeEach
    public void init() {
        initHttpResponseOrders(2);
        Mockito.when(wbClientPrices.getNewOrders(Mockito.any(String.class))).thenReturn(httpResponseOrders);
        initTelegramUserService();
    }

    @Test
    public void shouldCorrectlySendMessage1() {
        //given
        String command = SET_WB_TOKEN.getCommandName();

        executeAndVerify(setWbTokenCommand, command, MESSAGE1);
    }

    @Test
    public void shouldCorrectlySendMessage2() {
        //given
        String command = SET_WB_TOKEN.getCommandName() + " 12654cvbdfg";

        executeAndVerify(setWbTokenCommand, command, MESSAGE2);
    }

    @Test
    public void shouldCorrectlySendMessage3() {
        //given
        String command = SET_WB_TOKEN.getCommandName() + " 12654cvbdfg";
        Mockito.when(httpResponseOrders.getStatus()).thenReturn(HttpStatus.UNAUTHORIZED);

        executeAndVerify(setWbTokenCommand, command, MESSAGE3);
    }

    @Test
    public void shouldCorrectlySendMessage4() {
        //given
        String command = SET_WB_TOKEN.getCommandName() + "12654cvbdfg";

        executeAndVerify(setWbTokenCommand, command, MESSAGE4);
    }
}
