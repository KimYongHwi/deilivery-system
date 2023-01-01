package com.delivery.kyh.adapter.out.persistence;

import com.delivery.kyh.DeliverySystemConfiguration;
import com.delivery.kyh.adapter.out.persistence.config.BasicDataSourceConfig;
import com.delivery.kyh.adapter.out.persistence.config.JPAQueryFactoryConfig;
import com.delivery.kyh.adapter.out.persistence.member.MemberJpaEntity;
import com.delivery.kyh.adapter.out.persistence.member.MemberJpaRepository;
import com.delivery.kyh.adapter.out.persistence.member.MemberJpaRepositoryAdapter;
import com.delivery.kyh.adapter.out.persistence.member.MemberMapper;
import com.delivery.kyh.domain.Member;
import com.delivery.kyh.domain.vo.Authority;
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
@ContextConfiguration(classes = {DeliverySystemConfiguration.class, BasicDataSourceConfig.class, JPAQueryFactoryConfig.class, MemberMapper.class, MemberJpaRepository.class, MemberJpaRepositoryAdapter.class})
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@DisplayName("MemberJpaRepositoryAdapter 유닛 테스트")
public class MemberJpaRepositoryAdapterTest {
    @Autowired
    private MemberJpaRepository repository;

    @Autowired
    private MemberJpaRepositoryAdapter repositoryAdapter;

    @Test
    void countByUserId_메소드는_userId에_해당하는_데이터가_없을_경우_0을_리턴한다() {
        String given = "test";

        int actual = repositoryAdapter.countByLoginId(given);

        assertThat(actual).isZero();
    }

    @Test
    void countByUserId_메소드는_userId에_해당하는_데이터가_있을_경우_1을_리턴한다() {
        String given = "test";

        Member member = Member.create(
            null, given, "test", "test", Authority.USER.name()
        );

        repositoryAdapter.signUp(member);

        int actual = repositoryAdapter.countByLoginId(given);

        assertThat(actual).isEqualTo(1);
    }

    @Test
    void signUp_메소드는_Member를_저장한다() {
        Member given = Member.create(null, "test", "test", "test", Authority.USER.name());

        Member savedMember = repositoryAdapter.signUp(given).get();
        MemberJpaEntity actual = repository.findById(savedMember.getId()).get();

        assertThat(actual.getId()).isNotNull();
        assertEquals(actual.getLoginId(), given.getLoginId());
        assertEquals(actual.getName(), given.getName());
    }
}
