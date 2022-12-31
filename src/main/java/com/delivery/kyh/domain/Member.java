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

    /*
        비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열로 생성해야 합니다.
     */
    public void validatePassword() {

    }
}
