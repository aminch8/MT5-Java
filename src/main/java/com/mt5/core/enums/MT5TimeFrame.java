package com.mt5.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;


@AllArgsConstructor
@Getter
public enum MT5TimeFrame {
TICK("TICK",Duration.ZERO),W1("W1",Duration.ofDays(7)),D1("D1",Duration.ofDays(1)),H4("H4",Duration.ofHours(4)),H1("H1",Duration.ofHours(1)),M30("M30",Duration.ofMinutes(30)),M15("M15",Duration.ofMinutes(15)),M5("M5",Duration.ofMinutes(5)),M1("M1",Duration.ofMinutes(1));
private String value;
private Duration duration;
}
