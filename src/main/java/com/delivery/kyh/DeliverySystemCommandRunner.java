package com.delivery.kyh;

import com.delivery.kyh.adapter.out.persistence.driver.DriverJpaEntity;
import com.delivery.kyh.adapter.out.persistence.driver.DriverJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliverySystemCommandRunner implements CommandLineRunner {

    private final DriverJpaRepository driverJpaRepository;

    @Override
    public void run(String... args) throws Exception {
        driverJpaRepository.save(new DriverJpaEntity(null, "test", "test"));
    }
}
