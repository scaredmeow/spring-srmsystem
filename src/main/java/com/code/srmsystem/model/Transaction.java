package com.code.srmsystem.model;

import org.springframework.stereotype.Component;

@Component
public class Transaction {
    private int TID;
    private int UID;
    private String status;
    private String created_at;

    public Transaction() {
    }

    public Transaction(int TID, int UID, String status, String created_at) {
        this.TID = TID;
        this.UID = UID;
        this.status = status;
        this.created_at = created_at;
    }

    public int getTID() {
        return TID;
    }

    public void setTID(int tID) {
        TID = tID;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int uID) {
        UID = uID;
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
                ", status='" + status + "\'" +
                ", created_at='" + created_at + "\'" + "}";
    }
}
