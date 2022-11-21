package com.mt5.core.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveSymbols {
    @JsonProperty("data")
    private List<LiveSymbol> symbols;
}
