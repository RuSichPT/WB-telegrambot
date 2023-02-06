package com.github.RuSichPT.WBtelegrambot.service;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPricesImpl;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Order;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Orders;
import kong.unirest.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

public class FindNewOrderServiceTest {

    private final WbClientPricesImpl wbClientPrices = Mockito.mock(WbClientPricesImpl.class);
    private final SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
    private final TelegramUserServiceImpl telegramUserService = Mockito.mock(TelegramUserServiceImpl.class);
    private final FindNewOrderServiceImpl findNewOrderService = new FindNewOrderServiceImpl(sendBotMessageService, wbClientPrices, telegramUserService);

    private final HttpResponse<Orders> httpResponse = Mockito.mock(HttpResponse.class);
    private final Orders orders = Mockito.mock(Orders.class);
    private final List<Order> orderList = Mockito.mock(List.class);

    private final Long chatId = 12564L;
    private final int NUM_USERS = 3;

    @BeforeEach
    public void init() {
        Mockito.when(wbClientPrices.getNewOrders()).thenReturn(httpResponse);
        Mockito.when(httpResponse.getBody()).thenReturn(orders);
        Mockito.when(orders.getOrders()).thenReturn(orderList);
    }

    @Test
    public void shouldCorrectlyNotifyAllUsers() {
        //given
        String message = FindNewOrderServiceImpl.MESSAGE;
        Mockito.when(orderList.size()).thenReturn(1);
        Mockito.when(telegramUserService.findAll()).thenReturn(createListTelegramUser(0));

        //when
        findNewOrderService.findNewOrders();

        //then
        Mockito.verify(sendBotMessageService, times(NUM_USERS)).sendMessage(chatId, message);
    }

    @Test
    public void shouldCorrectlyResetNumNewOrders() {
        //given
        List<TelegramUser> list = createListTelegramUser(2);
        List<TelegramUser> newList = createListTelegramUser(0);
        Mockito.when(orderList.size()).thenReturn(0);
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
}
