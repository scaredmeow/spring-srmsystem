package com.code.srmsystem.model;

import org.springframework.stereotype.Component;

@Component
public class Dashboard {
    private int checking;
    private int approval;
    private int fincheck;
    private int printing;
    private int rejected;

    private int pickup;

    public Dashboard() {
    }

    public Dashboard(int checking, int approval, int fincheck, int printing, int rejected, int pickup) {
        this.checking = checking;
        this.approval = approval;
        this.fincheck = fincheck;
        this.printing = printing;
        this.rejected = rejected;
        this.pickup = pickup;
    }

    public int getChecking() {
        return checking;
    }

    public void setChecking(int checking) {
        this.checking = checking;
    }

    public int getApproval() {
        return approval;
    }

    public void setApproval(int approval) {
        this.approval = approval;
    }

    public int getFincheck() {
        return fincheck;
    }

    public void setFincheck(int fincheck) {
        this.fincheck = fincheck;
    }

    public int getPrinting() {
        return printing;
    }

    public void setPrinting(int printing) {
        this.printing = printing;
    }

    public int getRejected() {
        return rejected;
    }

    public void setRejected(int rejected) {
        this.rejected = rejected;
    }

    public int getPickup() {
        return pickup;
    }

    public void setPickup(int pickup) {
        this.pickup = pickup;
    }

    @Override
    public String toString() {
        return "Dashboard {checking=" + checking +
                ", approval=" + approval +
                ", fincheck=" + fincheck +
                ", printing=" + printing +
                ", rejected=" + rejected +
                ", pickup=" + pickup + "}";
    }
}
