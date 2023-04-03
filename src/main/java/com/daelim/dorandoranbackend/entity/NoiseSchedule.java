package com.daelim.dorandoranbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Data
@DynamicInsert
@DynamicUpdate
//@ToString
@Entity
public class NoiseSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;
    private String userId;
    private Timestamp reportDate;
    private Timestamp startDate;
    private Timestamp endDate;
    private String reason;
}
