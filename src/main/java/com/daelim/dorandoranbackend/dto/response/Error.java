package com.daelim.dorandoranbackend.dto.response;

import lombok.Data;

@Data
public class Error {
    Integer errorId;
    String message;
}
