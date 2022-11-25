package com.mt5.core.domains;

import lombok.*;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class Tick {
    private ZonedDateTime time;
    private Number bidPrice;
    private Number askPrice;
    private String symbol;

    public Tick(ZonedDateTime time, Number bidPrice, Number askPrice) {
        this.time = time;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }
}
