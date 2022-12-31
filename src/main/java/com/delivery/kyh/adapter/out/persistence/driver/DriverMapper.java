package com.delivery.kyh.adapter.out.persistence.driver;

import com.delivery.kyh.domain.Driver;
import org.springframework.stereotype.Component;

@Component
public class DriverMapper {
    public Driver toDomainEntity(DriverJpaEntity entity) {
        return Driver.create(entity.getId(), entity.getName(), entity.getPhone());
    }
}
