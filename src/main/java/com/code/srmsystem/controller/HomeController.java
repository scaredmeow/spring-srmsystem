package com.code.srmsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.code.srmsystem.service.AuthService;

@Controller
@RequestMapping(path = "/")
public class HomeController {

    private AuthService authService;

    @Autowired
    public HomeController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String indexPage() {
        return this.authService.redirect("index");
    }

    @GetMapping(path = "/signup")
    public String signupPage() {
        return this.authService.redirect("signup");
    }

    @GetMapping(path = "/requests")
    public String requestsPage() {
        return this.authService.requestRedirect();
    }

    @PostMapping(path = "/signup")
    public ModelAndView signup(@RequestParam String username, @RequestParam String password,
            @RequestParam String cpassword, @RequestParam String email, @RequestParam String snumber,
            @RequestParam String fname, @RequestParam String mname, @RequestParam String lname,
            @RequestParam String mnumber, Model model) {
        return this.authService.signup(username, email, password, cpassword, snumber, fname, mname, lname, mnumber,
                "signup", model);
    }
}
