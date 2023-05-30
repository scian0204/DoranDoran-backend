package com.daelim.dorandoranbackend.repository;

import com.daelim.dorandoranbackend.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findAllByUserId(String userId);
}
