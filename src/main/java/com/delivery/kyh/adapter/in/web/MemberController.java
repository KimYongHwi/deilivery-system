package com.delivery.kyh.adapter.in.web;

import com.delivery.kyh.adapter.in.web.request.CheckoutRequest;
import com.delivery.kyh.adapter.in.web.request.SignInRequest;
import com.delivery.kyh.adapter.in.web.request.SignUpRequest;
import com.delivery.kyh.application.port.out.response.TokenInfo;
import com.delivery.kyh.application.usecase.AuthUseCase;
import com.delivery.kyh.application.usecase.CheckoutUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final AuthUseCase authUseCase;

    @PostMapping("/sign-up")
    public Map<String, Boolean> signUp(@RequestBody SignUpRequest request) {
        boolean isSignUp = authUseCase.signUp(request);

        return Map.of("isSignUp", isSignUp);
    }

    @PostMapping("/sign-in")
    public TokenInfo signIn(@RequestBody SignInRequest request) {
        return authUseCase.signIn(request);
    }
}
