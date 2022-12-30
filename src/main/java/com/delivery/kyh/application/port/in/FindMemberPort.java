package com.delivery.kyh.application.port.in;

import com.delivery.kyh.domain.Member;

import java.util.Optional;

public interface FindMemberPort {
    int countByLoginId(String loginId);

    Optional<Member> findByLoginId(String loginId);
}
