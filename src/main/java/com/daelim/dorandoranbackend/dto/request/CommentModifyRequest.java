package com.daelim.dorandoranbackend.dto.request;

import lombok.Data;

@Data
public class CommentModifyRequest {
    private Integer commentId;
    private Integer postId;
    private Integer target;
    private String comment;
}
