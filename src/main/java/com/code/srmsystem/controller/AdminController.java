package com.code.srmsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/requests/admin")
public class AdminController {

    @GetMapping
    public String adminPage() {
        return "admin";
    }

    @GetMapping(path = "/reports")
    public String reportsPage() {
        return "admin-report";
    }
}
