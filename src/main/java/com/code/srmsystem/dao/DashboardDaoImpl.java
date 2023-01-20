package com.code.srmsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.code.srmsystem.model.Dashboard;

@Repository
public class DashboardDaoImpl implements DashboardDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Dashboard dashboard;

    @Override
    public Dashboard getNumber() {

        int check = (int) jdbcTemplate.queryForObject("SELECT COUNT(*) FROM requests WHERE status = ?",
                int.class, "FOR CHECKING");
        int approve = (int) jdbcTemplate.queryForObject("SELECT COUNT(*) FROM requests WHERE status = ?",
                int.class, "FOR APPROVAL");
        int printing = (int) jdbcTemplate.queryForObject("SELECT COUNT(*) FROM requests WHERE status = ?",
                int.class, "FOR PRINTING");
        int fincheck = (int) jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM requests WHERE status = ?",
                int.class, "FOR FINAL CHECKING");
        int pickup = (int) jdbcTemplate.queryForObject("SELECT COUNT(*) FROM requests WHERE status = ?",
                int.class, "FOR PICKUP");
        int reject = (int) jdbcTemplate.queryForObject("SELECT COUNT(*) FROM requests WHERE status = ?",
                int.class, "REJECTED");

        dashboard.setChecking(check);
        dashboard.setApproval(approve);
        dashboard.setFincheck(fincheck);
        dashboard.setPrinting(printing);
        dashboard.setRejected(reject);
        dashboard.setPickup(pickup);
        return dashboard;
    }

}
