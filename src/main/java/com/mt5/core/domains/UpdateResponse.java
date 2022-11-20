package com.mt5.core.domains;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateResponse {

    private boolean error;
    private String lastError;
    private String description;
    private String function;

}
