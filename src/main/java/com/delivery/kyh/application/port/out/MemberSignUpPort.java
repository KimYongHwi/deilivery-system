package com.delivery.kyh.application.port.out;

import com.delivery.kyh.domain.Member;

public interface MemberSignUpPort {
    Member signUp(Member member);
}
