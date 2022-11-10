package com.code.srmsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.code.srmsystem.service.AuthService;
import com.code.srmsystem.service.StudentService;

@Controller
@RequestMapping(path = "/requests/student")
public class StudentController {

    private AuthService authService;
    private StudentService studentService;

    public StudentController(AuthService authService, StudentService studentService) {
        this.authService = authService;
        this.studentService = studentService;
    }

    @GetMapping
    public ModelAndView studentPage(Model model) {
        return this.studentService.displayUserandTransactions("student");
    }

    @GetMapping(path = "/new")
    public ModelAndView newRequestPage() {
        return this.authService.displayUserNav("student-req");
    }

    @GetMapping(path = "/receipt")
    public ModelAndView receiptPage() {
        return this.authService.displayUserNav("student-req-receipt");
    }

    @PostMapping(path = "/new")
    public ModelAndView trial(@RequestParam("doc") String[] requests, Model model) {
        return this.studentService.createTransaction(requests, "student-req", model);
    }

}
