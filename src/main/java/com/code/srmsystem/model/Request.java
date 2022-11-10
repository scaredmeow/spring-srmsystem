package com.code.srmsystem.model;

import org.springframework.stereotype.Component;

@Component
public class Request {
    private int RID;
    private int TID;
    private int DID;
    private String updated_at;
    private String status;

    public Request() {
    }

    public Request(int RID, int TID, int DID, String updated_at, String status) {
        this.RID = RID;
        this.TID = TID;
        this.DID = DID;
        this.updated_at = updated_at;
        this.status = status;
    }

    public int getRID() {
        return RID;
    }

    public void setRID(int rID) {
        RID = rID;
    }

    public int getTID() {
        return TID;
    }

    public void setTID(int tID) {
        TID = tID;
    }

    public int getDID() {
        return DID;
    }

    public void setDID(int dID) {
        DID = dID;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Post {RID=" + RID +
                ", TID=" + TID +
                ", DID=" + DID +
                ", updated_at='" + updated_at + "\'" +
                ", status='" + status + "\'" + "}";
    }

}
