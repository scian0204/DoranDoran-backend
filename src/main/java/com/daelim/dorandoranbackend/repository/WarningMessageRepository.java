package com.daelim.dorandoranbackend.repository;

import com.daelim.dorandoranbackend.entity.WarningMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarningMessageRepository extends JpaRepository<WarningMessage, Integer> {
    List<WarningMessage> findAllByUserId(String userId);
}
