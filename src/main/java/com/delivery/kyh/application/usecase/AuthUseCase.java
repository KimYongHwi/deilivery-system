package com.delivery.kyh.application.usecase;

import com.delivery.kyh.adapter.in.web.request.SignInRequest;
import com.delivery.kyh.adapter.in.web.request.SignUpRequest;
import com.delivery.kyh.application.port.out.response.TokenInfo;

public interface AuthUseCase {
    TokenInfo signIn(SignInRequest request);

    boolean signUp(SignUpRequest request);
}
