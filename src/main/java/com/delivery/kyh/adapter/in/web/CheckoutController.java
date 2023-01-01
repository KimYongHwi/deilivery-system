package com.delivery.kyh.adapter.in.web;

import com.delivery.kyh.adapter.in.web.request.CheckoutRequest;
import com.delivery.kyh.adapter.in.web.request.UpdateAddressRequest;
import com.delivery.kyh.application.usecase.CheckoutUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Operation(summary = "주문")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @PostMapping
    public Map<String, Boolean> checkout(@RequestBody CheckoutRequest request, Principal principal) {
        Long memberId = Long.valueOf(principal.getName().split(":")[0]);
        request.changeMemberId(memberId);

        boolean isCheckout = checkoutUseCase.checkout(request);

        return Map.of("isCheckout", isCheckout);
    }

    @Operation(summary = "배송지 변경")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @PatchMapping("/{orderItemId}/address")
    public Map<String, Boolean> updateAddress(
        @PathVariable Long orderItemId,
        @RequestBody UpdateAddressRequest req,
        Principal principal
    ) {
        Long memberId = Long.valueOf(principal.getName().split(":")[0]);
        checkoutUseCase.updateAddress(orderItemId, memberId, req);

        return Map.of("isUpdated", true);
    }
}
