package com.github.RuSichPT.WBtelegrambot.wbclient.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

@Builder
@Getter
@EqualsAndHashCode
public class OrderRequestArgs {
    private Integer limit;
    private Long next;
    private Integer dateFrom;
    private Integer dateTo;

    public Map<String,Object> populateQueries() {
        Map<String,Object> queries = new HashMap<>();
        if (nonNull(limit)) {
            queries.put("limit", limit);
        }
        if (nonNull(next)) {
            queries.put("next", next);
        }
        if (nonNull(dateFrom)) {
            queries.put("dateFrom", dateFrom);
        }
        if (nonNull(dateTo)) {
            queries.put("dateTo", dateTo);
        }
        return queries;
    }
}
