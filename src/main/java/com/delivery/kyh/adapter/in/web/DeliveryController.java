package com.delivery.kyh.adapter.in.web;

import com.delivery.kyh.application.usecase.QueryDeliveryUseCase;
import com.delivery.kyh.domain.Delivery;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final QueryDeliveryUseCase queryDeliveryUseCase;

    @GetMapping
    public List<Delivery> getDeliveries(Principal principal) {
        LocalDateTime createdAt = LocalDateTime.now().minusDays(3);
        Long memberId = Long.valueOf(principal.getName().split(":")[0]);

        return queryDeliveryUseCase.findDeliveryByMemberIdAndCreatedAtGoe(memberId, createdAt);
    }
}
