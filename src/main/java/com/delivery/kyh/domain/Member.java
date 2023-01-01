package com.delivery.kyh.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.util.regex.Pattern;

import static com.delivery.kyh.common.ErrorMessage.INVALID_PASSWORD;

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
        String pwPattern1 = "^(?=.*[A-Z])|(?=.*[0-9])[A-Z[0-9]]{12,}$";
        String pwPattern2 = "^(?=.*[A-Z])|(?=.*[a-z])[A-Za-z]{12,}$";
        String pwPattern3 = "^(?=.*[A-Z])|(?=.*[$@$!%*#?&])[A-Z$@$!%*#?&]{12,}$";
        String pwPattern4 = "^(?=.*[a-z])|(?=.*[0-9])[a-z[0-9]]{12,}$";
        String pwPattern5 = "^(?=.*[a-z])|(?=.*[$@$!%*#?&])[a-z$@$!%*#?&]{12,}$";

        if (this.password.length() < 12) {
            throw new IllegalArgumentException(INVALID_PASSWORD.getMessage());
        }

        if (Pattern.matches(pwPattern1, this.password)
            || Pattern.matches(pwPattern2, this.password)
            || Pattern.matches(pwPattern3, this.password)
            || Pattern.matches(pwPattern4, this.password)
            || Pattern.matches(pwPattern5, this.password)
        ) {
            throw new IllegalArgumentException(INVALID_PASSWORD.getMessage());
        }
    }
}
