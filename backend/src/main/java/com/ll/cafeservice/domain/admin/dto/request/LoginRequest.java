package com.ll.cafeservice.domain.admin.dto.request;

public record LoginRequest (
    String username,
    String password
){ }
