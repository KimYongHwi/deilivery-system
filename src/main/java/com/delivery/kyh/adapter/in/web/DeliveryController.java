package com.delivery.kyh.adapter.in.web;

import com.delivery.kyh.application.usecase.CommandDeliveryUseCase;
import com.delivery.kyh.application.usecase.QueryDeliveryUseCase;
import com.delivery.kyh.domain.Delivery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final CommandDeliveryUseCase commandDeliveryUseCase;

    private final QueryDeliveryUseCase queryDeliveryUseCase;

    @Operation(summary = "배달 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @PostMapping("/{orderItemId}")
    public Map<String, Boolean> createDelivery(@PathVariable Long orderItemId, Principal principal) {
        Long memberId = Long.valueOf(principal.getName().split(":")[0]);
        boolean inDelivery = commandDeliveryUseCase.createDelivery(orderItemId, memberId);

        return Map.of("inDelivery", inDelivery);
    }

    @Operation(summary = "배달 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @GetMapping
    public List<Delivery> getDeliveries(Principal principal) {
        LocalDateTime createdAt = LocalDateTime.now().minusDays(3);
        Long memberId = Long.valueOf(principal.getName().split(":")[0]);

        return queryDeliveryUseCase.findDeliveryByMemberIdAndCreatedAtGoe(memberId, createdAt);
    }
}
