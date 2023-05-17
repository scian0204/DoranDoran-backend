package com.daelim.dorandoranbackend.controller.responseObject;

import lombok.Data;

@Data
public class Response<E> {
    Error error = null;
    E data = null;
}
