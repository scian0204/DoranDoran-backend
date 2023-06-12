package com.daelim.dorandoranbackend.repository;

import com.daelim.dorandoranbackend.entity.ApartInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartInfoRepository extends JpaRepository<ApartInfo, Integer> {
    List<ApartInfo> findAllByApartNameLikeIgnoreCase(String apartName);
}
