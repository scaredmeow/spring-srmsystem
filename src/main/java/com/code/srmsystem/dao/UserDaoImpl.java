package com.code.srmsystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT u.* FROM users as u join login as l on l.email = u.email WHERE l.role = 'EMPLOYEE'";

        List<User> listOfUsers = jdbcTemplate.query(sql, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setUID(rs.getInt("UID"));
                user.setStudent_number(rs.getString("student_number"));
                user.setFirst_name(rs.getString("first_name"));
                user.setMiddle_name(rs.getString("middle_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setMobile_number(rs.getString("mobile_number"));
                user.setUsername(rs.getString("username"));
                return user;
            }
        });
        return listOfUsers;
    }

    @Override
    public boolean deleteLogin(String email) {
        String sql = "DELETE FROM login WHERE email = ?";
        Object[] args = new Object[] { email };
        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public boolean deleteUser(String email) {
        String sql = "DELETE FROM users WHERE email = ?";
        Object[] args = new Object[] { email };
        return jdbcTemplate.update(sql, args) == 1;
    }

}
