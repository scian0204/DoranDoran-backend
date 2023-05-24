package com.daelim.dorandoranbackend.repository;

import com.daelim.dorandoranbackend.controller.responseObject.DongResponse;
import com.daelim.dorandoranbackend.entity.Apart;
import com.daelim.dorandoranbackend.controller.responseObject.HoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartRepository extends JpaRepository<Apart, Integer> {
    List<DongResponse> findDistinctByApartId(Integer apartId);
    List<HoResponse> findDistinctByApartIdAndDong(Integer apartId, String dong);
}