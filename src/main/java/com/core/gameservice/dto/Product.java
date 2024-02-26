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
public class Product {
    private Double rate;
    private Double rateLimit;
    private String productId;
    private String productName;
    private Status status;

}
