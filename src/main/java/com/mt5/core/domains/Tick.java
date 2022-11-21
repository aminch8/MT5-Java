package com.mt5.core.domains;

import lombok.*;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class Tick {
    private ZonedDateTime time;
    private Number bidPrice;
    private Number askPrice;
}
