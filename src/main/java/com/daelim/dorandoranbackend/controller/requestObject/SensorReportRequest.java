package com.daelim.dorandoranbackend.controller.requestObject;

import lombok.Data;

@Data
public class SensorReportRequest {
    String sensorId;
    Double noiseLevel;
}
