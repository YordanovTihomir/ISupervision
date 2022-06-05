package com.example.isupervision.repository;

import com.example.isupervision.entities.users.Student;
import com.example.isupervision.entities.works.Work;
import com.example.isupervision.entities.works.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {

    ArrayList<Work> findAllByType(WorkType type);

    ArrayList<Work> findAllByStudentEmail(String email);

    ArrayList<Work> findAllByTypeAndApprovedAndTakenFalse(WorkType type, boolean approved);


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Modifying
    @Query(
            "UPDATE Work SET student = :student WHERE id = :workId"
    )
    void setStudent(@Param("workId") Long id,@Param("student") Student student);
}
