package com.example.isupervision.web;

import com.example.isupervision.entities.works.WorkLimits;
import com.example.isupervision.services.WorkService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

class StaffHomepageControllerTest {
    private WorkLimits limits;

    public final WorkService workService;


    public StaffHomepageControllerTest(
            WorkService workService
    ){
        this.workService = workService;
    }

    public WorkLimits setWorkLimits(){
        limits = workService.fetchLimits();
        return limits;
    }

    public void setLimits(WorkLimits limits) {
        this.limits = setWorkLimits();
    }

}