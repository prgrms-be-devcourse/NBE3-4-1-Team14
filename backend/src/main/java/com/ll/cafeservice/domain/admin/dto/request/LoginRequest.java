package com.ll.cafeservice.domain.admin.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest (
    @NotBlank String username,
    @NotBlank String password
){ }
