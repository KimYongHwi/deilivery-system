package com.delivery.kyh.adapter.out.persistence.delivery;


import com.delivery.kyh.application.port.in.FindDeliveryPort;
import com.delivery.kyh.application.port.out.CreateDeliveryPort;
import com.delivery.kyh.common.OrderItemState;
import com.delivery.kyh.domain.Delivery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.delivery.kyh.adapter.out.persistence.delivery.QDeliveryJpaEntity.deliveryJpaEntity;
import static com.delivery.kyh.adapter.out.persistence.driver.QDriverJpaEntity.driverJpaEntity;
import static com.delivery.kyh.adapter.out.persistence.order.QOrderItemJpaEntity.orderItemJpaEntity;

@Repository
@RequiredArgsConstructor
public class DeliveryJpaRepositoryAdapter implements CreateDeliveryPort, FindDeliveryPort {

    private final DeliveryMapper mapper;

    private final DeliveryJpaRepository repository;

    private final JPAQueryFactory queryFactory;

    @Override
    public Delivery createDelivery(Delivery delivery) {
        DeliveryJpaEntity entity = mapper.toJpaEntity(delivery);
        repository.save(entity);

        return mapper.toDomainEntity(entity);
    }

    @Override
    public List<Delivery> findDeliveryByMemberIdAndCreatedAtGoe(Long memberId, LocalDateTime createdAt) {
        List<DeliveryJpaEntity> deliveryJpaEntities =
            queryFactory.selectFrom(deliveryJpaEntity)
                .innerJoin(orderItemJpaEntity).on(deliveryJpaEntity.orderItemId.eq(orderItemJpaEntity.id)).fetchJoin()
                .leftJoin(deliveryJpaEntity.driver, driverJpaEntity).fetchJoin()
                .where(
                    orderItemJpaEntity.memberId.eq(memberId),
                    orderItemJpaEntity.state.eq(OrderItemState.IN_DELIVERY),
                    deliveryJpaEntity.createdAt.goe(createdAt)
                )
                .fetch();

        return deliveryJpaEntities.stream().map(mapper::toDomainEntity).collect(Collectors.toList());
    }
}
