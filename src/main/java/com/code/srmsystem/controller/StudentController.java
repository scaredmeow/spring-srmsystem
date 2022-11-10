package com.code.srmsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.code.srmsystem.service.AuthService;

@Controller
@RequestMapping(path = "/requests/student")
public class StudentController {

    private AuthService authService;

    public StudentController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String studentPage(Model model) {
        model.addAttribute("username", this.authService.getUser());
        return "student";
    }

    @GetMapping(path = "/new")
    public String newRequestPage() {
        return "student-req";
    }

    @GetMapping(path = "/receipt")
    public String receiptPage() {
        return "student-req-receipt";
    }
}
