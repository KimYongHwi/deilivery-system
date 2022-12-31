package com.delivery.kyh.application.port.in;

import com.delivery.kyh.domain.Delivery;
import com.delivery.kyh.domain.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

public interface FindDeliveryPort {
    List<Delivery> findDeliveryByMemberIdAndCreatedAtGoe(Long memberId, LocalDateTime createdAt);
}
