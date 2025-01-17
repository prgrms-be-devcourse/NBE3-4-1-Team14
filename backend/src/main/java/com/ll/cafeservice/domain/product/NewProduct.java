package com.ll.cafeservice.domain.product;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NewProduct {
    private final String name;
    private final Integer price;
    private final String description;
    private final Integer quantity;
    private final String ImgUrl;


}
