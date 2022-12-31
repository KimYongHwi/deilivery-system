package com.delivery.kyh.adapter.out.persistence.order;

import com.delivery.kyh.common.OrderItemState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "order_item")
@Getter
@NoArgsConstructor
public class OrderItemJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "paid_amount", nullable = false)
    private int paidAmount;

    @Column(name = "postcode", nullable = false)
    private String postcode;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "state", nullable = false)
    private OrderItemState state;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public OrderItemJpaEntity(
        @Nullable Long id,
        Long memberId,
        int paidAmount,
        String postcode,
        String address,
        OrderItemState state
    ) {
        this.id = id;
        this.memberId = memberId;
        this.paidAmount = paidAmount;
        this.postcode = postcode;
        this.address = address;
        this.state = state;
    }

    public void changeState(OrderItemState state) {
        this.state = state;
    }
}
