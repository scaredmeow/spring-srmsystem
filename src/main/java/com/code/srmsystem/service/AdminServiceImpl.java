package com.code.srmsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.code.srmsystem.dao.DashboardDao;
import com.code.srmsystem.dao.RequestDao;
import com.code.srmsystem.dao.UserDao;
import com.code.srmsystem.model.Dashboard;
import com.code.srmsystem.model.Request;
import com.code.srmsystem.model.User;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private User user;

    @Autowired
    private Request request;

    @Autowired
    private Dashboard dashboard;

    private PasswordEncoder passwordEncoder;
    private UserDao userDao;
    private AuthService authService;
    private RequestDao requestDao;
    private DashboardDao dashboardDao;

    public AdminServiceImpl(UserDao userDao, AuthService authService, RequestDao requestDao,
            DashboardDao dashboardDao, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.userDao = userDao;
        this.requestDao = requestDao;
        this.dashboardDao = dashboardDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ModelAndView displayUserNoTable(String viewName) {
        ModelAndView mav = new ModelAndView(viewName);
        user = this.userDao.findByEmail(this.authService.getUser());
        mav.addObject("username", user.getLast_name());
        mav.addObject("date", this.authService.getDate());
        mav.addObject("noTable", true);
        List<Request> requests = this.requestDao.getRecentRequests();
        mav.addObject("requests", requests);
        dashboard = this.dashboardDao.getNumber();
        mav.addObject("pickup", dashboard.getPickup());
        mav.addObject("checking", dashboard.getChecking());
        mav.addObject("approval", dashboard.getApproval());
        mav.addObject("fincheck", dashboard.getFincheck());
        mav.addObject("printing", dashboard.getPrinting());
        mav.addObject("rejected", dashboard.getRejected());
        return mav;
    }

    @Override
    public ModelAndView displayStatusRequests(String method, String viewName) {
        ModelAndView mav = new ModelAndView(viewName);
        user = this.userDao.findByEmail(this.authService.getUser());
        mav.addObject("username", user.getLast_name());
        mav.addObject("date", this.authService.getDate());
        mav.addObject("noTable", true);
        List<Request> requests = this.requestDao.getStatusRequests(method);
        mav.addObject("requests", requests);
        dashboard = this.dashboardDao.getNumber();
        mav.addObject("pickup", dashboard.getPickup());
        mav.addObject("checking", dashboard.getChecking());
        mav.addObject("approval", dashboard.getApproval());
        mav.addObject("fincheck", dashboard.getFincheck());
        mav.addObject("printing", dashboard.getPrinting());
        mav.addObject("rejected", dashboard.getRejected());
        return mav;
    }

    @Override
    public ModelAndView displayUserTable(String snumber, String viewName) {
        ModelAndView mav = new ModelAndView(viewName);
        user = this.userDao.findByEmail(this.authService.getUser());
        mav.addObject("username", user.getLast_name());
        mav.addObject("date", this.authService.getDate());
        dashboard = this.dashboardDao.getNumber();
        mav.addObject("pickup", dashboard.getPickup());
        mav.addObject("checking", dashboard.getChecking());
        mav.addObject("approval", dashboard.getApproval());
        mav.addObject("fincheck", dashboard.getFincheck());
        mav.addObject("printing", dashboard.getPrinting());
        mav.addObject("rejected", dashboard.getRejected());
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
        user = this.userDao.findByEmail(this.authService.getUser());
        mav.addObject("username", user.getLast_name());
        mav.addObject("date", this.authService.getDate());
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
    public ModelAndView displayCurrentTransactionAssign(int RID) {
        ModelAndView mav = new ModelAndView("admin-check");
        user = this.userDao.findByEmail(this.authService.getUser());
        mav.addObject("username", user.getLast_name());
        mav.addObject("date", this.authService.getDate());
        request = this.requestDao.getRequest(RID);
        mav.addObject("requests", request);
        User student = new User();
        student = this.userDao.findByStudentNumber(request.getStudent_number());
        mav.addObject("snumber", request.getStudent_number());
        mav.addObject("fullname",
                student.getLast_name() + ", " + student.getFirst_name() + " " + student.getMiddle_name());
        mav.addObject("RID", RID);
        this.requestDao.updateAssign(user.getLast_name(), RID);
        return mav;
    }

    @Override
    public ModelAndView updateCurrentTransaction(int RID, String method) {
        ModelAndView mav = new ModelAndView("admin-check");
        user = this.userDao.findByEmail(this.authService.getUser());
        mav.addObject("username", user.getLast_name());
        mav.addObject("date", this.authService.getDate());
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

    @Override
    public String updateComment(int RID, String comment) {
        this.requestDao.updateComment(RID, comment);
        return "redirect:/requests/admin/check/" + RID;
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
                user.setRole("EMPLOYEE");
                user.setIs_active(1);
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
    public ModelAndView displayAllEmployees(String viewName) {
        ModelAndView mav = new ModelAndView(viewName);
        user = this.userDao.findByEmail(this.authService.getUser());
        mav.addObject("username", user.getLast_name());
        mav.addObject("date", this.authService.getDate());
        List<User> users = this.userDao.getAllUsers();
        mav.addObject("users", users);
        return mav;
    }

    @Override
    public ModelAndView deleteEmployee(String viewName, int UID) {
        ModelAndView mav = new ModelAndView(viewName);
        user = this.userDao.findByEmail(this.authService.getUser());
        mav.addObject("username", user.getLast_name());
        mav.addObject("date", this.authService.getDate());
        String email = this.userDao.findByUserID(UID).getEmail();
        this.userDao.deleteUser(email);
        this.userDao.deleteLogin(email);
        return mav;
    }

}
