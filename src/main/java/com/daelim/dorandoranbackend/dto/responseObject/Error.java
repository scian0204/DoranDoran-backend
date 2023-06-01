package com.daelim.dorandoranbackend.dto.responseObject;

import lombok.Data;

@Data
public class Error {
    Integer errorId;
    String message;
}
