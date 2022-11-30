package com.code.srmsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.code.srmsystem.service.AdminService;

@Controller
@RequestMapping(path = "/requests/admin")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ModelAndView adminPage() {
        return this.adminService.displayUserNoTable("admin");
    }

    @PostMapping(path = "/search")
    public ModelAndView search(@RequestParam("snumber") String snumber) {
        if (snumber.equals("")) {
            return this.adminService.displayUserNoTable("admin");
        } else {
            return this.adminService.displayUserTable(snumber, "admin");
        }
    }

    @GetMapping(path = "/check/{RID}")
    public ModelAndView checkPage(@PathVariable("RID") int RID) {
        return this.adminService.displayCurrentTransaction(RID);
    }

    @PostMapping(path = "/check/{RID}/{method}")
    public ModelAndView checkPage(@PathVariable("RID") int RID, @PathVariable("method") String method) {
        return this.adminService.updateCurrentTransaction(RID, method);

    }
}
