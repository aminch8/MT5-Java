package com.mt5.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum Action {
    HISTORY("HISTORY"),CONFIG("CONFIG");

    private String value;


}
