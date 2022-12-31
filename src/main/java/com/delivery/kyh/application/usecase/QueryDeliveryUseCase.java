package com.delivery.kyh.application.usecase;

import com.delivery.kyh.domain.Delivery;

import java.time.LocalDateTime;
import java.util.List;

public interface QueryDeliveryUseCase {
    List<Delivery> findDeliveryByMemberIdAndCreatedAtGoe(Long memberId, LocalDateTime createdAt);
}
