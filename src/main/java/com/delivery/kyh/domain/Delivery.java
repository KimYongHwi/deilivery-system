package com.delivery.kyh.domain;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.lang.Nullable;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Value
public class Delivery {
    @Nullable
    Long id;

    Long orderItemId;

    Driver driver;

    public static Delivery create(@Nullable Long id, Long orderItemId, Driver driver) {
        return new Delivery(id, orderItemId, driver);
    }
}
