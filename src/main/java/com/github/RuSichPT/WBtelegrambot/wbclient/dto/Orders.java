package com.github.RuSichPT.WBtelegrambot.wbclient.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class Orders {
    private Integer next;
    private List<Order> orders;
}