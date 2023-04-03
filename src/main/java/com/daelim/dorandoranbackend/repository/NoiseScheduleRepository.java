package com.daelim.dorandoranbackend.repository;

import com.daelim.dorandoranbackend.entity.NoiseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoiseScheduleRepository extends JpaRepository<NoiseSchedule, Integer> {
}
