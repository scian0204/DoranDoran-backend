package com.daelim.dorandoranbackend.repository;

import com.daelim.dorandoranbackend.entity.WarningMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarningMessageRepository extends JpaRepository<WarningMessage, Integer> {
    Page<WarningMessage> findAllByUserId(Pageable pageable, String userId);
}
