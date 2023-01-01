package com.delivery.kyh;

import com.delivery.kyh.adapter.in.web.request.CheckoutRequest;
import com.delivery.kyh.adapter.in.web.request.SignInRequest;
import com.delivery.kyh.adapter.in.web.request.SignUpRequest;
import com.delivery.kyh.adapter.out.persistence.delivery.DeliveryJpaEntity;
import com.delivery.kyh.adapter.out.persistence.delivery.DeliveryJpaRepository;
import com.delivery.kyh.adapter.out.persistence.driver.DriverJpaEntity;
import com.delivery.kyh.adapter.out.persistence.driver.DriverJpaRepository;
import com.delivery.kyh.adapter.out.persistence.member.MemberJpaEntity;
import com.delivery.kyh.adapter.out.persistence.member.MemberJpaRepository;
import com.delivery.kyh.adapter.out.persistence.order.OrderItemJpaEntity;
import com.delivery.kyh.adapter.out.persistence.order.OrderItemJpaRepository;
import com.delivery.kyh.common.OrderItemState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

import static com.delivery.kyh.common.ErrorMessage.CANT_NOT_CHANGE_IN_DELIVERY_STATE;
import static com.delivery.kyh.common.ErrorMessage.DUPLICATE_LOGIN_ID;
import static com.delivery.kyh.common.ErrorMessage.NOT_FOUND_MEMBER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration
@DirtiesContext
@SpringBootTest(classes = {DeliverySystemApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class DeliverySystemApplicationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeliveryJpaRepository deliveryJpaRepository;

    @Autowired
    private DriverJpaRepository driverJpaRepository;

    @Autowired
    private OrderItemJpaRepository orderItemJpaRepository;

    @Autowired
    private EntityManager em;

    private String loginId = "barogo";

    private String password = "barogo";

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    private DriverJpaEntity driverJpaEntity;

    @PostConstruct
    void init() throws Exception {
        signup(new SignUpRequest(loginId, password, "barogo"));
        driverJpaEntity = driverJpaRepository.save(new DriverJpaEntity(null, "test", "test"));
    }

    @Test
    void signup_라우트는_전달받은_loginId가_이미_저장돼있을_경우_400을_리턴한다() throws Exception {
        SignUpRequest req = new SignUpRequest("barogo", "test", "test");

        signup(req)
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(DUPLICATE_LOGIN_ID.getMessage()));
    }

    @Test
    void signup_라우트는_사용자_정보를_저장한다() throws Exception {
        SignUpRequest req = new SignUpRequest("test", "test", "test");

        signup(req)
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSignUp").value(true));

        MemberJpaEntity entity = em.createQuery(
            "SELECT m FROM MemberJpaEntity m WHERE m.loginId = :loginId",
            MemberJpaEntity.class
        ).setParameter("loginId", req.getLoginId()).getSingleResult();

        assertThat(entity.getId()).isNotNull();
        assertThat(entity.getLoginId()).isEqualTo(req.getLoginId());
        assertThat(entity.getName()).isEqualTo(req.getName());
    }

    @Test
    void signin_라우트는_사용자_정보가_잘못된_경우_400을_리턴한다() throws Exception {
        SignInRequest req = new SignInRequest("test", "test");

        mockMvc.perform(
                post("/member/sign-in")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(NOT_FOUND_MEMBER.getMessage()));
    }

    @Test
    void signin_라우트는_로그인_정보가_사용자_정보와_일치할_경우_200을_리턴한다() throws Exception {
        String userId = "test";
        String password = "test";
        SignUpRequest signupReq = new SignUpRequest(userId, password, "test");
        signup(signupReq);

        SignInRequest req = new SignInRequest(userId, password);

        mockMvc.perform(
                post("/member/sign-in")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.grantType").value("Bearer"))
            .andExpect(jsonPath("$.accessToken").isNotEmpty())
            .andExpect(jsonPath("$.refreshToken").isNotEmpty());
    }

    @Test
    void checkout_라우트는_accessToken이_없을_경우_401을_리턴한다() throws Exception {
        mockMvc.perform(
                post("/checkout")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(new CheckoutRequest(null, 100, "test", "test")))
            ).andDo(print())
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = "1:barogo")
    void checkout_라우트는_주문정보를_저장한다() throws Exception {
        CheckoutRequest req = new CheckoutRequest(null, 100, "test", "test");

        mockMvc.perform(
                post("/checkout")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isCheckout").value(true));

        MemberJpaEntity member = memberJpaRepository.findByLoginId(loginId).get();
        OrderItemJpaEntity orderItem = orderItemJpaRepository.findAll().stream().findFirst().get();

        assertThat(orderItem.getId()).isNotNull();
        assertThat(orderItem.getMemberId()).isEqualTo(member.getId());
        assertThat(orderItem.getPaidAmount()).isEqualTo(req.getPaidAmount());
        assertThat(orderItem.getAddress()).isEqualTo(req.getAddress());
        assertThat(orderItem.getPostcode()).isEqualTo(req.getPostcode());
        assertThat(orderItem.getState()).isEqualTo(OrderItemState.ORDER);
    }

    @Test
    @WithMockUser(value = "1:barogo")
    void delivery_생성_라우트는_Delivery를_저장하고_해당_주문건의_OrderItemState를_IN_DELIVERY로_변경한다() throws Exception {
        Long memberId = 1L;
        OrderItemJpaEntity orderItemJpaEntity = orderItemJpaRepository.save(
            new OrderItemJpaEntity(null, memberId, 100, "test", "test", OrderItemState.ORDER)
        );

        mockMvc.perform(
                post("/delivery/" + orderItemJpaEntity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.inDelivery").value(true));

        DeliveryJpaEntity entity = em.createQuery(
            "SELECT d FROM DeliveryJpaEntity d WHERE d.orderItemId = :orderItemId",
            DeliveryJpaEntity.class
        ).setParameter("orderItemId", orderItemJpaEntity.getId()).getSingleResult();

        assertThat(entity.getId()).isNotNull();
        assertThat(entity.getOrderItemId()).isEqualTo(orderItemJpaEntity.getId());
        assertThat(entity.getDriver()).isNotNull();
        assertThat(orderItemJpaEntity.getState()).isEqualTo(OrderItemState.IN_DELIVERY);
    }

    @Test
    @WithMockUser(value = "1:barogo")
    void delivery_생성_라우트는_주문건의_OrderItemState이_ORDER_상태가_아닐_경우_400을_리턴한다() throws Exception {
        Long memberId = 1L;
        OrderItemJpaEntity orderItemJpaEntity = orderItemJpaRepository.save(
            new OrderItemJpaEntity(null, memberId, 100, "test", "test", OrderItemState.IN_DELIVERY)
        );

        mockMvc.perform(
                post("/delivery/" + orderItemJpaEntity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(CANT_NOT_CHANGE_IN_DELIVERY_STATE.getMessage()));
    }

    @Test
    @WithMockUser(value = "1:barogo")
    void delivery_라우트는_3일치의_Delivery_목록을_조회한다() throws Exception {
        Long memberId = 1L;
        List<OrderItemJpaEntity> orderItemEntities = orderItemJpaRepository.saveAll(List.of(
                new OrderItemJpaEntity(null, memberId, 100, "test", "test", OrderItemState.IN_DELIVERY),
                new OrderItemJpaEntity(null, memberId, 100, "test", "test", OrderItemState.IN_DELIVERY),
                new OrderItemJpaEntity(null, memberId, 100, "test", "test", OrderItemState.IN_DELIVERY)
            )
        );

        List<DeliveryJpaEntity> deliveryJpaEntities = orderItemEntities.stream().map(e -> new DeliveryJpaEntity(null, e.getId(), driverJpaEntity)).collect(Collectors.toList());

        deliveryJpaRepository.saveAll(deliveryJpaEntities);

        mockMvc.perform(
                get("/delivery")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isOk());
    }

    private ResultActions signup(SignUpRequest req) throws Exception {
        return mockMvc.perform(
            post("/member/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        );
    }
}
