package com.ll.cafeservice.domain.delivery.service;

import com.ll.cafeservice.domain.delivery.dto.response.DeliveryResponse;
import com.ll.cafeservice.domain.delivery.implement.DeliveryReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryReader deliveryReader;

    public List<DeliveryResponse> getList(String email) {
        return deliveryReader.findByEmail(email).stream()
                .map(delivery ->{
                    return new DeliveryResponse("1", LocalDate.now(), List.of());
                }).toList();
    }
}
