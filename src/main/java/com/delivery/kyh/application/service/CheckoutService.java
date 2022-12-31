package com.delivery.kyh.application.service;

import com.delivery.kyh.adapter.in.web.request.CheckoutRequest;
import com.delivery.kyh.adapter.in.web.request.UpdateAddressRequest;
import com.delivery.kyh.application.port.in.FindOrderItemPort;
import com.delivery.kyh.application.port.out.CommandOrderItemPort;
import com.delivery.kyh.application.usecase.CheckoutUseCase;
import com.delivery.kyh.common.OrderItemState;
import com.delivery.kyh.domain.OrderItem;
import com.delivery.kyh.domain.vo.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckoutService implements CheckoutUseCase {
    private final CommandOrderItemPort commandOrderItemPort;

    private final FindOrderItemPort findOrderItemPort;

    @Override
    public boolean checkout(CheckoutRequest request) {
        commandOrderItemPort.checkout(OrderItem.create(
            null,
            request.getMemberId(),
            request.getPaidAmount(),
            new Address(request.getPostcode(), request.getAddress()),
            OrderItemState.ORDER
        ));

        return true;
    }

    @Override
    public boolean updateAddress(Long orderItemId, Long memberId, UpdateAddressRequest req) {
        OrderItem item = findOrderItemPort.findByIdAndMemberId(orderItemId, memberId);
        item.changeAddress(new Address(req.getPostcode(), req.getAddress()));

        commandOrderItemPort.updateAddress(item);

        return false;
    }
}
