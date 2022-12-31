package com.delivery.kyh.application.service;

import com.delivery.kyh.adapter.in.web.request.CheckoutRequest;
import com.delivery.kyh.application.port.in.FindDriverPort;
import com.delivery.kyh.application.port.in.FindMemberPort;
import com.delivery.kyh.application.port.out.CheckoutPort;
import com.delivery.kyh.application.port.out.CreateDeliveryPort;
import com.delivery.kyh.common.OrderItemState;
import com.delivery.kyh.domain.Driver;
import com.delivery.kyh.domain.Member;
import com.delivery.kyh.domain.OrderItem;
import com.delivery.kyh.domain.vo.Address;
import com.delivery.kyh.domain.vo.Authority;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("CheckoutService 유닛 테스트")
@ExtendWith(MockitoExtension.class)
public class CheckoutServiceTest {

    @Mock
    private CheckoutPort checkoutPort;

    @Mock
    private CreateDeliveryPort createDeliveryPort;

    @Mock
    private FindDriverPort findDriverPort;

    @InjectMocks
    private CheckoutService checkoutService;

//    @Test
//    void checkout_메소드는_OrderItem과_Delivery_정보를_저장한다() {
//        CheckoutRequest request = new CheckoutRequest(1L, 100, "test", "test");
//        OrderItem mockOrderItem = OrderItem.create(
//            null,
//            request.getMemberId(),
//            request.getPaidAmount(),
//            new Address(request.getPostcode(), request.getAddress()),
//            OrderItemState.IN_DELIVERY
//        );
//
//        Driver mockDriver = Driver.create(1L, "test", "test");
//
//        when(checkoutPort.checkout(mockOrderItem))
//            .thenReturn(mockOrderItem);
//
//        when(findDriverPort.findById(1L))
//            .thenReturn(mockDriver);
//
//        OrderItem actual = checkoutService.checkout(request);
//    }
}
