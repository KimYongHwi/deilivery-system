package com.delivery.kyh.application.service;

import com.delivery.kyh.application.port.in.FindDeliveryPort;
import com.delivery.kyh.application.usecase.QueryDeliveryUseCase;
import com.delivery.kyh.domain.Delivery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService implements QueryDeliveryUseCase {

    private final FindDeliveryPort findDeliveryPort;

    @Override
    public List<Delivery> findDeliveryByMemberIdAndCreatedAtGoe(Long memberId, LocalDateTime createdAt) {
        return findDeliveryPort.findDeliveryByMemberIdAndCreatedAtGoe(memberId, createdAt);
    }
}
