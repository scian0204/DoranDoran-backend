package com.daelim.dorandoranbackend.service;

import com.daelim.dorandoranbackend.controller.responseObject.Response;
import com.daelim.dorandoranbackend.entity.*;
import com.daelim.dorandoranbackend.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SensorService {
    ObjectMapper objMpr = new ObjectMapper();
    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    SensorReportRepository sensorReportRepository;
    @Autowired
    ApartRepository apartRepository;
    @Autowired
    WarningMessageRepository warningMessageRepository;
    @Autowired
    ApartUserRepository apartUserRepository;

    public Response<Object> reportNoise(Map<String, Object> req) {
        SensorReport sr = objMpr.convertValue(req, SensorReport.class);
        sensorReportRepository.save(sr);

        /**
         * @TODO websocket 연결 후 해당 클라이언트로 경고 메시지 전송 혹은 로그인 된 계정에서 경고 메시지 확인 기능 추가
         * */
        Sensor sensor = sensorRepository.getReferenceById(sr.getSensorId());
        Apart apart = apartRepository.getReferenceById(sensor.getApartIdx());
        String message = apart.getDong() + "동 " + apart.getHo() + "호 " + sensor.getLocation() + "에서 " + sr.getReportDate() + "에 " + sr.getNoiseLevel() + "db 정도의 소음이 일어남";

        List<ApartUser> aus = apartUserRepository.findAllByApartIdx(apart.getApartIdx());
        aus.forEach(au -> {
            WarningMessage wm = new WarningMessage();
            wm.setMessage(message);
            wm.setUserId(au.getUserId());
            warningMessageRepository.save(wm);
        });

        return new Response<>();
    }

    public Response<Sensor> regSensor(Map<String, Object> sensorObj) {
        Sensor sensor = objMpr.convertValue(sensorObj, Sensor.class);
        Response<Sensor> res = new Response<>();
        res.setData(sensorRepository.save(sensor));
        return res;
    }
}
