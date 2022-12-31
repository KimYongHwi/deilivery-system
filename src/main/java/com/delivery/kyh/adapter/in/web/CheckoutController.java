package com.delivery.kyh.adapter.in.web;

import com.delivery.kyh.adapter.in.web.request.CheckoutRequest;
import com.delivery.kyh.application.usecase.CheckoutUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {
    private final CheckoutUseCase checkoutUseCase;

    @PostMapping()
    public Map<String, Boolean> checkout(@RequestBody CheckoutRequest request, Principal principal) {
        Long memberId = Long.valueOf(principal.getName().split(":")[0]);
        request.changeMemberId(memberId);

        boolean isCheckout = checkoutUseCase.checkout(request);

        return Map.of("isCheckout", isCheckout);
    }
}
