package com.delivery.kyh.adapter.out.persistence.driver;

import com.delivery.kyh.application.port.in.FindDriverPort;
import com.delivery.kyh.domain.Driver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.delivery.kyh.common.ErrorMessage.NOT_FOUND_DRIVER;

@Repository
@RequiredArgsConstructor
public class DriverJpaRepositoryAdapter implements FindDriverPort {

    private final DriverMapper mapper;

    private final DriverJpaRepository repository;

    @Override
    public Driver findById(Long id) {
        DriverJpaEntity entity = repository.findById(id)
            .orElseThrow(() -> {
                throw new IllegalArgumentException(NOT_FOUND_DRIVER.getMessage());
            });

        return mapper.toDomainEntity(entity);
    }
}
