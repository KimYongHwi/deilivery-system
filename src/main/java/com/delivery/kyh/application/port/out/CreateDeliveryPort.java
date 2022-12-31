package com.delivery.kyh.application.port.out;

import com.delivery.kyh.domain.Delivery;

public interface CreateDeliveryPort {
    Delivery createDelivery(Delivery delivery);
}
