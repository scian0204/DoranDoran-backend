package com.daelim.dorandoranbackend.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    String userId;
    String password;
}
