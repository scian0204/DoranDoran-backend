package com.daelim.dorandoranbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@DynamicInsert
@DynamicUpdate
@Entity
public class ApartInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer apartId;
    private String apartName;
    private String location;
}
