package com.daelim.dorandoranbackend.service;

import com.daelim.dorandoranbackend.controller.responseObject.DongResponse;
import com.daelim.dorandoranbackend.entity.ApartInfo;
import com.daelim.dorandoranbackend.repository.ApartInfoRepository;
import com.daelim.dorandoranbackend.repository.ApartRepository;
import com.daelim.dorandoranbackend.repository.ApartUserRepository;
import com.daelim.dorandoranbackend.controller.responseObject.HoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ApartService {
    ObjectMapper objMpr = new ObjectMapper();
    @Autowired
    ApartRepository apartRepository;
    @Autowired
    ApartInfoRepository apartInfoRepository;
    @Autowired
    ApartUserRepository apartUserRepository;

    public List<ApartInfo> getApartList(String apartName) {
        return apartInfoRepository.findAllByApartName(apartName);
    }

    public List<String> getDongList(Integer apartId) {
        List<DongResponse> findResults = apartRepository.findDistinctByApartId(apartId);
        List<String> result = new ArrayList<>();
        findResults.forEach(findResult -> {
            result.add(findResult.getDong());
        });
        return result;
    }

    public List<String> getHoList(Integer apartId, String dong) {
        List<HoResponse> findResults = apartRepository.findDistinctByApartIdAndDong(apartId, dong);
        List<String> result = new ArrayList<>();
        findResults.forEach((findResult) -> {
            result.add(findResult.getHo());
        });
        return result;
    }

    public ApartInfo registApart(Map<String, Object> apartInfoObj) {
        ApartInfo apartInfo = objMpr.convertValue(apartInfoObj, ApartInfo.class);
        apartInfo.setApartId(apartInfoRepository.save(apartInfo).getApartId());
        return apartInfo;
    }
}
