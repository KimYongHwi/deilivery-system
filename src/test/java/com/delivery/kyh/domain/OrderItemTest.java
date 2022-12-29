package com.delivery.kyh.domain;


import com.delivery.kyh.common.OrderItemState;
import com.delivery.kyh.domain.vo.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.delivery.kyh.common.ErrorMessage.CAN_NOT_CANCEL_DELIVERY_COMPLETE;
import static com.delivery.kyh.common.ErrorMessage.CAN_NOT_CANCEL_IN_DELIVERY;
import static com.delivery.kyh.common.ErrorMessage.CAN_NOT_CHANGE_ADDRESS_DELIVERY_COMPLETE;
import static com.delivery.kyh.common.ErrorMessage.CAN_NOT_CHANGE_ADDRESS_IN_DELIVERY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("OrderItem Domain 유닛 테스트")
public class OrderItemTest {
    @Test
    void cancel_메소드는_주문_상태가_배달중_또는_배달완료_상태일_경우_IllegalStateException_에러가_발생한다() {
        OrderItem inDeliveryOrderItem = OrderItem.create(null, null, new Address("barogo", "barogo"), OrderItemState.IN_DELIVERY);
        OrderItem deliveryCompleteOrderItem = OrderItem.create(null, null, new Address("barogo", "barogo"), OrderItemState.DELIVERY_COMPLETE);

        Exception inDeliveryException = assertThrows(IllegalStateException.class, () -> inDeliveryOrderItem.cancel());
        Exception deliveryCompleteException = assertThrows(IllegalStateException.class, () -> deliveryCompleteOrderItem.cancel());

        String inDeliveryActualMessage = inDeliveryException.getMessage();
        String deliveryCompleteActualMessage = deliveryCompleteException.getMessage();

        assertEquals(inDeliveryActualMessage, CAN_NOT_CANCEL_IN_DELIVERY.getMessage());
        assertEquals(deliveryCompleteActualMessage, CAN_NOT_CANCEL_DELIVERY_COMPLETE.getMessage());
    }

    @Test
    void cancel_메소드는_주문_상태가_배달중_또는_배달완료_상태가_아닐_경우_취소_상태로_상태가_변경된다() {
        OrderItem orderItem = OrderItem.create(null, null, new Address("barogo", "barogo"), OrderItemState.ORDER);
        OrderItem waitDeliveryOrderItem = OrderItem.create(null, null, new Address("barogo", "barogo"), OrderItemState.WAIT_DELIVERY);

        orderItem.cancel();
        waitDeliveryOrderItem.cancel();

        assertEquals(orderItem.state, OrderItemState.CANCEL);
        assertEquals(waitDeliveryOrderItem.state, OrderItemState.CANCEL);
    }

    @Test
    void changeAddress_메소드는_주문_상태가_배달중_또는_배달완료_상태일_경우_IllegalStateException_에러가_발생한다() {
        OrderItem inDeliveryOrderItem = OrderItem.create(null, null, new Address("barogo", "barogo"), OrderItemState.IN_DELIVERY);
        OrderItem deliveryCompleteOrderItem = OrderItem.create(null, null, new Address("barogo", "barogo"), OrderItemState.DELIVERY_COMPLETE);

        Exception inDeliveryException = assertThrows(IllegalStateException.class, () -> inDeliveryOrderItem.changeAddress(new Address("barogo1", "barogo1")));
        Exception deliveryCompleteException = assertThrows(IllegalStateException.class, () -> deliveryCompleteOrderItem.changeAddress(new Address("barogo1", "barogo1")));

        String inDeliveryActualMessage = inDeliveryException.getMessage();
        String deliveryCompleteActualMessage = deliveryCompleteException.getMessage();

        assertEquals(inDeliveryActualMessage, CAN_NOT_CHANGE_ADDRESS_IN_DELIVERY.getMessage());
        assertEquals(deliveryCompleteActualMessage, CAN_NOT_CHANGE_ADDRESS_DELIVERY_COMPLETE.getMessage());
    }

    @Test
    void changeAddress_메소드는_주문_상태가_배달중_또는_배달완료_상태가_아닐_경우_주소_변경이_가능하다() {
        OrderItem orderItem = OrderItem.create(null, null, new Address("barogo", "barogo"), OrderItemState.ORDER);
        OrderItem waitDeliveryOrderItem = OrderItem.create(null, null, new Address("barogo", "barogo"), OrderItemState.WAIT_DELIVERY);
        Address newAddress = new Address("barogo1", "barogo1");

        orderItem.changeAddress(newAddress);
        waitDeliveryOrderItem.changeAddress(newAddress);

        assertEquals(orderItem.address.getPostcode(), newAddress.getPostcode());
        assertEquals(waitDeliveryOrderItem.address.getAddress(), newAddress.getAddress());
    }
}
