package com.delivery.kyh.application.port.out;

import com.delivery.kyh.domain.OrderItem;

public interface CheckoutPort {
    OrderItem checkout(OrderItem orderItem);
}
