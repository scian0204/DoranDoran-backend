package com.daelim.dorandoranbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Entity
@DynamicInsert
@DynamicUpdate
@Data
public class NoiseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;
    private String sensorId;
    private Double avgNoise;
    private Timestamp reportDate;
    private Double minNoise;
    private Double maxNoise;
}
