package com.example.isupervision.services;

import com.example.isupervision.entities.users.Student;
import com.example.isupervision.web.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
    // extends UserDetailsService
    void saveStudent(UserDTO registrationDto);

    void saveStaff(UserDTO registrationDto);

    Student fetchUserByEmail(String email);
}
