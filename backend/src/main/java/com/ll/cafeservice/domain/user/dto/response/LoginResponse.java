package com.ll.cafeservice.domain.user.dto.response;

import org.springframework.lang.NonNull;

public record LoginResponse (
    @NonNull
    String username
){ }
