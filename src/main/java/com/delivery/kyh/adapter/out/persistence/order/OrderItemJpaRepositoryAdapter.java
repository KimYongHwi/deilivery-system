package com.delivery.kyh.adapter.out.persistence.order;

import com.delivery.kyh.application.port.in.FindOrderItemPort;
import com.delivery.kyh.application.port.out.CommandOrderItemPort;
import com.delivery.kyh.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.delivery.kyh.common.ErrorMessage.NOT_FOUND_ORDER_ITEM;

@Repository
@RequiredArgsConstructor
public class OrderItemJpaRepositoryAdapter implements CommandOrderItemPort, FindOrderItemPort {

    private final OrderItemMapper mapper;

    private final OrderItemJpaRepository repository;

    @Override
    public OrderItem checkout(OrderItem orderItem) {
        OrderItemJpaEntity entity = mapper.toJpaEntity(orderItem);
        repository.save(entity);

        return mapper.toDomainEntity(entity);
    }

    @Override
    public OrderItem updateState(OrderItem orderItem) {
        OrderItemJpaEntity entity = mapper.toJpaEntity(orderItem);
        repository.save(entity);

        return orderItem;
    }

    @Override
    public OrderItem updateAddress(OrderItem orderItem) {
        OrderItemJpaEntity entity = mapper.toJpaEntity(orderItem);
        repository.save(entity);

        return orderItem;
    }

    @Override
    public OrderItem findByIdAndMemberId(Long id, Long memberId) {
        OrderItemJpaEntity entity = repository.findByIdAndMemberId(id, memberId);

        if (entity == null) {
            throw new IllegalArgumentException(NOT_FOUND_ORDER_ITEM.getMessage());
        }

        return mapper.toDomainEntity(entity);
    }
}
