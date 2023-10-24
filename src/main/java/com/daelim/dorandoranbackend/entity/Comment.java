package com.daelim.dorandoranbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    private Integer postId;
    private String userId;
    private Integer target;
    private String comment;
    private Timestamp writeDate;
}
