package com.delivery.kyh.adapter.out.persistence.member;

import com.delivery.kyh.application.port.in.FindMemberPort;
import com.delivery.kyh.application.port.out.SignUpPort;
import com.delivery.kyh.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepositoryAdapter implements SignUpPort, FindMemberPort {

    private final MemberMapper mapper;

    private final MemberJpaRepository repository;

    @Override
    public Optional<Member> signUp(Member member) {
        MemberJpaEntity entity = mapper.toJpaEntity(member);
        repository.save(entity);

        return mapper.toDomainEntity(Optional.of(entity));
    }

    @Override
    public int countByLoginId(String loginId) {
        return repository.countByLoginId(loginId);
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        Optional<MemberJpaEntity> entity = repository.findByLoginId(loginId);

        return mapper.toDomainEntity(entity);
    }
}
