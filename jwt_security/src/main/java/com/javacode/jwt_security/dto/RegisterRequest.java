package com.javacode.jwt_security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {

    private String username;

    private String password;

    private String role;

}
