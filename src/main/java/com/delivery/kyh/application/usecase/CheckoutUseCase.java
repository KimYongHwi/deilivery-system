package com.delivery.kyh.application.usecase;

import com.delivery.kyh.adapter.in.web.request.CheckoutRequest;

public interface CheckoutUseCase {
    boolean checkout(CheckoutRequest request);
}
