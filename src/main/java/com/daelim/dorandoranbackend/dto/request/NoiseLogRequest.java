package com.daelim.dorandoranbackend.dto.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class NoiseLogRequest {
    private String sensorId;
    private Double avgNoise;
    private Double minNoise;
    private Double maxNoise;
}
