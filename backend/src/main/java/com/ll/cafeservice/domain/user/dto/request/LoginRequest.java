package com.ll.cafeservice.domain.user.dto.request;

public record LoginRequest (
    String username,
    String password
){ }
