package com.example.isupervision.web;

//
import com.example.isupervision.repository.StaffRepository;
import com.example.isupervision.repository.StudentRepository;
import com.example.isupervision.services.UserService;
import com.example.isupervision.validator.InputValidator;
import com.example.isupervision.web.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    private final UserService userService;
    private final StudentRepository studentRepository;
    private final StaffRepository staffRepository;
    private final InputValidator inputValidator;

    public UserRegistrationController(
            UserService userService,
            StudentRepository studentRepository,
            StaffRepository staffRepository,
            InputValidator inputValidator
    ){
        this.userService = userService;
        this.studentRepository = studentRepository;
        this.staffRepository = staffRepository;
        this.inputValidator = inputValidator;
    }

    //reference to registration.html whenever user enter a form data that data will be stored in the dto
    @ModelAttribute("user")
    public UserDTO userRegistrationDto(){
        return new UserDTO();
    }

    @GetMapping
    public String showRegistrationForm(Model model){
        UserDTO user = new UserDTO();
        model.addAttribute("userDto", user);
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(
            @ModelAttribute("user")UserDTO registrationDto,
            RedirectAttributes redirectAttributes
    ) {

        if (!allFieldsAreFilled(registrationDto)){
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Please fill all the fields!");
            return "redirect:/registration";
        }
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())){
            redirectAttributes.addFlashAttribute("error", "Passwords must match!");
            return "redirect:/registration";
        }
        if (!inputValidator.test(registrationDto.getEmail())){
            redirectAttributes.addFlashAttribute("error", "Email is not valid!");
            return "redirect:/registration";
        }
        if (registrationDto.getRole() == null) {
            redirectAttributes.addFlashAttribute("error", "Please specify a role!");
            return "redirect:/registration";
        }
        if (studentRepository.existsByEmail(registrationDto.getEmail()) ||
                staffRepository.existsByEmail(registrationDto.getEmail())){
            redirectAttributes.addFlashAttribute("error", "This email is already taken!");
            return "redirect:/registration";
        }

        if (registrationDto.getRole().equals("ROLE_STUDENT")){
            userService.saveStudent(registrationDto);
            redirectAttributes.addFlashAttribute("success", "Successful Registration");
            return "/login";
        }

        else if (registrationDto.getRole().equals("ROLE_ASSISTANT") ||
                registrationDto.getRole().equals("ROLE_ADMIN")){
            redirectAttributes.addFlashAttribute("success", "Successful Registration");
            userService.saveStaff(registrationDto);
        }
        return "/login";
    }

    private boolean allFieldsAreFilled(UserDTO registrationDto) {
        if (registrationDto.getFirstName().isEmpty()
                || registrationDto.getLastName().isEmpty()
                || registrationDto.getPassword().isEmpty()
                || registrationDto.getConfirmPassword().isEmpty()
                || registrationDto.getEmail().isEmpty()){
            return false;
        }return true;
    }
}