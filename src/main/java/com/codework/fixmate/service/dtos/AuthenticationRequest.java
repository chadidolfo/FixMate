package com.codework.fixmate.service.dtos;

import lombok.Data;

@Data
public class AuthenticationRequest {
    public String username;
    public String password;
}
