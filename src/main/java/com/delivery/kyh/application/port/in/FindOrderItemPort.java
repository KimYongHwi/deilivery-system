package com.delivery.kyh.application.port.in;

import com.delivery.kyh.domain.OrderItem;

public interface FindOrderItemPort {
    OrderItem findByIdAndMemberId(Long id, Long memberId);
}
