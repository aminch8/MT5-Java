package com.mt5.core.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mt5.core.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private boolean error;
    private List<Order> orders;


}
