package com.daelim.dorandoranbackend.service;

import com.daelim.dorandoranbackend.controller.requestObject.ApartAllRequest;
import com.daelim.dorandoranbackend.controller.responseObject.DongResponse;
import com.daelim.dorandoranbackend.controller.responseObject.Error;
import com.daelim.dorandoranbackend.controller.responseObject.Response;
import com.daelim.dorandoranbackend.entity.Apart;
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

    public Response<List<ApartInfo>> getApartList(String apartName) {
        Response<List<ApartInfo>> res = new Response<>();
        res.setData(apartInfoRepository.findAllByApartName(apartName));
        return res;
    }

    public Response<List<String>> getDongList(Integer apartId) {
        Response<List<String>> res = new Response<>();
        List<DongResponse> findResults = apartRepository.findDistinctByApartId(apartId);
        List<String> result = new ArrayList<>();
        findResults.forEach(findResult -> {
            result.add(findResult.getDong());
        });
        res.setData(result);
        return res;
    }

    public Response<List<String>> getHoList(Integer apartId, String dong) {
        Response<List<String>> res = new Response<>();
        List<HoResponse> findResults = apartRepository.findDistinctByApartIdAndDong(apartId, dong);
        List<String> result = new ArrayList<>();
        findResults.forEach((findResult) -> {
            result.add(findResult.getHo());
        });
        res.setData(result);
        return res;
    }

    public Response<ApartInfo> registApart(Map<String, Object> apartInfoObj) {
        Response<ApartInfo> res = new Response<>();
        ApartInfo apartInfo = objMpr.convertValue(apartInfoObj, ApartInfo.class);
        apartInfo.setApartId(apartInfoRepository.save(apartInfo).getApartId());
        res.setData(apartInfo);
        return res;
    }

    public Response<List<Apart>> registAll(Map<String, Object> apartAll) {
        ApartAllRequest aar = objMpr.convertValue(apartAll, ApartAllRequest.class);
        Response<List<Apart>> res = new Response<>();
        if (apartInfoRepository.findById(aar.getApartId()).isPresent()) {
            List<Apart> aparts = new ArrayList<>();

            aar.getDongs().forEach(dong -> {
                for (int i = 1; i <= aar.getNumOfFloor(); i++) {
                    for (int j = 0; j < aar.getNumOfHo(); j++) {
                        Apart apart = new Apart();
                        apart.setApartId(aar.getApartId());
                        apart.setDong(dong);
                        String ho = String.format("%02d", j);
                        apart.setHo(i + ho);
                        aparts.add(apartRepository.save(apart));
                    }
                }
            });
            res.setData(aparts);
        } else {
            Error error = new Error();
            error.setErrorId(0);
            error.setMessage("해당 apartId를 가진 아파트 정보가 없음");
            res.setError(error);
        }
        return res;
    }
}
