package com.daelim.dorandoranbackend.dto.request;

import com.daelim.dorandoranbackend.entity.User;
import lombok.Data;

@Data
public class UserRequest {
    String userId;
    String userName;
    String password;
    String telNum;
    Integer apartIdx;

    public User convert() {
        return new User().builder()
                .userId(this.userId)
                .userName(this.userName)
                .password(this.password)
                .telNum(this.password)
                .build();
    }
}
