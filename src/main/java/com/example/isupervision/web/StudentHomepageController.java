package com.example.isupervision.web;

import com.example.isupervision.entities.users.Student;
import com.example.isupervision.entities.works.Work;
import com.example.isupervision.services.UserService;
import com.example.isupervision.services.WorkService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/studentHomepage")
public class StudentHomepageController {

    private final WorkService workService;

    private final UserService userService;

    public StudentHomepageController(
            WorkService workService,
            UserService userService
    ){
        this.workService = workService;
        this.userService = userService;
    }

    @GetMapping
    public String showStudentHomepage(Model model){
        String userEmail = getEmailFromSecurityContextHolder();

        List<Work> userWorks = new ArrayList<>();
        List<Work> relatedWorks = new ArrayList<>();
        Student student = userService.fetchUserByEmail(userEmail);

        userWorks = workService.fetchUserWorksByEmail(userEmail);
        relatedWorks = workService.fetchRelatedUserWorks(student);

        model.addAttribute("userWorks", userWorks);
        model.addAttribute("relatedWorks", relatedWorks);

        return "studentHomepage";
    }

    @GetMapping("/take/{id}")
    public String takeWork(@PathVariable Long id){
        String userEmail = getEmailFromSecurityContextHolder();
        Student student = userService.fetchUserByEmail(userEmail);

        workService.takeWork(id, student);

        return "redirect:/studentHomepage";
    }

    private String getEmailFromSecurityContextHolder() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = "";

        if (principal instanceof UserDetails){
            email = ((UserDetails)principal).getUsername();
        }else email = principal.toString();

        return email;
    }
}
