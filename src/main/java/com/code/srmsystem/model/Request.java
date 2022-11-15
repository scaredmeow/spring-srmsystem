package com.code.srmsystem.model;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@Component
public class Request {
    private int RID;
    private int TID;
    private int DID;
    private Timestamp updated_at;
    private String status;
    private String name;
    private String created_at;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Request() {
    }

    public Request(int RID, int TID, int DID, Timestamp updated_at, String status, String name, String created_at) {
        this.RID = RID;
        this.TID = TID;
        this.DID = DID;
        this.updated_at = updated_at;
        this.status = status;
        this.name = name;
        this.created_at = created_at;
    }

    public int getRID() {
        return RID;
    }

    public void setRID(int RID) {
        this.RID = RID;
    }

    public int getTID() {
        return TID;
    }

    public void setTID(int TID) {
        this.TID = TID;
    }

    public int getDID() {
        return DID;
    }

    public void setDID(int DID) {
        this.DID = DID;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Post {RID=" + RID +
                ", TID=" + TID +
                ", DID=" + DID +
                ", updated_at='" + updated_at + "\'" +
                ", status='" + status + "\'" +
                ", name='" + name + "\'" +
                ", created_at='" + created_at + "\'" + "}";
    }

}
