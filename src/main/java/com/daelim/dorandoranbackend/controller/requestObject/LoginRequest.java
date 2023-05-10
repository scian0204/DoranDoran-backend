package com.daelim.dorandoranbackend.controller.requestObject;

import lombok.Data;

@Data
public class LoginRequest {
    String userId;
    String password;
}
