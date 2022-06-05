package com.example.isupervision.services;

import com.example.isupervision.entities.roles.Role;
import com.example.isupervision.entities.roles.RolesRepository;
import com.example.isupervision.entities.users.CustomUserPrincipal;
import com.example.isupervision.entities.users.Staff;
import com.example.isupervision.entities.users.Student;
import com.example.isupervision.repository.StaffRepository;
import com.example.isupervision.repository.StudentRepository;
import com.example.isupervision.web.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final String STUDENT = "ROLE_STUDENT";
    private final String ASSISTANT = "ROLE_ASSISTANT";
    private final String ADMIN = "ROLE_ADMIN";

    private final StudentRepository studentRepository;
    private final StaffRepository staffRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;

    public UserServiceImpl(
            StudentRepository studentRepository,
            StaffRepository staffRepository,
            BCryptPasswordEncoder passwordEncoder,
            RolesRepository rolesRepository
    ){
        this.studentRepository = studentRepository;
        this.staffRepository = staffRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public void saveStaff(UserDTO registrationDto) {
        List<Role> roles = new ArrayList<>();
        if (registrationDto.getRole().equals(ADMIN)){
            roles = List.of(rolesRepository.getByName(ADMIN), rolesRepository.getByName(ASSISTANT));
        }
        else if (registrationDto.getRole().equals(ASSISTANT)){
            roles = List.of(rolesRepository.getByName(ASSISTANT));
        }
        Staff staff = new Staff(
                registrationDto.getFirstName(),
                registrationDto.getLastName(),
                registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()),
                roles
        );
        staffRepository.save(staff);
    }

    @Override
    public Student fetchUserByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Override
    public void saveStudent(UserDTO registrationDto){
        Student user = new Student(
                registrationDto.getFirstName(),
                registrationDto.getLastName(),
                registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()),
                rolesRepository.getByName(STUDENT)
        );
        studentRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (staffRepository.existsByEmail(email)) {
            Staff staff = staffRepository.getUserByEmail(email);
            return new CustomUserPrincipal(
                    staff.getEmail(),
                    staff.getPassword(),
                    mapRolesToAuthorities(staff.getRoles()),
                    staff.getFirstName(),
                    staff.getLastName());
        }else if (studentRepository.existsByEmail(email)){
            Student student = studentRepository.findByEmail(email);
            return new CustomUserPrincipal(
                    student.getEmail(),
                    student.getPassword(),
                    mapRolesToAuthorities(List.of(student.getRole())),
                    student.getFirstName(),
                    student.getLastName());
        }else
            throw new UsernameNotFoundException("Invalid username or password.");
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        System.out.println(roles.stream());
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}