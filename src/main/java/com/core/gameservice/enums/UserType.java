package com.core.gameservice.enums;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public enum UserType {
    @NotBlank
    admin,
    @NotBlank
    company,
    @NotBlank

    share_holder,
    @NotBlank

    super_senior,
    @NotBlank

    senior,
    @NotBlank

    master,
    @NotBlank

    agent,
    @NotBlank

    member
}