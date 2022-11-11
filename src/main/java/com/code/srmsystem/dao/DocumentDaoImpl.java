package com.code.srmsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.code.srmsystem.model.Document;

@Repository
public class DocumentDaoImpl implements DocumentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Document findByDocumentID(int DID) {
        String sql = "SELECT * FROM documents WHERE DID = " + DID;
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Document.class));
    }

}
