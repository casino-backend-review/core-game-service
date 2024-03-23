package com.core.gameservice.dto;

import com.core.gameservice.enums.Group;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberBetLimit {

    private String productId;
    private String productName;

    private String category;

    private String provider;
    private Group group;
    private BetLimit betLimit; //other than member and admin
    private Double commission;
    private Double commissionRate;
}
