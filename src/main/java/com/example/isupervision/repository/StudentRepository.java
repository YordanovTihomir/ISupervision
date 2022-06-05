package com.example.isupervision.repository;

import com.example.isupervision.entities.users.Student;
import com.example.isupervision.entities.works.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    Student findByEmail(String email);

    boolean existsByEmail(String email);


}