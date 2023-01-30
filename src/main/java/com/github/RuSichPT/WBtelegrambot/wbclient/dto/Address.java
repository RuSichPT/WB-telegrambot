package com.github.RuSichPT.WBtelegrambot.wbclient.dto;

import lombok.Data;

@Data
public class Address {
    String province;
    String area;
    String city;
    String street;
    String home;
    String flat;
    String entrance;
    Double longitude;
    Double latitude;
}
