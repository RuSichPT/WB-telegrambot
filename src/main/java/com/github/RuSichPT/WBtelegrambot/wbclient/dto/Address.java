package com.github.RuSichPT.WBtelegrambot.wbclient.dto;

import lombok.Data;

@Data
public class Address {
    private String province;
    private String area;
    private String city;
    private String street;
    private String home;
    private String flat;
    private String entrance;
    private Double longitude;
    private Double latitude;
}
