package com.github.RuSichPT.WBtelegrambot.service;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPricesImpl;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Order;
import com.github.RuSichPT.WBtelegrambot.wbclient.dto.Orders;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.github.RuSichPT.WBtelegrambot.service.FindNewOrderServiceImpl.MESSAGE;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

public class FindNewOrderServiceTest {

    private final WbClientPricesImpl wbClientPrices = Mockito.mock(WbClientPricesImpl.class);
    private final SendBotService sendBotService = Mockito.mock(SendBotService.class);
    private final TelegramUserServiceImpl telegramUserService = Mockito.mock(TelegramUserServiceImpl.class);
    private final FindNewOrderServiceImpl findNewOrderService = new FindNewOrderServiceImpl(sendBotService, wbClientPrices, telegramUserService);

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
    public void shouldCorrectlyNotifyAllUsers() throws IOException {
        //given
        Mockito.when(telegramUserService.findAll()).thenReturn(createListTelegramUser(0));
        Order order = orderList.get(0);
        String message = MESSAGE + order.toStringShort() + "\n";

        //when
        findNewOrderService.findNewOrders();

        //then
        Long chatId = 12564L;
        Mockito.verify(sendBotService, times(NUM_USERS)).sendPhoto(Mockito.eq(chatId),
                Mockito.any(URL.class), Mockito.eq(message));
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
        Mockito.verify(sendBotService, never()).sendMessage(Mockito.any(Long.class), Mockito.any(String.class));
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
        order.setNmId(12345678);
        order.setArticle("article");
        order.setPrice(356800);
        order.setCurrencyCode(643);

        orders.add(order);
        return orders;
    }
}
