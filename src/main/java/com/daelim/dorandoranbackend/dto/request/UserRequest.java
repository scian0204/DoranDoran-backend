package com.daelim.dorandoranbackend.dto.request;

import lombok.Data;

@Data
public class UserRequest {
    String userId;
    String userName;
    String password;
    String telNum;
}
