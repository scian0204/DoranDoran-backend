package com.daelim.dorandoranbackend.repository;

import com.daelim.dorandoranbackend.entity.NoiseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoiseLogRepository extends JpaRepository<NoiseLog, Integer> {
    NoiseLog findFirstBySensorId(String sensorId);
}
