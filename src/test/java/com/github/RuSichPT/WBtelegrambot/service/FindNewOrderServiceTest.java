package com.github.RuSichPT.WBtelegrambot.service;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPricesImpl;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Order;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Orders;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.github.RuSichPT.WBtelegrambot.service.FindNewOrderServiceImpl.MESSAGE;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

public class FindNewOrderServiceTest {

    private final WbClientPricesImpl wbClientPrices = Mockito.mock(WbClientPricesImpl.class);
    private final SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
    private final TelegramUserServiceImpl telegramUserService = Mockito.mock(TelegramUserServiceImpl.class);
    private final FindNewOrderServiceImpl findNewOrderService = new FindNewOrderServiceImpl(sendBotMessageService, wbClientPrices, telegramUserService);

    private final HttpResponse<Orders> httpResponse = Mockito.mock(HttpResponse.class);
    private final Orders orders = Mockito.mock(Orders.class);
    private final List<Order> orderList = createListOrder();

    private final int NUM_USERS = 3;

    @BeforeEach
    public void init() {
        Mockito.when(wbClientPrices.getNewOrders(Mockito.any(String.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getBody()).thenReturn(orders);
        Mockito.when(httpResponse.getStatus()).thenReturn(HttpStatus.OK);
        Mockito.when(orders.getOrders()).thenReturn(orderList);
    }

    @Test
    public void shouldCorrectlyNotifyAllUsers() {
        //given
        Mockito.when(telegramUserService.findAll()).thenReturn(createListTelegramUser(0));
        Order order = orderList.get(0);
        String message = String.format(MESSAGE, order.getNmId(), order.getArticle(), order.getPrice());

        //when
        findNewOrderService.findNewOrders();

        //then
        Long chatId = 12564L;
        Mockito.verify(sendBotMessageService, times(NUM_USERS)).sendMessage(chatId, message);
    }

    @Test
    public void shouldCorrectlyResetNumNewOrders() {
        //given
        List<TelegramUser> list = createListTelegramUser(2);
        List<TelegramUser> newList = createListTelegramUser(0);
        orderList.clear();
        Mockito.when(telegramUserService.findAll()).thenReturn(list);

        //when
        findNewOrderService.findNewOrders();

        //then
        Mockito.verify(sendBotMessageService, never()).sendMessage(Mockito.any(Long.class), Mockito.any(String.class));
        Mockito.verify(telegramUserService, times(NUM_USERS)).saveUser(newList.get(0));
    }

    private List<TelegramUser> createListTelegramUser(int numNewOrders) {

        List<TelegramUser> list = new ArrayList<>();
        for (int i = 0; i < NUM_USERS; i++) {
            TelegramUser user = new TelegramUser();
            user.setChatId(12564L);
            user.setNumNewOrders(numNewOrders);
            list.add(user);
        }

        return list;
    }

    private List<Order> createListOrder() {
        ArrayList<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setNmId(123456);
        order.setArticle("article");
        order.setPrice(3568);
        orders.add(order);
        return orders;
    }
}
