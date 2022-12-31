package com.delivery.kyh.adapter.out.persistence;

import com.delivery.kyh.domain.OrderItem;
import com.delivery.kyh.domain.vo.Address;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public OrderItemJpaEntity toJpaEntity(OrderItem orderItem) {
        return new OrderItemJpaEntity(
            orderItem.getId() == null ? null : orderItem.getId(),
            orderItem.getMemberId(),
            orderItem.getPaidAmount(),
            orderItem.getAddress().getPostcode(),
            orderItem.getAddress().getAddress(),
            orderItem.getState()
        );
    }

    public OrderItem toDomainEntity(OrderItemJpaEntity entity) {
        return OrderItem.create(
            entity.getId(),
            entity.getMemberId(),
            entity.getPaidAmount(),
            new Address(entity.getPostcode(), entity.getAddress()),
            entity.getState()
        );
    }
}
