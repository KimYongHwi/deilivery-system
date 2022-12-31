package com.delivery.kyh.adapter.in.web.request;

import com.delivery.kyh.domain.vo.Authority;
import lombok.Value;

@Value
public class SignUpRequest {
    String loginId;
    String password;
    String name;

    String authority = Authority.USER.name();
}
