package com.daelim.dorandoranbackend.dto.request;

import lombok.Data;

@Data
public class SensorReportRequest {
    String sensorId;
    Double noiseLevel;
}
