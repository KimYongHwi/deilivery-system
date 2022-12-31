package com.delivery.kyh.application.usecase;

public interface CommandDeliveryUseCase {
    boolean createDelivery(Long orderItemId, Long memberId);
}
