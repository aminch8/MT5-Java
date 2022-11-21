package com.mt5.core.domains;

import lombok.*;

@Data
@AllArgsConstructor
public class UpdateConfigResponse {

    private boolean error;
    private String lastError;
    private String description;
    private String function;

}
