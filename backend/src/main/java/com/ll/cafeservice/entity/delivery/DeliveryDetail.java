package com.ll.cafeservice.entity.delivery;

import com.ll.cafeservice.entity.base.BaseEntity;
import jakarta.persistence.*;

@Entity
public class DeliveryDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    String email;
}
