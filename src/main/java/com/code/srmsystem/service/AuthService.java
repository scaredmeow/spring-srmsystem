package com.code.srmsystem.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public interface AuthService {

    public ModelAndView signup(
            String username,
            String email,
            String password,
            String cpassword,
            String snumber,
            String fname,
            String mname,
            String lname,
            String mnumber,
            String viewName,
            Model model);

    public String getUser();

    public String redirect(String viewName);

    public String requestRedirect();

    public ModelAndView displayUserNav(String viewName);
}
