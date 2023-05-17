package com.daelim.dorandoranbackend.controller.responseObject;

import lombok.Data;

@Data
public class Error {
    Integer errorId;
    String message;
}
