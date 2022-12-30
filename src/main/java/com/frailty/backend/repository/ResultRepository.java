package com.frailty.backend.repository;

import com.frailty.backend.entity.AppUser;
import com.frailty.backend.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResultRepository extends JpaRepository<Result, UUID> {
    List<Result> findByAppUser(AppUser patient);
}
