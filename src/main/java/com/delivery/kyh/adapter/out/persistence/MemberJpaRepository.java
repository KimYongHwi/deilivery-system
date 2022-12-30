package com.delivery.kyh.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {
    int countByLoginId(String loginId);

    Optional<MemberJpaEntity> findByLoginId(String loginId);
}
