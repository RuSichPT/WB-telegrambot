package com.github.RuSichPT.WBtelegrambot.job;

import com.github.RuSichPT.WBtelegrambot.service.FindNewOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Component
public class FindNewOrders {
    private final FindNewOrderService findNewOrderService;

    @Autowired
    public FindNewOrders(FindNewOrderService findNewOrderService) {
        this.findNewOrderService = findNewOrderService;
    }

    @Scheduled(fixedRateString = "${bot.findNewOrdersTimeMs}")
    public void findNewOrders() {
        LocalDateTime start = LocalDateTime.now();

        log.info("Find new orders job started.");

        findNewOrderService.findNewOrders();

        LocalDateTime end = LocalDateTime.now();

        log.info("Find new orders job finished. Took seconds: {}",
                end.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC));
    }
}
