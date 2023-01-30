package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Order;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.OrderRequestArgs;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Orders;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static com.github.RuSichPT.WBtelegrambot.command.GetNumOrdersCommand.GET_NUM_ORDERS_COMMAND2;
import static com.github.RuSichPT.WBtelegrambot.command.GetNumOrdersCommand.MAX_LIMIT;

@Slf4j
public class GetNumOrdersCommandTest extends AbstractPriceCommandTest {

    private final GetNumOrdersCommand getNumOrdersCommand = new GetNumOrdersCommand(sendBotMessageService, wbClientPrices);

    private final HttpResponse<Orders> httpResponse = Mockito.mock(HttpResponse.class);
    private final HttpResponse<Orders> httpResponse2 = Mockito.mock(HttpResponse.class);
    private final Orders orders1 = Mockito.mock(Orders.class);
    private final Orders orders2 = Mockito.mock(Orders.class);
    private final List<Order> ordersList1 = Mockito.mock(List.class);
    private final List<Order> ordersList2 = Mockito.mock(List.class);

    private final Long next = 15236L;
    public final OrderRequestArgs args1 = OrderRequestArgs.builder()
            .limit(MAX_LIMIT)
            .next(0L)
            .build();
    public final OrderRequestArgs args2 = OrderRequestArgs.builder()
            .limit(MAX_LIMIT)
            .next(next)
            .build();

    @BeforeEach
    public void init() {
        Mockito.when(httpResponse.getBody()).thenReturn(orders1);
        Mockito.when(orders1.getOrders()).thenReturn(ordersList1);
        Mockito.when(orders1.getNext()).thenReturn(next.intValue());
        Mockito.when(wbClientPrices.getOrders(args1)).thenReturn(httpResponse);

        Mockito.when(httpResponse2.getBody()).thenReturn(orders2);
        Mockito.when(orders2.getOrders()).thenReturn(ordersList2);
        Mockito.when(orders2.getNext()).thenReturn(next.intValue());
        Mockito.when(wbClientPrices.getOrders(args2)).thenReturn(httpResponse2);
    }

    @Test
    public void shouldCorrectlyExecuteCommand1() {
        //given
        Mockito.when(httpResponse.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(httpResponse2.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(ordersList1.size()).thenReturn(MAX_LIMIT);
        Mockito.when(ordersList2.size()).thenReturn(5);
        String command = "/getnumorders";
        String answer = String.format(GetNumOrdersCommand.GET_NUM_ORDERS_COMMAND1, ordersList1.size() + ordersList2.size());

        //while
        getNumOrdersCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, answer);
    }

    @Test
    public void shouldCorrectlyExecuteCommand2() {
        //given
        Mockito.when(httpResponse.getStatus()).thenReturn(HttpStatus.BAD_REQUEST);
        String command = "/getnumorders";

        //while
        getNumOrdersCommand.execute(getUpdate(command));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, GET_NUM_ORDERS_COMMAND2);
    }
}
