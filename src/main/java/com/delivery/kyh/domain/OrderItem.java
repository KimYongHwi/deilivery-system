package com.delivery.kyh.domain;

import com.delivery.kyh.common.OrderItemState;
import com.delivery.kyh.domain.vo.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Value;
import org.springframework.lang.Nullable;

import static com.delivery.kyh.common.ErrorMessage.CAN_NOT_CANCEL_DELIVERY_COMPLETE;
import static com.delivery.kyh.common.ErrorMessage.CAN_NOT_CANCEL_IN_DELIVERY;
import static com.delivery.kyh.common.ErrorMessage.CAN_NOT_CHANGE_ADDRESS_DELIVERY_COMPLETE;
import static com.delivery.kyh.common.ErrorMessage.CAN_NOT_CHANGE_ADDRESS_IN_DELIVERY;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OrderItem {
    @Nullable
    Long id;

    Long memberId;

    int paidAmount;

    Address address;

    OrderItemState state;

    public static OrderItem create(
        @Nullable Long id,
        Long memberId,
        int paidAmount,
        Address address,
        OrderItemState state
    ) {
        return new OrderItem(id, memberId, paidAmount, address, state);
    }

    public void cancel() {
        if (isInDelivery()) {
            throw new IllegalStateException(CAN_NOT_CANCEL_IN_DELIVERY.getMessage());
        }

        if (isDeliveryComplete()) {
            throw new IllegalStateException(CAN_NOT_CANCEL_DELIVERY_COMPLETE.getMessage());
        }

        this.state = OrderItemState.CANCEL;
    }

    public void changeAddress(Address address) {
        if (isInDelivery()) {
            throw new IllegalStateException(CAN_NOT_CHANGE_ADDRESS_IN_DELIVERY.getMessage());
        }

        if (isDeliveryComplete()) {
            throw new IllegalStateException(CAN_NOT_CHANGE_ADDRESS_DELIVERY_COMPLETE.getMessage());
        }

        this.address = address;
    }

    private boolean isInDelivery() {
        return state.getState().equals(OrderItemState.IN_DELIVERY.getState());
    }

    private boolean isDeliveryComplete() {
        return state.getState().equals(OrderItemState.DELIVERY_COMPLETE.getState());
    }
}
