package com.example.isupervision.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class MainController {

    @GetMapping
    public String login(){
        return "login";
    }
}
