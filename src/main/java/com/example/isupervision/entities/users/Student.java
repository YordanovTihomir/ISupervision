package com.example.isupervision.entities.users;

import com.example.isupervision.entities.roles.Role;
import com.example.isupervision.entities.works.Work;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table
public class Student extends User{

    @Column(name = "has_project")
    private boolean hasProject;

    @Column(name = "has_bachelor")
    private boolean hasBachelor;

    @Column(name = "has_master")
    private boolean hasMaster;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Role role;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Work> works = new ArrayList<>();

    public List<Work> getWorks() {
        return works;
    }

    public void addWork(Work work) {
        this.works.add(work);
    }

    public Student(){}

    public Student(
            String firstname,
            String lastName,
            String email,
            String password,
            Role role
    ){
//        setId(UUID.randomUUID());
        setFirstName(firstname);
        setLastName(lastName);
        setEmail(email);
        setPassword(password);
        this.role = role;
    }

    public boolean isHasProject() {
        return hasProject;
    }

    public boolean isHasBachelor() {
        return hasBachelor;
    }

    public boolean isHasMaster() {
        return hasMaster;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean hasProject() {
        return hasProject;
    }

    public void setHasProject(boolean hasProject) {
        this.hasProject = hasProject;
    }

    public boolean hasBachelor() {
        return hasBachelor;
    }

    public void setHasBachelor(boolean hasBachelor) {
        this.hasBachelor = hasBachelor;
    }

    public boolean hasMaster() {
        return hasMaster;
    }

    public void setHasMaster(boolean hasMaster) {
        this.hasMaster = hasMaster;
    }

}

