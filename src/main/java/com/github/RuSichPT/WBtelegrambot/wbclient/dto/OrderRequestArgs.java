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
    Integer limit;
    Long next;
    Integer dateFrom;
    Integer dateTo;

    public Map populateQueries() {
        Map queries = new HashMap<>();
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
