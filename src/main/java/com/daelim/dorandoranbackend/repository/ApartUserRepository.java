package com.daelim.dorandoranbackend.repository;

import com.daelim.dorandoranbackend.entity.ApartUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartUserRepository extends JpaRepository<ApartUser, Integer> {
}
