package com.mt5.core.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelOrderResponse {
    private boolean error;
    private long retcode;
    @JsonProperty("desription")
    private String description;
    @JsonProperty("order")
    private long orderId;
    private Number volume;
    private Number price;
    private Number bid;
    private Number ask;
    private String function;
}
