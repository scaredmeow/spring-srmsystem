package com.code.srmsystem.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.code.srmsystem.dao.DocumentDao;
import com.code.srmsystem.dao.RequestDao;
import com.code.srmsystem.dao.TransactionDao;
import com.code.srmsystem.dao.UserDao;
import com.code.srmsystem.model.Document;
import com.code.srmsystem.model.Request;
import com.code.srmsystem.model.Transaction;
import com.code.srmsystem.model.User;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private User user;

    @Autowired
    private Transaction transaction;

    private AuthService authService;
    private UserDao userDao;
    private DocumentDao documentDao;
    private TransactionDao transactionDao;
    private RequestDao requestDao;

    public StudentServiceImpl(AuthService authService, UserDao userDao, DocumentDao documentDao,
            TransactionDao transactionDao, RequestDao requestDao) {
        this.authService = authService;
        this.userDao = userDao;
        this.documentDao = documentDao;
        this.transactionDao = transactionDao;
        this.requestDao = requestDao;

    }

    @Override
    public ModelAndView displayUserandTransactions(String viewName) {
        ModelAndView mav = new ModelAndView(viewName);
        List<Transaction> transactions = this.transactionDao.getUnpaidTransactions();
        List<Request> requests = this.requestDao.getAllRequests();
        user = this.userDao.findByUserName(this.authService.getUser());
        mav.addObject("username", user.getUsername());
        mav.addObject("fullname", user.getLast_name() + ", " + user.getFirst_name() + " " + user.getMiddle_name());
        mav.addObject("transactions", transactions);
        mav.addObject("requests", requests);
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
            mav.addObject("noTID", false);
            mav.setViewName("student-req-receipt");

            String document_array = String.join(",", Arrays.copyOfRange(requests, 1, requests.length));
            User curr_user = this.userDao.findByUserName(this.authService.getUser());
            transaction.setUID(curr_user.getUID());
            transaction.setDocuments(document_array);

            if (this.transactionDao.saveTransaction(transaction)) {
                model.addAttribute("result", "Transaction successful!");
            } else {
                model.addAttribute("result", "Transaction failed!");
            }

        } else {
            mav.addObject("nullArray", true);
            mav.setViewName(viewName);
        }

        return mav;
    }

    @Override
    public ModelAndView updateTransaction(int TID, String viewName) {
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("noTID", true);
        mav.addObject("username", this.authService.getUser());
        transaction = this.transactionDao.findByTransactionID(TID);

        String[] requests = transaction.getDocuments().split(",");
        List<Document> documents = new ArrayList<Document>();
        float total = 0;
        for (int i = 0; i < requests.length; i++) {
            Document document = new Document();
            document = this.documentDao.findByDocumentID(Integer.parseInt(requests[i]));
            total = total + document.getPrice();
            documents.add(document);
            mav.addObject("document", documents);
        }
        mav.addObject("total", total);
        mav.addObject("TID", TID);

        return mav;
    }

    @Override
    public ModelAndView submitTransaction(String payment, String viewName) {
        ModelAndView mav = new ModelAndView(viewName);
        this.transactionDao.updateLatestTransaction(payment);

        Timestamp timestamp = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy hh:mm:ss");
            Date date = new Date();
            Date parsedDate = sdf.parse(sdf.format(date));
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            // this generic but you can control the type of exception
        }

        transaction = this.transactionDao.findByTransactionID(this.transactionDao.getLatestTransactionID());

        String[] requests = transaction.getDocuments().split(",");

        for (int i = 0; i < requests.length; i++) {
            Request request = new Request();
            request.setTID(this.transactionDao.getLatestTransactionID());
            request.setDID(Integer.parseInt(requests[i]));
            request.setUpdated_at(timestamp);
            this.requestDao.createLatestRequests(request);
        }

        return mav;
    }

    @Override
    public ModelAndView submitSpecificTransaction(String payment, int TID, String viewName) {
        ModelAndView mav = new ModelAndView(viewName);
        this.transactionDao.updateSpecificTransaction(payment, TID);

        Timestamp timestamp = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy hh:mm:ss");
            Date date = new Date();
            Date parsedDate = sdf.parse(sdf.format(date));
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            // this generic but you can control the type of exception
        }
        transaction = this.transactionDao.findByTransactionID(TID);

        String[] requests = transaction.getDocuments().split(",");
        for (int i = 0; i < requests.length; i++) {
            Request request = new Request();
            request.setTID(TID);
            request.setDID(Integer.parseInt(requests[i]));
            request.setUpdated_at(timestamp);
            this.requestDao.createRequests(request);
        }
        return mav;
    }

    @Override
    public String deleteTransaction(int TID) {
        try {
            boolean result = this.transactionDao.deleteTransaction(TID);

        } catch (Exception e) {

        }

        return "redirect:/";
    }

}
