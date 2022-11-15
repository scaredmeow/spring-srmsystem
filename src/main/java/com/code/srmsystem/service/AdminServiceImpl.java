package com.code.srmsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.code.srmsystem.dao.RequestDao;
import com.code.srmsystem.dao.UserDao;
import com.code.srmsystem.model.Request;
import com.code.srmsystem.model.User;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private User user;

    @Autowired
    private Request request;

    private UserDao userDao;
    private AuthService authService;
    private RequestDao requestDao;

    public AdminServiceImpl(UserDao userDao, AuthService authService, RequestDao requestDao) {
        this.authService = authService;
        this.userDao = userDao;
        this.requestDao = requestDao;
    }

    @Override
    public ModelAndView displayUserNoTable(String viewName) {
        ModelAndView mav = new ModelAndView(viewName);
        user = this.userDao.findByUserName(this.authService.getUser());
        mav.addObject("username", user.getUsername());
        mav.addObject("noTable", true);
        List<Request> requests = this.requestDao.getRecentRequests();
        mav.addObject("requests", requests);
        return mav;
    }

    @Override
    public ModelAndView displayUserTable(String snumber, String viewName) {
        ModelAndView mav = new ModelAndView(viewName);
        user = this.userDao.findByUserName(this.authService.getUser());
        mav.addObject("username", user.getUsername());
        try {
            User student = new User();
            student = this.userDao.findByStudentNumber(snumber);
            mav.addObject("snumber", snumber);
            mav.addObject("fullname",
                    student.getLast_name() + ", " + student.getFirst_name() + " " + student.getMiddle_name());
            List<Request> requests = this.requestDao.getSpecificRequests(snumber);
            mav.addObject("requests", requests);
            mav.addObject("noTable", false);
        } catch (Exception e) {
            mav.addObject("errorSnumber", "Student number does not exist");
            List<Request> requests = this.requestDao.getRecentRequests();
            mav.addObject("requests", requests);
            mav.addObject("noTable", true);
        }
        return mav;
    }

    @Override
    public ModelAndView displayCurrentTransaction(int RID) {
        ModelAndView mav = new ModelAndView("admin-check");
        user = this.userDao.findByUserName(this.authService.getUser());
        mav.addObject("username", user.getUsername());
        request = this.requestDao.getRequest(RID);
        mav.addObject("requests", request);
        User student = new User();
        student = this.userDao.findByStudentNumber(request.getStudent_number());
        mav.addObject("snumber", request.getStudent_number());
        mav.addObject("fullname",
                student.getLast_name() + ", " + student.getFirst_name() + " " + student.getMiddle_name());
        mav.addObject("RID", RID);
        return mav;
    }

    @Override
    public ModelAndView updateCurrentTransaction(int RID, String method) {
        ModelAndView mav = new ModelAndView("admin-check");
        user = this.userDao.findByUserName(this.authService.getUser());
        mav.addObject("username", user.getUsername());
        this.requestDao.updateRequest(RID, method);

        request = this.requestDao.getRequest(RID);
        mav.addObject("requests", request);
        User student = new User();
        student = this.userDao.findByStudentNumber(request.getStudent_number());
        mav.addObject("snumber", request.getStudent_number());
        mav.addObject("fullname",
                student.getLast_name() + ", " + student.getFirst_name() + " " + student.getMiddle_name());
        return mav;
    }
}
