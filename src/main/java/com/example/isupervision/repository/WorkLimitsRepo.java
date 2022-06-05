package com.example.isupervision.repository;

import com.example.isupervision.entities.works.WorkLimits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkLimitsRepo extends JpaRepository<WorkLimits, Integer> {
    WorkLimits getWorkLimitsByYear(Integer year);


}
