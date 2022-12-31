package com.delivery.kyh.application.service;

import com.delivery.kyh.adapter.in.web.request.CheckoutRequest;
import com.delivery.kyh.application.port.in.FindDriverPort;
import com.delivery.kyh.application.port.out.CheckoutPort;
import com.delivery.kyh.application.usecase.CheckoutUseCase;
import com.delivery.kyh.application.port.out.CreateDeliveryPort;
import com.delivery.kyh.common.OrderItemState;
import com.delivery.kyh.domain.Delivery;
import com.delivery.kyh.domain.Driver;
import com.delivery.kyh.domain.OrderItem;
import com.delivery.kyh.domain.vo.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckoutService implements CheckoutUseCase {

    private final CheckoutPort checkoutPort;

    private final CreateDeliveryPort createDeliveryPort;

    private final FindDriverPort findDriverPort;

    @Override
    public boolean checkout(CheckoutRequest request) {
        OrderItem savedOrderItem = checkoutPort.checkout(OrderItem.create(
            null,
            request.getMemberId(),
            request.getPaidAmount(),
            new Address(request.getPostcode(), request.getAddress()),
            OrderItemState.IN_DELIVERY
        ));

        Driver driver = findDriverPort.findById(1L);
        Delivery delivery = Delivery.create(null, savedOrderItem.getId(), driver);

        createDeliveryPort.createDelivery(delivery);

        return true;
    }
}
