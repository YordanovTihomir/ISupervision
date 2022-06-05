package com.example.isupervision.entities.works;

import javax.persistence.*;

@Entity
public class WorkLimits {
    @Id
    private Integer year;

    @Column(name = "project_limit")
    public int maxNumOfProjects;

    @Column(name = "bachelor_limit")
    public int maxNumOfBachelor;

    @Column(name = "master_limit")
    public int maxNumOfMaster;

    public WorkLimits(){}

    public WorkLimits(Integer year){
        this.maxNumOfBachelor = 0;
        this.maxNumOfMaster = 0;
        this.maxNumOfProjects = 0;
        this.year = year;
    }

    public int getMaxNumOfProjects() {
        return maxNumOfProjects;
    }

    public void setMaxNumOfProjects(int maxNumOfProjects) {
        this.maxNumOfProjects = maxNumOfProjects;
    }

    public int getMaxNumOfBachelor() {
        return maxNumOfBachelor;
    }

    public void setMaxNumOfBachelor(int maxNumOfBachelor) {
        this.maxNumOfBachelor = maxNumOfBachelor;
    }

    public int getMaxNumOfMaster() {
        return maxNumOfMaster;
    }

    public void setMaxNumOfMaster(int maxNumOfMaster) {
        this.maxNumOfMaster = maxNumOfMaster;
    }
}
