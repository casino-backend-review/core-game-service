package com.core.gameservice.dto;

import com.core.gameservice.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductMemberGameStatus {

    private String productId;
    private Status memberStatus;

}
