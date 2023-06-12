package com.daelim.dorandoranbackend.repository;

import com.daelim.dorandoranbackend.dto.response.DongResponse;
import com.daelim.dorandoranbackend.entity.Apart;
import com.daelim.dorandoranbackend.dto.response.HoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApartRepository extends JpaRepository<Apart, Integer> {
    List<DongResponse> findDistinctByApartId(Integer apartId);
//    List<HoResponse> findDistinctByApartIdAndDong(Integer apartId, String dong);
    List<Apart> findByApartIdAndDong(Integer apartId, String dong);
    Optional<Apart> findByApartIdAndDongAndHo(Integer apartId, String dong, String ho);
}