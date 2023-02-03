package com.code.srmsystem.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public interface AdminService {
    public ModelAndView displayUserNoTable(String viewName);

    public ModelAndView displayUserTable(String snumber, String viewName);

    public ModelAndView displayStatusRequests(String method, String viewName);

    public ModelAndView displayCurrentTransaction(int RID);

    public ModelAndView displayCurrentTransactionAssign(int RID);

    public ModelAndView updateCurrentTransaction(int RID, String method);

    public String updateComment(int RID, String comment);

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
}
