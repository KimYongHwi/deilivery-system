package com.delivery.kyh.adapter.out.persistence;

import com.delivery.kyh.application.port.out.CheckoutPort;
import com.delivery.kyh.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderItemJpaRepositoryAdapter implements CheckoutPort {

    private final OrderItemMapper mapper;

    private final OrderItemJpaRepository repository;

    @Override
    public OrderItem checkout(OrderItem orderItem) {
        OrderItemJpaEntity entity = mapper.toJpaEntity(orderItem);
        repository.save(entity);

        return mapper.toDomainEntity(entity);
    }
}
