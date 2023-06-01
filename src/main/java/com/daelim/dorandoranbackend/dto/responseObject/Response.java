package com.daelim.dorandoranbackend.dto.responseObject;

import lombok.Data;

@Data
public class Response<E> {
    Error error = null;
    E data = null;
}
