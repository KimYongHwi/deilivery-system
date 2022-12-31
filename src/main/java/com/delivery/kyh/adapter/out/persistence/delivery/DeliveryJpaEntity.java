package com.delivery.kyh.adapter.out.persistence.delivery;

import com.delivery.kyh.adapter.out.persistence.driver.DriverJpaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "delivery")
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "order_item_id", nullable = false)
    private Long orderItemId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private DriverJpaEntity driver;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public DeliveryJpaEntity(@Nullable Long id, Long orderItemId, DriverJpaEntity driver) {
        this.id = id;
        this.orderItemId = orderItemId;
        this.driver = driver;
    }
}
