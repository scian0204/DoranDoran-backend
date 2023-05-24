package com.daelim.dorandoranbackend.repository;

import com.daelim.dorandoranbackend.entity.SensorReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorReportRepository extends JpaRepository<SensorReport, Integer> {
}
