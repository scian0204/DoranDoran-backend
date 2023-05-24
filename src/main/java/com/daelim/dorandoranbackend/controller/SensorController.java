package com.daelim.dorandoranbackend.controller;

import com.daelim.dorandoranbackend.controller.requestObject.SensorReportRequest;
import com.daelim.dorandoranbackend.controller.responseObject.Response;
import com.daelim.dorandoranbackend.entity.Sensor;
import com.daelim.dorandoranbackend.service.SensorService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "Sensor", description = "센서 컨트롤러")
@RestController
@RequestMapping("/api/sensor")
public class SensorController {
    @Autowired
    SensorService sensorService;

    @PostMapping("/regist")
    public Response<Sensor> regSensor(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = Sensor.class)
                    )
            )
            @RequestBody Map<String, Object> sensorObj) {
        return sensorService.regSensor(sensorObj);
    }

    @PostMapping("/report")
    public Response<Object> reportNoise(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = SensorReportRequest.class)
                    )
            )
            @RequestBody Map<String, Object> req) {
        return sensorService.reportNoise(req);
    }
}
