package com.example.isupervision.services;

import com.example.isupervision.entities.users.Student;
import com.example.isupervision.entities.works.Work;
import com.example.isupervision.entities.works.WorkLimits;
import com.example.isupervision.entities.works.WorkType;
import com.example.isupervision.web.dto.WorkDTO;

import java.util.ArrayList;
import java.util.List;

public interface WorkService {
    void saveWork(WorkDTO newWork);

    void updateLimits(WorkLimits limits);

    WorkLimits fetchLimits();

    ArrayList<Work> fetchUserWorksByEmail(String email);

    List<Work> fetchWorksByType(WorkType type);

    Work getWorkById(Long id);

    void updateWork(WorkDTO work, Long id);

    void deleteWorkById(Long id);

    void approveWorkById(Long id);

    ArrayList<Work> fetchRelatedUserWorks(Student student);

    void takeWork(Long id, Student student);
}
