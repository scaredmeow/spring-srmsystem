package com.code.srmsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.code.srmsystem.dao.UserDao;
import com.code.srmsystem.model.User;
import com.code.srmsystem.security.UserAccount;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private User user;

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    @Override
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
            Model model) {

        boolean existsUser = this.userDao.existsUsername(username);
        boolean existsEmail = this.userDao.existsEmail(email);
        boolean existsStudentNumber = this.userDao.existsStudentNumber(snumber);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("existsEmail", existsEmail);
        modelAndView.addObject("existsUser", existsUser);
        modelAndView.addObject("existsStudentNumber", existsStudentNumber);

        if (!cpassword.equals(password)) {
            modelAndView.addObject("errorConfirmPassword", "error");
        }

        if (!existsEmail && !existsUser && !existsStudentNumber) {
            if (cpassword.equals(password)) {
                user.setEmail(email);
                user.setPassword(passwordEncoder.encode(password));
                user.setUsername(username);
                user.setRole("STUDENT");
                user.setStudent_number(snumber);
                user.setFirst_name(fname);
                user.setMiddle_name(mname);
                user.setLast_name(lname);
                user.setMobile_number(mnumber);
                userDao.saveUserRegistration(user);

                model.asMap().clear();
                final ModelAndView mav = new ModelAndView("redirect:/");
                return mav;

            } else {
                modelAndView.setViewName(viewName);
            }

        } else {
            modelAndView.setViewName(viewName);
            modelAndView.setStatus(HttpStatus.CONFLICT);
        }

        return modelAndView;
    }

    @Override
    public String getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserAccount) {
            String username = ((UserAccount) principal).getUsername();
            return username;
        } else {
            String username = principal.toString();
            return username;
        }
    }

    @Override
    public String redirect(String viewName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return viewName;
        }

        return "redirect:/requests";
    }

    @Override
    public String requestRedirect() {
        user = this.userDao.loginByUserName(this.getUser());
        String view = "redirect:/";
        if (user.getRole().equals("STUDENT")) {
            view = "redirect:/requests/student";
        } else if (user.getRole().equals("ADMIN")) {
            view = "redirect:/requests/admin";
        } else if (user.getRole().equals("EMPLOYEE")) {
            view = "redirect:/requests/admin";
        }
        return view;
    }

    @Override
    public ModelAndView displayUserNav(String viewName) {
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("username", this.getUser());
        return mav;
    }
}
