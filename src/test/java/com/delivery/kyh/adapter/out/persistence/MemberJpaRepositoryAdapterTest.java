package com.delivery.kyh.adapter.out.persistence;

import com.delivery.kyh.adapter.out.persistence.config.BasicDataSourceConfig;
import com.delivery.kyh.adapter.out.persistence.config.JPAQueryFactoryConfig;
import com.delivery.kyh.domain.Member;
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
@ContextConfiguration(classes = {BasicDataSourceConfig.class, JPAQueryFactoryConfig.class, MemberMapper.class, MemberJpaRepository.class, MemberJpaRepositoryAdapter.class})
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@DisplayName("RefundTaxJpaRepositoryAdapterTest 유닛 테스트")
public class MemberJpaRepositoryAdapterTest {
    @Autowired
    private MemberJpaRepository repository;

    @Autowired
    private MemberJpaRepositoryAdapter repositoryAdapter;

    @Test
    void signUp_메소드는_Member를_저장한다() {
        Member given = Member.create(null, "test", "test", "test");

        Member savedMember = repositoryAdapter.signUp(given);
        MemberJpaEntity actual = repository.findById(savedMember.getId()).get();

        assertThat(actual.getId()).isNotNull();
        assertEquals(actual.getLoginId(), given.getLoginId());
        assertEquals(actual.getName(), given.getName());
    }
}
