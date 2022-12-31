package com.delivery.kyh.application.service;

import com.delivery.kyh.application.port.in.FindDriverPort;
import com.delivery.kyh.application.port.in.FindOrderItemPort;
import com.delivery.kyh.application.port.out.CommandOrderItemPort;
import com.delivery.kyh.application.port.out.CreateDeliveryPort;
import com.delivery.kyh.application.usecase.CommandDeliveryUseCase;
import com.delivery.kyh.common.OrderItemState;
import com.delivery.kyh.domain.Delivery;
import com.delivery.kyh.domain.Driver;
import com.delivery.kyh.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommandDeliveryService implements CommandDeliveryUseCase {
    private final CreateDeliveryPort createDeliveryPort;

    private final CommandOrderItemPort commandOrderItemPort;

    private final FindDriverPort findDriverPort;

    private final FindOrderItemPort findOrderItemPort;

    @Override
    @Transactional
    public boolean createDelivery(Long orderItemId, Long memberId) {
        OrderItem item = findOrderItemPort.findByIdAndMemberId(orderItemId, memberId);
        item.changeInDeliveryState();

        Driver driver = findDriverPort.findById(1L);
        Delivery delivery = Delivery.create(null, orderItemId, driver);

        createDeliveryPort.createDelivery(delivery);
        commandOrderItemPort.updateState(item);

        return true;
    }
}
