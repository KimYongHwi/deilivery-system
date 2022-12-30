package com.delivery.kyh.adapter.out.persistence;

import com.delivery.kyh.adapter.out.persistence.config.BasicDataSourceConfig;
import com.delivery.kyh.adapter.out.persistence.config.JPAQueryFactoryConfig;
import com.delivery.kyh.domain.Driver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {BasicDataSourceConfig.class, JPAQueryFactoryConfig.class, DriverMapper.class, DriverJpaRepository.class, DriverJpaRepositoryAdapter.class})
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@DisplayName("RefundTaxJpaRepositoryAdapterTest 유닛 테스트")
public class DriverJpaRepositoryAdapterTest {
    @Autowired
    private DriverJpaRepository repository;

    @Autowired
    private DriverJpaRepositoryAdapter repositoryAdapter;

    @Test
    void signUp_메소드는_Driver를_저장한다() {
        Driver given = Driver.create(null, "test", "test", "test", "test");

        Driver savedDriver = repositoryAdapter.signUp(given);
        DriverJpaEntity actual = repository.findById(savedDriver.getId()).get();

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getCreatedAt()).isNotNull();
        assertEquals(actual.getLoginId(), given.getLoginId());
        assertEquals(actual.getName(), given.getName());
        assertEquals(actual.getDriverLicenseNumber(), given.getDriverLicenseNumber());
    }
}
