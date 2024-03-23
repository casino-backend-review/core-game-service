package com.core.gameservice.dto;


import com.core.gameservice.enums.Group;
import com.core.gameservice.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberBetLimitDetails {

    private String productId;
    private Status status;
    private String productName;
    private String category;
    private String provider;
    private Group group;
    private BetLimit betLimit; //only member
    private Double commission;
    private Double commissionRate;

}
