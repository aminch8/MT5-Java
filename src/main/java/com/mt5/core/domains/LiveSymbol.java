package com.mt5.core.domains;

import com.mt5.core.enums.TimeFrame;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveSymbol {
    private String symbolName;
    private TimeFrame timeFrame;
}
