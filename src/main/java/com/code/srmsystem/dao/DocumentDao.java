package com.code.srmsystem.dao;

import org.springframework.stereotype.Component;

import com.code.srmsystem.model.Document;

@Component
public interface DocumentDao {
    public Document findByDocumentID(int DID);

}
