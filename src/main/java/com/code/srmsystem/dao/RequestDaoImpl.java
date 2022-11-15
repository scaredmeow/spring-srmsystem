package com.code.srmsystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
                return request;
            }
        }, new Object[] { curr_user.getUID() });
        return listOfRequests;
    }

}
