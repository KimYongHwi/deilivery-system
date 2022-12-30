package com.delivery.kyh.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.lang.Nullable;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Value
public class Driver {
    @Nullable
    Long id;

    String loginId;

    String password;

    String name;

    String driverLicenseNumber;

    public static Driver create(
        @Nullable Long id,
        String loginId,
        String password,
        String name,
        String driverLicenseNumber
    ) {
        return new Driver(id, loginId, password, name, driverLicenseNumber);
    }
}
