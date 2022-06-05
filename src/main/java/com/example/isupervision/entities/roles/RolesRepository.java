package com.example.isupervision.entities.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {


    boolean existsByName(String str);

    Role getByName(String name);

}
