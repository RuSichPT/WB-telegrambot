package com.github.RuSichPT.WBtelegrambot.wbclient;

import com.github.RuSichPT.WBtelegrambot.wbclient.dto.PriceInfo;

import java.util.List;

public interface WbClientPrices {
    List<PriceInfo> getPriceInfo(Integer quantity);
}
