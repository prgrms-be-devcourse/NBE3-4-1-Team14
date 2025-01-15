package com.ll.cafeservice.domain.admin.dto.response;

import org.springframework.lang.NonNull;

public record LoginResponse (
    @NonNull
    String username
){ }
