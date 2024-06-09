package com.thofactorauth.tfa;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationRequest {

    private String username;
    private String code;
}
