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
    private boolean checked;
    private double rate;
    private double rateLimit;
    private String productId;
    private String productName;
    private Status newGameStatus;
    private Status getNewRate;// Assuming you have a Status field

    public double getNewRate() {
        return this.getNewRate.ordinal();
    }

    // Lombok will generate a getter for newGameStatus, so you don't need to manually define it
    // unless you need to add some custom logic.
}
