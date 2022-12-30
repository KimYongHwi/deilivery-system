package com.delivery.kyh.adapter.out.persistence;

import com.delivery.kyh.application.port.out.DriverSignUpPort;
import com.delivery.kyh.application.port.out.MemberSignUpPort;
import com.delivery.kyh.domain.Driver;
import com.delivery.kyh.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DriverJpaRepositoryAdapter implements DriverSignUpPort {
    private final DriverMapper mapper;

    private final DriverJpaRepository repository;

    @Override
    public Driver signUp(Driver driver) {
        DriverJpaEntity entity = repository.save(mapper.toJpaEntity(driver));

        return mapper.toDomainEntity(entity);
    }
}
