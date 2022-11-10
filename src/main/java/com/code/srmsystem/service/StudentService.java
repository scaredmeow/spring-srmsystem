package com.code.srmsystem.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public interface StudentService {
    public ModelAndView displayUserandTransactions(String viewName);

    public ModelAndView createTransaction(String[] requests, String viewName, Model model);
}
