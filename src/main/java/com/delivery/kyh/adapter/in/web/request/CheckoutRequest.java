package com.delivery.kyh.adapter.in.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
public class CheckoutRequest {
    @Nullable Long memberId;

    int paidAmount;

    String postcode;

    String address;

    public void changeMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
