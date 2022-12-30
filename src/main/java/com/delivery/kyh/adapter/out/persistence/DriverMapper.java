package com.delivery.kyh.adapter.out.persistence;

import com.delivery.kyh.domain.Driver;
import org.springframework.stereotype.Component;

@Component
public class DriverMapper {
    public DriverJpaEntity toJpaEntity(Driver driver) {
        return new DriverJpaEntity(
            driver.getId() == null ? null : driver.getId(),
            driver.getLoginId(),
            driver.getPassword(),
            driver.getName(),
            driver.getDriverLicenseNumber()
        );
    }

    public Driver toDomainEntity(DriverJpaEntity entity) {
        return Driver.create(
            entity.getId(),
            entity.getLoginId(),
            entity.getPassword(),
            entity.getName(),
            entity.getDriverLicenseNumber()
        );
    }
}
