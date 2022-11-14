package com.code.srmsystem.model;

import org.springframework.stereotype.Component;

@Component
public class Transaction {
    private int TID;
    private int UID;
    private String documents;
    private String payment;
    private String status;
    private String created_at;

    public Transaction() {
    }

    public Transaction(int TID, int UID, String documents, String payment, String status, String created_at) {
        this.TID = TID;
        this.UID = UID;
        this.documents = documents;
        this.payment = payment;
        this.status = status;
        this.created_at = created_at;
    }

    public int getTID() {
        return TID;
    }

    public void setTID(int TID) {
        this.TID = TID;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Post {TID=" + TID +
                ", UID=" + UID +
                ", documents='" + documents + "\'" +
                ", payment='" + payment + "\'" +
                ", status='" + status + "\'" +
                ", created_at='" + created_at + "\'" + "}";
    }
}
