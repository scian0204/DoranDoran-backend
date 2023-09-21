package com.daelim.dorandoranbackend.dto.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    String userName;
    String password;
    String telNum;
    Integer apartIdx;
}
