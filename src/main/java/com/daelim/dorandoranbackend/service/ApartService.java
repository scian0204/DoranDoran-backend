package com.daelim.dorandoranbackend.service;

import com.daelim.dorandoranbackend.dto.request.ApartAllRequest;
import com.daelim.dorandoranbackend.dto.response.*;
import com.daelim.dorandoranbackend.dto.response.Error;
import com.daelim.dorandoranbackend.entity.Apart;
import com.daelim.dorandoranbackend.entity.ApartInfo;
import com.daelim.dorandoranbackend.entity.ApartUser;
import com.daelim.dorandoranbackend.entity.User;
import com.daelim.dorandoranbackend.repository.ApartInfoRepository;
import com.daelim.dorandoranbackend.repository.ApartRepository;
import com.daelim.dorandoranbackend.repository.ApartUserRepository;
import com.daelim.dorandoranbackend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ApartService {
    ObjectMapper objMpr = new ObjectMapper();
    @Autowired
    ApartRepository apartRepository;
    @Autowired
    ApartInfoRepository apartInfoRepository;
    @Autowired
    ApartUserRepository apartUserRepository;
    @Autowired
    UserRepository userRepository;

    public Response<List<ApartInfo>> getApartList(String apartName) {
        Response<List<ApartInfo>> res = new Response<>();
        res.setData(apartInfoRepository.findAllByApartNameLikeIgnoreCase("%"+apartName+"%"));
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

    public Response<List<Apart>> getHoList(Integer apartId, String dong) {
        Response<List<Apart>> res = new Response<>();
        List<Apart> results = apartRepository.findByApartIdAndDong(apartId, dong);
        res.setData(results);
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
                    for (int j = 1; j <= aar.getNumOfHo(); j++) {
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

    public Response<List<UserInfoResponse>> getUserByHo(Integer apartIdx) {
        Response<List<UserInfoResponse>> res = new Response<>();
        List<UserInfoResponse> results = new ArrayList<>();
        List<ApartUser> apartUsers = apartUserRepository.findAllByApartIdx(apartIdx);
        apartUsers.forEach(apartUser -> {
            User user = userRepository.getReferenceById(apartUser.getUserId());
            results.add(new UserInfoResponse(user));
        });

        res.setData(results);

        return res;
    }

    public Response<Apart> getInfoByUserId(String userId) {
        ApartUser apartUser = apartUserRepository.findByUserId(userId);
        Response<Apart> res = new Response<>();
        if (apartUser != null) {
            Apart apart = new Apart(apartRepository.getReferenceById(apartUser.getApartIdx()));

            res.setData(apart);
        } else {
            Error error = new Error();
            error.setErrorId(0);
            error.setMessage("해당 유저가 등록돼있지 않음");
            res.setError(error);
        }

        return res;
    }

    public Response<Apart> getApartByApartIdAndDongAndHo(Integer apartId, String dong, String ho) {
        Optional<Apart> apartOptional = apartRepository.findByApartIdAndDongAndHo(apartId, dong, ho);
        Response<Apart> res = new Response<>();
        if (apartOptional.isPresent()) {
            res.setData(apartOptional.get());
        } else {
            Error error = new Error();
            error.setErrorId(0);
            error.setMessage("해당 아파트가 없음");
            res.setError(error);
        }

        return res;
    }
}
