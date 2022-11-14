package com.code.srmsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ModelAndView postNewTrans(@RequestParam("doc") String[] requests, Model model) {
        return this.studentService.createTransaction(requests, "student-req", model);
    }

    @PostMapping(path = "/{TID}")
    public ModelAndView postUnpaidTrans(@PathVariable("TID") int TID) {
        return this.studentService.updateTransaction(TID, "student-req-receipt");
    }

    @PostMapping(path = "/new/submit")
    public ModelAndView postSubmitTrans(@RequestParam("payment") String payment) {
        return this.studentService.submitTransaction(payment, "redirect:/requests");
    }

    @PostMapping(path = "/{TID}/submit")
    public ModelAndView postSpecificTrans(@RequestParam("payment") String payment, @PathVariable("TID") int TID) {
        return this.studentService.submitSpecificTransaction(payment, TID, "redirect:/requests");
    }

}
