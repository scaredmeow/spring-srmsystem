package com.code.srmsystem.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class User {
    private int UID;
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String role;
    private String student_number;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String mobile_number;
    private int is_active;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(int UID, String email, String student_number, String first_name, String middle_name, String last_name,
            String mobile_number) {
        this.UID = UID;
        this.email = email;
        this.student_number = student_number;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.mobile_number = mobile_number;
    }

    public User(int UID, String username, String password, String confirmPassword, String email, String role,
            String student_number, String first_name, String middle_name, String last_name, String mobile_number,
            int is_active) {
        this.UID = UID;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.role = role;
        this.student_number = student_number;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.mobile_number = mobile_number;
        this.is_active = is_active;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    @Override
    public String toString() {
        return "User {UID=" + UID + ", is_active="
                + is_active + ", username='"
                + username + "\'" + ", password='"
                + password + "\'" + "confirmPassword='"
                + confirmPassword + "\'" + ", email='"
                + email + "\'" + ", role='"
                + role + "\'" + ", student_number='"
                + student_number + "\'" + ", first_name='"
                + first_name + "\'" + ", middle_name='"
                + middle_name + "\'" + ", last_name='"
                + last_name + "\'" + ", mobile_number='"
                + mobile_number + "\'" + "}";
    }

    public List<String> getRoleList() {
        if (this.role.length() > 0) {
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }

}
