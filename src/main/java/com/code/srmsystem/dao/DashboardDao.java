package com.code.srmsystem.dao;

import org.springframework.stereotype.Component;

import com.code.srmsystem.model.Dashboard;

@Component
public interface DashboardDao {
    public Dashboard getNumber();
}
