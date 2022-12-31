package com.delivery.kyh.adapter.out.persistence.member;

import com.delivery.kyh.domain.Member;
import com.delivery.kyh.domain.vo.Authority;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MemberMapper {
    public MemberJpaEntity toJpaEntity(Member member) {
        return new MemberJpaEntity(
            member.getId() == null ? null : member.getId(),
            member.getLoginId(),
            member.getPassword(),
            member.getName(),
            Authority.USER
        );
    }

    public Optional<Member> toDomainEntity(Optional<MemberJpaEntity> memberJpaEntity) {
        return memberJpaEntity.map(m -> Member.create(
            m.getId(),
            m.getLoginId(),
            m.getPassword(),
            m.getName(),
            m.getAuthority().name()
        ));
    }
}
