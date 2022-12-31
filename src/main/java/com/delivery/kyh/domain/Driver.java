package com.delivery.kyh.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.lang.Nullable;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Value
public class Driver {
    @Nullable Long id;
    String name;
    String phone;

    public static Driver create(@Nullable Long id, String name, String phone) {
        return new Driver(id, name, phone);
    }
}
