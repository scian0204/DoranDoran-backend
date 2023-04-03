package com.daelim.dorandoranbackend.repository;

import com.daelim.dorandoranbackend.entity.Apart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartRepository extends JpaRepository<Apart, Integer> {
    List<String> findDistinctApartNameByApartName(String apartName);
    List<String> findDistinctDongByApartName(String apartName);
    List<String> findDistinctHoByApartNameAndDong(String apartName, String dong);
}