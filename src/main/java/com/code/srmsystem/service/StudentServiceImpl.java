package com.code.srmsystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.code.srmsystem.dao.DocumentDao;
import com.code.srmsystem.dao.UserDao;
import com.code.srmsystem.model.Document;
import com.code.srmsystem.model.User;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private User user;

    private AuthService authService;
    private UserDao userDao;
    private DocumentDao documentDao;

    public StudentServiceImpl(AuthService authService, UserDao userDao, DocumentDao documentDao) {
        this.authService = authService;
        this.userDao = userDao;
        this.documentDao = documentDao;
    }

    @Override
    public ModelAndView displayUserandTransactions(String viewName) {
        ModelAndView mav = new ModelAndView(viewName);
        user = this.userDao.findByUserName(this.authService.getUser());
        mav.addObject("username", user.getUsername());
        mav.addObject("fullname", user.getLast_name() + ", " + user.getFirst_name() + " " + user.getMiddle_name());
        return mav;
    }

    @Override
    public ModelAndView createTransaction(String[] requests, String viewName, Model model) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("username", this.authService.getUser());
        if (requests.length > 1) {
            List<Document> documents = new ArrayList<Document>();
            float total = 0;
            for (int i = 1; i < requests.length; i++) {
                Document document = new Document();
                document = this.documentDao.findByDocumentID(Integer.parseInt(requests[i]));
                total = total + document.getPrice();
                documents.add(document);
            }
            mav.addObject("total", total);
            mav.addObject("nullArray", false);
            mav.addObject("document", documents);
            mav.setViewName("student-req-receipt");
        } else {
            mav.addObject("nullArray", true);
            mav.setViewName(viewName);
        }
        // TODO: Create a list of Documents and add them to the model

        // if (requests != null) {
        // for (String string : requests) {
        // System.out.println(string);
        // }
        // }
        String str = String.join(",", requests);

        System.out.println(str);

        return mav;
    }

}
