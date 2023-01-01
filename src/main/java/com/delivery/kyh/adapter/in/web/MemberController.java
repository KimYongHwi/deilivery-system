package com.delivery.kyh.adapter.in.web;

import com.delivery.kyh.adapter.in.web.request.SignInRequest;
import com.delivery.kyh.adapter.in.web.request.SignUpRequest;
import com.delivery.kyh.application.port.out.response.TokenInfo;
import com.delivery.kyh.application.usecase.AuthUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final AuthUseCase authUseCase;

    @Operation(summary = "회원가입")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @PostMapping("/sign-up")
    public Map<String, Boolean> signUp(@RequestBody SignUpRequest request) {
        boolean isSignUp = authUseCase.signUp(request);

        return Map.of("isSignUp", isSignUp);
    }

    @Operation(summary = "로그인")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = TokenInfo.class))),
        @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @PostMapping("/sign-in")
    public TokenInfo signIn(@RequestBody SignInRequest request) {
        return authUseCase.signIn(request);
    }
}
