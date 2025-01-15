package com.ll.cafeservice.domain.delivery.implement;

import com.ll.cafeservice.domain.delivery.Delivery;
import com.ll.cafeservice.entity.delivery.DeliveryDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeliveryReader {

    private final DeliveryDetailRepository deliveryDetailRepository;

    public List<Delivery> findByEmail(String email) {
        return deliveryDetailRepository.findByEmail(email).stream()
                .map(deliveryDetail -> {
                    return new Delivery();
                }).toList();
    }
}
