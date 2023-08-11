package com.daelim.dorandoranbackend.controller;

import com.daelim.dorandoranbackend.dto.request.NoiseLogRequest;
import com.daelim.dorandoranbackend.dto.request.SensorReportRequest;
import com.daelim.dorandoranbackend.dto.response.Response;
import com.daelim.dorandoranbackend.entity.NoiseLog;
import com.daelim.dorandoranbackend.entity.Sensor;
import com.daelim.dorandoranbackend.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Sensor", description = "센서 컨트롤러")
@RestController
@RequestMapping("/api/sensor")
public class SensorController {
    @Autowired
    SensorService sensorService;

    @Operation(summary = "센서 등록 API")
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

    @Operation(summary = "기준치 이상 소음 발생 시 등록 API", description = "센서에서 감지된 소음 데이터 DB에 저장 및 해당 유저 경고 메시지 전송 처리")
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

    @Operation(summary = "지속적인 소음 로그 등록 API", description = "특정 시간동안 평균, 최소, 최대 데시벨 로그")
    @PostMapping("/log")
    public Response<NoiseLog> logNoise(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = NoiseLogRequest.class)
                    )
            )
            @RequestBody Map<String, Object> logObj) {
        return sensorService.logNoise(logObj);
    }

    @Operation(summary = "호별 가장 최근 소음정도", description = "Apart의 apartIdx값을 넣을 것")
    @GetMapping("/noiseLevel/{apartIdx}")
    public Response<Double> getNoiseLevelByHo(@PathVariable Integer apartIdx) {
        return sensorService.getNoiseLevelByHo(apartIdx);
    }
}
