package com.delivery.kyh.application.service;

import com.delivery.kyh.adapter.in.web.request.SignInRequest;
import com.delivery.kyh.adapter.in.web.request.SignUpRequest;
import com.delivery.kyh.adapter.in.web.security.jwt.JwtProvider;
import com.delivery.kyh.adapter.out.persistence.member.MemberMapper;
import com.delivery.kyh.application.port.in.FindMemberPort;
import com.delivery.kyh.application.port.out.SignUpPort;
import com.delivery.kyh.application.port.out.response.TokenInfo;
import com.delivery.kyh.application.usecase.AuthUseCase;
import com.delivery.kyh.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.delivery.kyh.common.ErrorMessage.DUPLICATE_LOGIN_ID;
import static com.delivery.kyh.common.ErrorMessage.INVALID_LOGIN_INFO;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService implements AuthUseCase {
    private final FindMemberPort findMemberPort;

    private final JwtProvider jwtProvider;

    private final MemberMapper memberMapper;

    private final PasswordEncoder passwordEncoder;

    private final SignUpPort signUpPort;

    private final UserDetailsService userDetailsService;

    @Override
    @Transactional
    public TokenInfo signIn(SignInRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLoginId());
        boolean isMatch = passwordEncoder.matches(request.getPassword(), userDetails.getPassword());

        if (!isMatch) {
            throw new BadCredentialsException(INVALID_LOGIN_INFO.getMessage());
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        return jwtProvider.generateToken(token);
    }

    @Override
    @Transactional
    public boolean signUp(SignUpRequest request) {
        Member member = memberMapper.toDomainEntity(request);
        member.validatePassword();

        int userIdCount = findMemberPort.countByLoginId(request.getLoginId());

        if (userIdCount > 0) {
            throw new IllegalArgumentException(DUPLICATE_LOGIN_ID.getMessage());
        }

        signUpPort.signUp(member);

        return true;
    }
}
