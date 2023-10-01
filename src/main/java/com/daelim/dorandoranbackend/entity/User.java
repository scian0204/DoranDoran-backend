package com.daelim.dorandoranbackend.entity;

import com.daelim.dorandoranbackend.dto.request.UserRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Data
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class User {
    @Id
    private String userId;
    private String userName;
    private String password;
    private String telNum;
    private Integer isAdmin;
    private Timestamp regDate;
}
