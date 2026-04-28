package com.taron.enterprises.repositories;

import com.taron.enterprises.models.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterprisesRepository extends JpaRepository<Enterprise, Integer> {

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    List<Enterprise> findByType(String type);
}
