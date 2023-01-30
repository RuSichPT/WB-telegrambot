package com.github.RuSichPT.WBtelegrambot.wbclient.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class Orders {
    Integer next;
    List<Order> orders;
}