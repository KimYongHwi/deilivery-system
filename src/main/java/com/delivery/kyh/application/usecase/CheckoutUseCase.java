package com.delivery.kyh.application.usecase;

import com.delivery.kyh.adapter.in.web.request.CheckoutRequest;
import com.delivery.kyh.adapter.in.web.request.UpdateAddressRequest;

public interface CheckoutUseCase {
    boolean checkout(CheckoutRequest request);

    boolean updateAddress(Long orderItemId, Long memberId, UpdateAddressRequest req);
}
