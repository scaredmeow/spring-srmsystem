package com.code.srmsystem.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.code.srmsystem.model.User;

@Component
public interface UserDao {
    public User loginByEmail(String email);

    public User findByEmail(String email);

    public User findByUserID(int UID);

    public User findByStudentNumber(String student_number);

    public List<User> getAllUsers();

    public boolean saveUserRegistration(User user);

    public boolean existsEmail(String email);

    public boolean existsUsername(String email);

    public boolean existsStudentNumber(String student_number);

    public boolean activateAccount(String Email);

    public boolean deleteLogin(String email);

    public boolean deleteUser(String email);

}
