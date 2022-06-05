package com.example.isupervision.repository;

import com.example.isupervision.entities.users.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StaffRepository extends JpaRepository<Staff, UUID> {

    @Query("SELECT staff FROM Staff staff JOIN FETCH staff.roles WHERE staff.email = :email")
    Staff getUserByEmail(@Param("email") String email);

    boolean existsByEmail(String email);
}
