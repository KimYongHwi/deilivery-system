package com.delivery.kyh.adapter.out.persistence;

import com.delivery.kyh.adapter.out.persistence.config.BasicDataSourceConfig;
import com.delivery.kyh.adapter.out.persistence.config.JPAQueryFactoryConfig;
import com.delivery.kyh.adapter.out.persistence.driver.DriverJpaEntity;
import com.delivery.kyh.adapter.out.persistence.driver.DriverJpaRepository;
import com.delivery.kyh.adapter.out.persistence.driver.DriverJpaRepositoryAdapter;
import com.delivery.kyh.adapter.out.persistence.driver.DriverMapper;
import com.delivery.kyh.domain.Driver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static com.delivery.kyh.common.ErrorMessage.NOT_FOUND_DRIVER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {BasicDataSourceConfig.class, JPAQueryFactoryConfig.class, DriverMapper.class, DriverJpaRepository.class, DriverJpaRepositoryAdapter.class})
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@DisplayName("DeliveryJpaRepositoryAdapter 유닛 테스트")
public class DriverJpaRepositoryAdapterTest {

    @Autowired
    private DriverJpaRepository repository;

    @Autowired
    private DriverJpaRepositoryAdapter repositoryAdapter;

    @Test
    void findById_메소드는_id에_대한_driver가_없을_경우_IllegalArgumentException_에러가_발생한다() {
        Long given = Long.MAX_VALUE;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> repositoryAdapter.findById(given));
        String message = exception.getMessage();

        assertEquals(message, NOT_FOUND_DRIVER.getMessage());
    }

    @Test
    void findById_메소드는_id에_대한_Driver를_리턴한다() {
        DriverJpaEntity given = new DriverJpaEntity(null, "test", "test");
        repository.save(given);

        Driver driver = repositoryAdapter.findById(given.getId());

        assertEquals(driver.getId(), given.getId());
        assertEquals(driver.getName(), given.getName());
        assertEquals(driver.getPhone(), given.getPhone());
    }
}
