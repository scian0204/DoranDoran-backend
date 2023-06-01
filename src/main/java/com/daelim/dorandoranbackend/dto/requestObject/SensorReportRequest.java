package com.daelim.dorandoranbackend.dto.requestObject;

import lombok.Data;

@Data
public class SensorReportRequest {
    String sensorId;
    Double noiseLevel;
}
