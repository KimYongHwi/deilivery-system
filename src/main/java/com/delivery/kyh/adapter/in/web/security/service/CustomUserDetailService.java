package com.delivery.kyh.adapter.in.web.security.service;

import com.delivery.kyh.application.port.in.FindMemberPort;
import com.delivery.kyh.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.delivery.kyh.common.ErrorMessage.NOT_FOUND_MEMBER;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final FindMemberPort findMemberPort;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = findMemberPort.findByLoginId(username)
            .orElseThrow(() -> {
                throw new IllegalArgumentException(NOT_FOUND_MEMBER.getMessage());
            });

        return createUserDetails(member);
    }

    private UserDetails createUserDetails(Member member) {
        return User.builder()
            .username(member.getId().toString())
            .password(member.getPassword())
            .roles(member.getAuthority())
            .build();
    }
}
