package com.delivery.kyh.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.lang.Nullable;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Value
public class Member {
    @Nullable
    Long id;

    String loginId;

    String password;

    String name;

    String authority;

    public static Member create(
        @Nullable Long id,
        String loginId,
        String password,
        String name,
        String authority
    ) {
        return new Member(id, loginId, password, name, authority);
    }

    public void validatePassword() {

    }
}
