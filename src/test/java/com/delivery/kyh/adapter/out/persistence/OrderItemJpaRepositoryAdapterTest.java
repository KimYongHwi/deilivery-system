package com.delivery.kyh.adapter.out.persistence;

import com.delivery.kyh.adapter.out.persistence.config.BasicDataSourceConfig;
import com.delivery.kyh.adapter.out.persistence.config.JPAQueryFactoryConfig;
import com.delivery.kyh.common.OrderItemState;
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
@ContextConfiguration(classes = {BasicDataSourceConfig.class, JPAQueryFactoryConfig.class, OrderItemMapper.class, OrderItemJpaRepository.class, OrderItemJpaRepositoryAdapter.class})
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@DisplayName("MemberJpaRepositoryAdapter 유닛 테스트")
public class OrderItemJpaRepositoryAdapterTest {

    @Autowired
    private OrderItemJpaRepository repository;

    @Autowired
    private OrderItemJpaRepositoryAdapter repositoryAdapter;

    @Test
    void checkout_메소드는_OrderItem을_저장한다() {
        OrderItem given = OrderItem.create(
            null,
            1L,
            100,
            new Address("test", "test"),
            OrderItemState.ORDER
        );

        OrderItem actual = repositoryAdapter.checkout(given);

        assertThat(actual.getId()).isNotNull();
    }
}
