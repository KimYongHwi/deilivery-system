package com.delivery.kyh.adapter.in.web.request;

import lombok.Data;

@Data
public class CheckoutRequest {
    Long memberId;

    int paidAmount;

    String postcode;

    String address;
}
