package com.example.isupervision.entities.roles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class RolesCheck {
    private final RolesRepository rolesRepository;
    private final String STUDENT = "ROLE_STUDENT";
    private final String ASSISTANT = "ROLE_ASSISTANT";
    private final String ADMIN = "ROLE_ADMIN";

    public RolesCheck(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Bean
    public void checkRoles() {
        Role studentRole = new Role(STUDENT);
        Role assistantRole = new Role(ASSISTANT);
        Role adminRole = new Role(ADMIN);

        if (!rolesRepository.existsByName(STUDENT)){
            rolesRepository.save(studentRole);
        }if (!rolesRepository.existsByName(ASSISTANT)){
            rolesRepository.save(assistantRole);
        }if (!rolesRepository.existsByName(ADMIN)){
            rolesRepository.save(adminRole);
        }
    }
}
