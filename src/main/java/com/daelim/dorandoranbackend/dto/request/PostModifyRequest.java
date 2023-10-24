package com.daelim.dorandoranbackend.dto.request;

import lombok.Data;

@Data
public class PostModifyRequest {
    private Integer postId;
    private String title;
    private String content;
}
