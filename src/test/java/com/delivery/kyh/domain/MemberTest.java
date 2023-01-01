package com.delivery.kyh.domain;

import com.delivery.kyh.domain.vo.Authority;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.delivery.kyh.common.ErrorMessage.INVALID_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Member Domain 유닛 테스트")
public class MemberTest {
    @Test
    void validatePassword_메소드는_비밀번호가_12자리_미만일_경우_IllegalArgumentException_에러가_발생한다() {
        Member member = Member.create(null, "test", "test", "test", Authority.USER.name());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> member.validatePassword());

        String message = exception.getMessage();

        assertEquals(message, INVALID_PASSWORD.getMessage());
    }

    @Test
    void validatePassword_메소드는_영어소문자만_사용할_경우_IllegalArgumentException_에러가_발생한다() {
        Member member = Member.create(null, "test", "testtesttest", "test", Authority.USER.name());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> member.validatePassword());

        String message = exception.getMessage();

        assertEquals(message, INVALID_PASSWORD.getMessage());
    }

    @Test
    void validatePassword_메소드는_영어소문자와_숫자만_사용할_경우_IllegalArgumentException_에러가_발생한다() {
        Member member = Member.create(null, "test", "testtest1234", "test", Authority.USER.name());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> member.validatePassword());

        String message = exception.getMessage();

        assertEquals(message, INVALID_PASSWORD.getMessage());
    }

    @Test
    void validatePassword_메소드는_영어소문자와_특수문자만_사용할_경우_IllegalArgumentException_에러가_발생한다() {
        Member member = Member.create(null, "test", "testtest!@#$", "test", Authority.USER.name());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> member.validatePassword());

        String message = exception.getMessage();

        assertEquals(message, INVALID_PASSWORD.getMessage());
    }

    @Test()
    void validatePassword_메소드는_영대문자_영소문자_숫자_특수문자_중_3가지를_포함한_12자리의_경우_에러가_발생하지_않는다() {
        Member member = Member.create(null, "test", "Testtest1234", "test", Authority.USER.name());

        member.validatePassword();
    }
}
