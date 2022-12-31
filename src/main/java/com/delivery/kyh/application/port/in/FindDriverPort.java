package com.delivery.kyh.application.port.in;

import com.delivery.kyh.domain.Driver;

public interface FindDriverPort {
    Driver findById(Long id);
}
