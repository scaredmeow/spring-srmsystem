package com.code.srmsystem.dao;

import org.springframework.stereotype.Component;

import com.code.srmsystem.model.User;

@Component
public interface UserDao {

    public User loginByUserName(String username);

    public User findByUserName(String username);

    public User findByUserID(int UID);

    public boolean saveUserRegistration(User user);

    public boolean existsEmail(String email);

    public boolean existsUsername(String username);

    public boolean existsStudentNumber(String student_number);
}
