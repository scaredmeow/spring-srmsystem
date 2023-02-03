package com.code.srmsystem.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.code.srmsystem.dao.UserDao;
import com.code.srmsystem.service.AuthService;

// import com.code.srmsystem.service.AuthService;

@Controller
public class CustomErrorController implements ErrorController {

    private AuthService authService;
    private UserDao userDao;

    public CustomErrorController(AuthService authService, UserDao userDao) {
        this.authService = authService;
        this.userDao = userDao;
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // String username = this.authService.getUser();
        // model.addAttribute("username", username);
        String errorPage = "error/error"; // default

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        model.addAttribute("username", this.userDao.findByEmail(this.authService.getUser()).getLast_name());
        model.addAttribute("date", this.authService.getDate());
        model.addAttribute("username", this.authService.getUser());
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // handle HTTP 404 Not Found error
                errorPage = "error/404";

                // } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                // // handle HTTP 403 Forbidden error
                // errorPage = "error/403";
                //
                // } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                // // handle HTTP 500 Internal Server error
                // errorPage = "error/500";
                //
            }
        }

        return errorPage;
    }

    // @Override
    // public String getErrorPath() {
    // return "/error";
    // }
}
