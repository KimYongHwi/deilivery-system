package com.delivery.kyh.adapter.out.persistence.delivery;

import com.delivery.kyh.adapter.out.persistence.driver.DriverJpaEntity;
import com.delivery.kyh.domain.Delivery;
import com.delivery.kyh.domain.Driver;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMapper {
    public DeliveryJpaEntity toJpaEntity(Delivery delivery) {
        return new DeliveryJpaEntity(
            null,
            delivery.getOrderItemId(),
            new DriverJpaEntity(delivery.getDriver().getId(), delivery.getDriver().getName(), delivery.getDriver().getPhone())
        );
    }

    public Delivery toDomainEntity(DeliveryJpaEntity entity) {
        DriverJpaEntity driverJpaEntity = entity.getDriver();

        return Delivery.create(
            entity.getId(),
            entity.getOrderItemId(),
            Driver.create(driverJpaEntity.getId(), driverJpaEntity.getName(), driverJpaEntity.getPhone())
        );
    }
}
