package com.delivery.kyh.adapter.out.persistence;

import com.delivery.kyh.application.port.out.MemberSignUpPort;
import com.delivery.kyh.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepositoryAdapter implements MemberSignUpPort {

    private final MemberMapper mapper;

    private final MemberJpaRepository repository;

    @Override
    public Member signUp(Member member) {
        MemberJpaEntity entity = mapper.toJpaEntity(member);
        repository.save(entity);

        return mapper.toDomainEntity(entity);
    }
}
