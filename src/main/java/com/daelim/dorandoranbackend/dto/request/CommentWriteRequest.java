package com.daelim.dorandoranbackend.dto.request;

import lombok.Data;

@Data
public class CommentWriteRequest {
    private Integer postId;
    private Integer target;
    private String comment;
}
