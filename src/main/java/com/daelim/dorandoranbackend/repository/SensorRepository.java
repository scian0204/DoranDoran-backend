package com.daelim.dorandoranbackend.repository;

import com.daelim.dorandoranbackend.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, String> {
    List<Sensor> findAllByApartIdx(Integer apartIdx);
}
