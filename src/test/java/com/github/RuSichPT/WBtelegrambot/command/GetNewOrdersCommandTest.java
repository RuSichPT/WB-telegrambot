package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Order;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Orders;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static com.github.RuSichPT.WBtelegrambot.command.GetNewOrdersCommand.GET_NUM_ORDERS_COMMAND1;
import static com.github.RuSichPT.WBtelegrambot.command.GetNumOrdersCommand.GET_NUM_ORDERS_COMMAND2;

public class GetNewOrdersCommandTest extends AbstractPriceCommandTest {

    private final GetNewOrdersCommand getNewOrdersCommand = new GetNewOrdersCommand(sendBotMessageService, wbClientPrices);

    private final HttpResponse<Orders> httpResponse = Mockito.mock(HttpResponse.class);
    private final Orders orders = Mockito.mock(Orders.class);
    private final List<Order> ordersList = Mockito.mock(List.class);

    @BeforeEach
    public void init() {
        Mockito.when(httpResponse.getBody()).thenReturn(orders);
        Mockito.when(orders.getOrders()).thenReturn(ordersList);
        Mockito.when(ordersList.size()).thenReturn(15);
        Mockito.when(wbClientPrices.getNewOrders()).thenReturn(httpResponse);
    }

    @Test
    public void shouldCorrectlyExecuteCommand1(){
        //given
        Mockito.when(httpResponse.getStatus()).thenReturn(HttpStatus.OK);
        String command = "/getneworders";
        String answer = String.format(GET_NUM_ORDERS_COMMAND1, ordersList.size());

        //while
        getNewOrdersCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, answer);
    }

    @Test
    public void shouldCorrectlyExecuteCommand2(){
        //given
        Mockito.when(httpResponse.getStatus()).thenReturn(HttpStatus.BAD_REQUEST);
        String command = "/getneworders";

        //while
        getNewOrdersCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, GET_NUM_ORDERS_COMMAND2);
    }
}
