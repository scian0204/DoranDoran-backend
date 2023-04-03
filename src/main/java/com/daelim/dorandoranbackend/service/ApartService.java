package com.daelim.dorandoranbackend.service;

import com.daelim.dorandoranbackend.entity.Apart;
import com.daelim.dorandoranbackend.repository.ApartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartService {
    @Autowired
    ApartRepository apartRepository;

    public List<String> getApartList(String apartName) {
        return apartRepository.findDistinctApartNameByApartName(apartName);
    }

    public List<String> getDongList(String dong) {
        return apartRepository.findDistinctDongByApartName(dong);
    }

    public List<String> getHoList(String apartName, String dong) {
        return apartRepository.findDistinctHoByApartNameAndDong(apartName, dong);
    }
}
