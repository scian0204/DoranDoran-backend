package com.daelim.dorandoranbackend.dto.requestObject;

import lombok.Data;

@Data
public class UserRequest {
    String userId;
    String userName;
    String password;
    String telNum;
}
