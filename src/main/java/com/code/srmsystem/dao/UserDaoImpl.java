package com.code.srmsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.code.srmsystem.model.User;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User loginByEmail(String email) {
        String sql = "SELECT * FROM login WHERE email = '"
                + email + "'";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT u.*, l.password, l.role FROM users as u join login as l on l.email = u.email WHERE u.email = '"
                + email + "'";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public User findByUserID(int UID) {
        String sql = "SELECT * FROM users WHERE UID = " + UID;
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public boolean saveUserRegistration(User user) {
        String sql = "INSERT INTO login(email,password,role) VALUES(?, ?, ?)";
        if ((jdbcTemplate.update(sql,
                user.getEmail(),
                user.getPassword(),
                user.getRole()) == 1)) {
            String sql2 = "INSERT INTO users(username,email,student_number,first_name,middle_name,last_name,mobile_number) VALUES(?, ?, ?, ?, ?, ?, ?)";
            return jdbcTemplate.update(sql2,
                    user.getUsername(),
                    user.getEmail(),
                    user.getStudent_number(),
                    user.getFirst_name(),
                    user.getMiddle_name(),
                    user.getLast_name(),
                    user.getMobile_number()) == 1;
        }
        return true;
    }

    @Override
    public boolean existsEmail(String email) {
        String sql = "SELECT email FROM users WHERE email = ? LIMIT 1";

        try {
            jdbcTemplate.queryForObject(sql, String.class, email);
            return true;

        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean existsUsername(String email) {
        String sql = "SELECT email FROM users WHERE email = ? LIMIT 1";

        try {
            jdbcTemplate.queryForObject(sql, String.class, email);
            return true;

        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean existsStudentNumber(String student_number) {
        String sql = "SELECT student_number FROM users WHERE student_number = ? LIMIT 1";

        try {
            jdbcTemplate.queryForObject(sql, String.class, student_number);
            return true;

        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public User findByStudentNumber(String student_number) {
        String sql = "SELECT * FROM users WHERE student_number = '" + student_number + "'";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public boolean activateAccount(String Email) {
        String sql = "UPDATE login SET is_active = 1 WHERE email = ? LIMIT 1";
        return jdbcTemplate.update(sql, Email) == 1;
    }

}
