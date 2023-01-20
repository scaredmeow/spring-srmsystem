package com.code.srmsystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.code.srmsystem.model.Request;
import com.code.srmsystem.model.User;
import com.code.srmsystem.service.AuthService;

@Repository
public class RequestDaoImpl implements RequestDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private UserDao userDao;
    private AuthService authService;

    public RequestDaoImpl(UserDao userDao, AuthService authService) {
        this.userDao = userDao;
        this.authService = authService;
    }

    @Override
    public boolean createRequests(Request request) {
        String sql = "INSERT INTO requests (TID, DID, updated_at) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, request.getTID(), request.getDID(), request.getUpdated_at()) == 1;
    }

    @Override
    public boolean createLatestRequests(Request request) {
        String sql = "INSERT INTO requests (TID, DID, updated_at) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, request.getTID(), request.getDID(),
                request.getUpdated_at()) == 1;
    }

    @Override
    public List<Request> getAllRequests() {
        String sql = "SELECT r.*, d.name, t.created_at FROM requests AS r JOIN transactions AS t ON t.TID = r.TID JOIN documents AS d ON r.DID = d.DID WHERE t.UID = ? ORDER BY r.updated_at DESC";
        User curr_user = this.userDao.findByUserName(this.authService.getUser());
        List<Request> listOfRequests = jdbcTemplate.query(sql, new RowMapper<Request>() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

            @Override
            public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
                Request request = new Request();
                request.setUpdated_at(rs.getTimestamp("updated_at"));
                request.setStatus(rs.getString("status"));
                request.setDID(rs.getInt("DID"));
                request.setTID(rs.getInt("TID"));
                request.setName(rs.getString("name"));
                String date = dateFormat.format(rs.getTimestamp("created_at"));
                request.setCreated_at(date);
                request.setRID(rs.getInt("RID"));
                request.setComment(rs.getString("comment"));
                return request;
            }
        }, new Object[] { curr_user.getUID() });
        return listOfRequests;
    }

    @Override
    public List<Request> getRecentRequests() {
        String sql = "SELECT r.*, d.name, t.created_at, u.student_number FROM requests AS r JOIN transactions AS t ON t.TID = r.TID JOIN documents AS d ON r.DID = d.DID JOIN users as u ON u.UID = t.UID WHERE NOT r.status = 'FOR PICKUP' AND NOT r.status = 'REJECTED' ORDER BY t.created_at DESC LIMIT 100";
        List<Request> listOfRequests = jdbcTemplate.query(sql, new RowMapper<Request>() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

            @Override
            public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
                Request request = new Request();
                request.setUpdated_at(rs.getTimestamp("updated_at"));
                request.setStatus(rs.getString("status"));
                request.setDID(rs.getInt("DID"));
                request.setTID(rs.getInt("TID"));
                request.setName(rs.getString("name"));
                String date = dateFormat.format(rs.getTimestamp("created_at"));
                request.setCreated_at(date);
                request.setRID(rs.getInt("RID"));
                request.setStudent_number(rs.getString("student_number"));
                return request;
            }
        });
        return listOfRequests;
    }

    @Override
    public List<Request> getSpecificRequests(String snumber) {
        String sql = "SELECT r.*, d.name, t.created_at, u.student_number FROM requests AS r JOIN transactions AS t ON t.TID = r.TID JOIN documents AS d ON r.DID = d.DID JOIN users as u ON u.UID = t.UID WHERE u.student_number = ? ORDER BY t.created_at DESC LIMIT 100";
        List<Request> listOfRequests = jdbcTemplate.query(sql, new RowMapper<Request>() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

            @Override
            public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
                Request request = new Request();
                request.setUpdated_at(rs.getTimestamp("updated_at"));
                request.setStatus(rs.getString("status"));
                request.setDID(rs.getInt("DID"));
                request.setTID(rs.getInt("TID"));
                request.setName(rs.getString("name"));
                String date = dateFormat.format(rs.getTimestamp("created_at"));
                request.setCreated_at(date);
                request.setRID(rs.getInt("RID"));
                request.setStudent_number(rs.getString("student_number"));
                return request;
            }
        }, new Object[] { snumber });
        return listOfRequests;

    }

    @Override
    public Request getRequest(int RID) {
        String sql = "SELECT r.*, t.created_at, d.name, u.student_number, t.payment FROM requests as r JOIN documents as d ON r.DID = d.DID JOIN transactions as t ON t.TID = r.TID JOIN users as u ON u.UID = t.UID WHERE r.RID = "
                + RID;
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Request.class));
    }

    @Override
    public boolean updateRequest(int RID, String method) {
        String sql = "UPDATE requests SET updated_at = ?, status = ? WHERE RID = ? LIMIT 1";

        Timestamp timestamp = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy hh:mm:ss");
            Date date = new Date();
            Date parsedDate = sdf.parse(sdf.format(date));
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            // this generic but you can control the type of exception
        }

        if (method.equals("reject")) {
            return jdbcTemplate.update(sql, timestamp, "REJECTED", RID) == 1;
        } else if (method.equals("receive")) {
            return jdbcTemplate.update(sql, timestamp, "FOR APPROVAL", RID) == 1;
        } else if (method.equals("approve")) {
            return jdbcTemplate.update(sql, timestamp, "FOR PRINTING", RID) == 1;
        } else if (method.equals("print")) {
            return jdbcTemplate.update(sql, timestamp, "FOR FINAL CHECKING", RID) == 1;
        } else if (method.equals("complete")) {
            return jdbcTemplate.update(sql, timestamp, "FOR PICKUP", RID) == 1;
        } else {
            return false;
        }
    }

}
