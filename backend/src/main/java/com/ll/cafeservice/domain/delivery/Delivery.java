package com.ll.cafeservice.domain.delivery;


import org.springframework.lang.NonNull;

import java.time.LocalDate;

public class Delivery {

    @NonNull
    private LocalDate deliveryDate;

    private String email;


}
