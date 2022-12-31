package com.delivery.kyh.adapter.out.persistence;

import com.delivery.kyh.adapter.out.persistence.config.BasicDataSourceConfig;
import com.delivery.kyh.adapter.out.persistence.config.JPAQueryFactoryConfig;
import com.delivery.kyh.adapter.out.persistence.delivery.DeliveryJpaEntity;
import com.delivery.kyh.adapter.out.persistence.delivery.DeliveryJpaRepository;
import com.delivery.kyh.adapter.out.persistence.delivery.DeliveryJpaRepositoryAdapter;
import com.delivery.kyh.adapter.out.persistence.delivery.DeliveryMapper;
import com.delivery.kyh.adapter.out.persistence.driver.DriverJpaEntity;
import com.delivery.kyh.adapter.out.persistence.driver.DriverJpaRepository;
import com.delivery.kyh.adapter.out.persistence.order.OrderItemJpaEntity;
import com.delivery.kyh.adapter.out.persistence.order.OrderItemJpaRepository;
import com.delivery.kyh.common.OrderItemState;
import com.delivery.kyh.domain.Delivery;
import com.delivery.kyh.domain.Driver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {BasicDataSourceConfig.class, JPAQueryFactoryConfig.class, DeliveryMapper.class, DeliveryJpaRepository.class, DeliveryJpaRepositoryAdapter.class, OrderItemJpaRepository.class})
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@DisplayName("DeliveryJpaRepositoryAdapter 유닛 테스트")
public class DeliveryJpaRepositoryAdapterTest {

    @Autowired
    private DeliveryJpaRepository repository;

    @Autowired
    private DeliveryJpaRepositoryAdapter repositoryAdapter;

    @Autowired
    private DriverJpaRepository driverJpaRepository;

    @Autowired
    private OrderItemJpaRepository orderItemJpaRepository;

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

    @Test
    void findDeliveryByMemberIdAndCreatedAtGoe_메소드는_회원번호와_일치하고_생성날짜가_같거나_큰_Delivery가_있을_경우_해당_배열을_리턴한다() {
        Long memberId = 1L;
        DriverJpaEntity driverJpaEntity = driverJpaRepository.save(new DriverJpaEntity(null, "test", "test"));
        List<OrderItemJpaEntity> orderItemEntities = orderItemJpaRepository.saveAll(List.of(
                new OrderItemJpaEntity(null, memberId, 100, "test", "test", OrderItemState.IN_DELIVERY),
                new OrderItemJpaEntity(null, memberId, 100, "test", "test", OrderItemState.IN_DELIVERY),
                new OrderItemJpaEntity(null, memberId, 100, "test", "test", OrderItemState.IN_DELIVERY)
            )
        );

        List<DeliveryJpaEntity> deliveryJpaEntities = orderItemEntities.stream().map(e -> new DeliveryJpaEntity(null, e.getId(), driverJpaEntity)).collect(Collectors.toList());

        repository.saveAll(deliveryJpaEntities);

        List<Delivery> actual = repositoryAdapter.findDeliveryByMemberIdAndCreatedAtGoe(memberId, LocalDateTime.now().minusDays(3L));

        AtomicInteger idx = new AtomicInteger();
        assertThat(actual.size()).isEqualTo(deliveryJpaEntities.size());
        actual.forEach(d -> {
            assertThat(d.getId()).isEqualTo(deliveryJpaEntities.get(idx.get()).getId());
            assertThat(d.getOrderItemId()).isEqualTo(deliveryJpaEntities.get(idx.get()).getOrderItemId());
            assertThat(d.getDriver().getId()).isEqualTo(deliveryJpaEntities.get(idx.get()).getDriver().getId());
            assertThat(d.getDriver().getName()).isEqualTo(deliveryJpaEntities.get(idx.get()).getDriver().getName());
            assertThat(d.getDriver().getPhone()).isEqualTo(deliveryJpaEntities.get(idx.get()).getDriver().getPhone());
            idx.getAndIncrement();
        });
    }
}
