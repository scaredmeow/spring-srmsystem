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
    private String student_number;
    private String payment;
    private String comment;
    private String assigned_employee;

    public String getAssigned_employee() {
        return assigned_employee;
    }

    public void setAssigned_employee(String assigned_employee) {
        this.assigned_employee = assigned_employee;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Request() {
    }

    public Request(int RID, int TID, int DID, Timestamp updated_at, String status, String name, String created_at,
            String student_number, String payment, String comment, String assigned_employee) {
        this.RID = RID;
        this.TID = TID;
        this.DID = DID;
        this.updated_at = updated_at;
        this.status = status;
        this.name = name;
        this.created_at = created_at;
        this.student_number = student_number;
        this.payment = payment;
        this.comment = comment;
        this.assigned_employee = assigned_employee;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Post {RID=" + RID +
                ", TID=" + TID +
                ", DID=" + DID +
                ", updated_at='" + updated_at + "\'" +
                ", status='" + status + "\'" +
                ", name='" + name + "\'" +
                ", comment='" + comment + "\'" +
                ", created_at='" + created_at +
                ", assigned_employee='" + assigned_employee + "\'" + "}";
    }

}
