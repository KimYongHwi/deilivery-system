package com.delivery.kyh.adapter.out.persistence;

import com.delivery.kyh.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public MemberJpaEntity toJpaEntity(Member member) {
        return new MemberJpaEntity(
            member.getId() == null ? null : member.getId(),
            member.getLoginId(),
            member.getPassword(),
            member.getName()
        );
    }

    public Member toDomainEntity(MemberJpaEntity entity) {
        return Member.create(
            entity.getId(),
            entity.getLoginId(),
            entity.getPassword(),
            entity.getName()
        );
    }
}
