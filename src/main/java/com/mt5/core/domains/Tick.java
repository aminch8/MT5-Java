package com.mt5.core.domains;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class Tick {
    private ZonedDateTime time;
    private Number bidPrice;
    private Number askPrice;
}
