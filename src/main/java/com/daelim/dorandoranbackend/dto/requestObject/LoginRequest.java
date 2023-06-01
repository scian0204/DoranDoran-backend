package com.daelim.dorandoranbackend.dto.requestObject;

import lombok.Data;

@Data
public class LoginRequest {
    String userId;
    String password;
}
