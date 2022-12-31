package com.delivery.kyh.adapter.out.persistence.delivery;


import com.delivery.kyh.application.port.out.CreateDeliveryPort;
import com.delivery.kyh.domain.Delivery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryJpaRepositoryAdapter implements CreateDeliveryPort {

    private final DeliveryMapper mapper;

    private final DeliveryJpaRepository repository;

    @Override
    public Delivery createDelivery(Delivery delivery) {
        DeliveryJpaEntity entity = mapper.toJpaEntity(delivery);
        repository.save(entity);

        return mapper.toDomainEntity(entity);
    }
}
