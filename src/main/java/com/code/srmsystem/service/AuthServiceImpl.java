package com.code.srmsystem.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.code.srmsystem.MailUtil;
import com.code.srmsystem.dao.UserDao;
import com.code.srmsystem.model.User;
import com.code.srmsystem.security.UserAccount;

import java.sql.Connection;
import java.sql.DriverManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.view.JasperViewer;

@Service
public class AuthServiceImpl implements AuthService {

    String secretKey = System.getenv("SECRET_KEY");

    @Autowired
    private User user;

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;
    private EncryptionAndDecryptionService authEncryptDecrypt;

    public AuthServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder,
            EncryptionAndDecryptionService authEncryptDecrypt) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.authEncryptDecrypt = authEncryptDecrypt;
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
                user.setIs_active(0);
                userDao.saveUserRegistration(user);

                model.asMap().clear();
                ModelAndView mav = new ModelAndView("signup");
                mav.addObject("activate", true);
                Random rand = new Random();
                Integer randSixKey = rand.nextInt(999999);
                String hashedlink = this.authEncryptDecrypt.encrypt(email + randSixKey, secretKey);
                MailUtil.sendMail(email, hashedlink);
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
    public String redirect(String viewName, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String date = this.getDate();
        model.addAttribute("date", date);
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return viewName;
        }

        return "redirect:/requests";
    }

    @Override
    public String requestRedirect() {
        user = this.userDao.loginByEmail(this.getUser());
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
        user = this.userDao.findByEmail(this.getUser());
        mav.addObject("username", user.getLast_name());
        String date = this.getDate();
        mav.addObject("date", date);
        return mav;
    }

    @Override
    public String getDate() {
        LocalDate localDate = LocalDate.now(ZoneId.of("GMT+08:00"));
        String pattern = "EEEEE, MMMM dd, yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat
                .format(java.util.Date.from(localDate.atStartOfDay(ZoneId.of("GMT+08:00")).toInstant()));
        return date;
    }

    @Override
    public String redirectSpecial(String viewName, String hashedkey, Model model) {
        String hashed = this.authEncryptDecrypt.decrypt(hashedkey, secretKey);
        model.addAttribute("activate", true);
        String date = this.getDate();
        model.addAttribute("date", date);
        this.userDao.activateAccount(hashed.substring(0, hashed.length() - 6));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return viewName;
        }

        return "redirect:/requests";

    }

    @Override
    public String generateReport() {
        Map<String, Object> map = new HashMap<>();
        map.put("Parameter1", "11");
        map.put("Parameter2", "2022");
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/srmsystem", "root", "admin");
            String reportPath = "C:\\Users\\Neilc\\dev\\commission\\spring-srmsystem\\src\\main\\resources\\reports\\frequent_document_request.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(reportPath);
            JasperPrint jp = JasperFillManager.fillReport(jr, map, con);
            JasperViewer.viewReport(jp);
            con.close();
            return "redirect:/";
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "redirect:/";
    }
}
