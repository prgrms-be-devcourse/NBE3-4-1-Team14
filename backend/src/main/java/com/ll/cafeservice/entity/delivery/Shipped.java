package com.ll.cafeservice.entity.delivery;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.UUID;

@Builder
@Entity
public class Shipped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false)
    private UUID uuid; // 주문자 uuid

}
