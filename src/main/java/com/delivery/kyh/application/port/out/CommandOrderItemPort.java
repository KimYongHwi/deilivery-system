package com.delivery.kyh.application.port.out;

import com.delivery.kyh.domain.OrderItem;

public interface CommandOrderItemPort {
    OrderItem checkout(OrderItem orderItem);

    OrderItem updateState(OrderItem item);

    OrderItem updateAddress(OrderItem orderItem);
}
