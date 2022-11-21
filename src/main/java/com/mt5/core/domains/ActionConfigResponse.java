package com.mt5.core.domains;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionConfigResponse {

    private boolean error;
    private String lastError;
    private String description;
    private String function;

}
