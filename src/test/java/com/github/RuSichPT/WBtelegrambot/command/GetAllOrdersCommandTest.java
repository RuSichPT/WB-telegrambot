package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Order;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.OrderRequestArgs;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Orders;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static com.github.RuSichPT.WBtelegrambot.command.CommandName.GET_ALL_ORDERS;
import static com.github.RuSichPT.WBtelegrambot.command.GetAllOrdersCommand.MAX_LIMIT;
import static com.github.RuSichPT.WBtelegrambot.command.GetAllOrdersCommand.MESSAGE2;

public class GetAllOrdersCommandTest extends AbstractWbClientCommandTest {

    private final GetAllOrdersCommand getAllOrdersCommand = new GetAllOrdersCommand(sendBotService, telegramUserService, wbClientPrices);

    private final HttpResponse<Orders> httpResponseOrders2 = Mockito.mock(HttpResponse.class);
    private final Orders orders2 = Mockito.mock(Orders.class);
    private final List<Order> ordersList2 = Mockito.mock(List.class);

    private final Long next = 15236L;
    public final OrderRequestArgs args1 = buildOrderRequestArgs(0L);
    public final OrderRequestArgs args2 = buildOrderRequestArgs(next);

    @BeforeEach
    public void init() {
        Mockito.when(httpResponseOrders.getBody()).thenReturn(orders);
        Mockito.when(orders.getOrders()).thenReturn(ordersList);
        Mockito.when(orders.getNext()).thenReturn(next.intValue());
        Mockito.when(wbClientPrices.getOrders(Mockito.eq(args1), Mockito.any(String.class))).thenReturn(httpResponseOrders);

        Mockito.when(httpResponseOrders2.getBody()).thenReturn(orders2);
        Mockito.when(orders2.getOrders()).thenReturn(ordersList2);
        Mockito.when(orders2.getNext()).thenReturn(next.intValue());
        Mockito.when(wbClientPrices.getOrders(Mockito.eq(args2), Mockito.any(String.class))).thenReturn(httpResponseOrders2);

        initTelegramUserService();
    }

    @Test
    public void shouldCorrectlySendMessage1() {
        //given
        Mockito.when(httpResponseOrders.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(httpResponseOrders2.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(ordersList.size()).thenReturn(MAX_LIMIT);
        Mockito.when(ordersList2.size()).thenReturn(5);
        String command = GET_ALL_ORDERS.getCommandName();
        String answer = String.format(GetAllOrdersCommand.MESSAGE1, ordersList.size() + ordersList2.size());

        executeAndVerify(getAllOrdersCommand, command, answer);
    }

    @Test
    public void shouldCorrectlySendMessage2() {
        //given
        Mockito.when(httpResponseOrders.getStatus()).thenReturn(HttpStatus.BAD_REQUEST);
        String command = GET_ALL_ORDERS.getCommandName();

        executeAndVerify(getAllOrdersCommand, command, MESSAGE2);
    }

    private OrderRequestArgs buildOrderRequestArgs(Long next) {
        return OrderRequestArgs.builder()
                .limit(MAX_LIMIT)
                .next(next)
                .build();
    }
}
