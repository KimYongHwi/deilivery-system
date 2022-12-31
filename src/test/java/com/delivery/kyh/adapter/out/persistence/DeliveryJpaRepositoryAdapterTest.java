package com.delivery.kyh.adapter.out.persistence;

import com.delivery.kyh.adapter.out.persistence.config.BasicDataSourceConfig;
import com.delivery.kyh.adapter.out.persistence.config.JPAQueryFactoryConfig;
import com.delivery.kyh.adapter.out.persistence.delivery.DeliveryJpaEntity;
import com.delivery.kyh.adapter.out.persistence.delivery.DeliveryJpaRepository;
import com.delivery.kyh.adapter.out.persistence.delivery.DeliveryJpaRepositoryAdapter;
import com.delivery.kyh.adapter.out.persistence.delivery.DeliveryMapper;
import com.delivery.kyh.adapter.out.persistence.order.OrderItemJpaRepository;
import com.delivery.kyh.adapter.out.persistence.order.OrderItemJpaRepositoryAdapter;
import com.delivery.kyh.adapter.out.persistence.order.OrderItemMapper;
import com.delivery.kyh.common.OrderItemState;
import com.delivery.kyh.domain.Delivery;
import com.delivery.kyh.domain.Driver;
import com.delivery.kyh.domain.OrderItem;
import com.delivery.kyh.domain.vo.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {BasicDataSourceConfig.class, JPAQueryFactoryConfig.class, DeliveryMapper.class, DeliveryJpaRepository.class, DeliveryJpaRepositoryAdapter.class})
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@DisplayName("DeliveryJpaRepositoryAdapter 유닛 테스트")
public class DeliveryJpaRepositoryAdapterTest {

    @Autowired
    private DeliveryJpaRepository repository;

    @Autowired
    private DeliveryJpaRepositoryAdapter repositoryAdapter;

    @Test
    void createDelivery_메소드는_Delivery를_저장한다() {
        Delivery given = Delivery.create(
            null,
            1L,
            Driver.create(null, "test", "test")
        );

        Delivery savedDelivery = repositoryAdapter.createDelivery(given);
        DeliveryJpaEntity actual = repository.findById(savedDelivery.getId()).get();

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getOrderItemId()).isEqualTo(given.getOrderItemId());
        assertThat(actual.getDriver().getName()).isEqualTo(given.getDriver().getName());
        assertThat(actual.getDriver().getPhone()).isEqualTo(given.getDriver().getPhone());
    }
}
