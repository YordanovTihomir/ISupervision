package com.example.isupervision.web.dto;

import com.example.isupervision.entities.works.WorkType;

import java.io.Serializable;

public class WorkLimitsDTO implements Serializable {

    private WorkType workType;
    private int limit;

    public WorkLimitsDTO(){}

    public WorkLimitsDTO(
            WorkType workType,
            int limit
    ){
        this.workType = workType;
        this.limit = limit;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
