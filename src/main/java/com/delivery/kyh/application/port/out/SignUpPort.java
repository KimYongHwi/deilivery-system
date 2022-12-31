package com.delivery.kyh.application.port.out;

import com.delivery.kyh.domain.Member;

import java.util.Optional;

public interface SignUpPort {
    Optional<Member> signUp(Member member);
}
