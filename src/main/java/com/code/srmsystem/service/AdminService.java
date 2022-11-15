package com.code.srmsystem.service;

import org.springframework.web.servlet.ModelAndView;

public interface AdminService {
    public ModelAndView displayUserNoTable(String viewName);

    public ModelAndView displayUserTable(String snumber, String viewName);

    public ModelAndView displayCurrentTransaction(int RID);

    public ModelAndView updateCurrentTransaction(int RID, String method);
}
