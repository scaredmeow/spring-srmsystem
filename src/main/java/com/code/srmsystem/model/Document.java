package com.code.srmsystem.model;

import org.springframework.stereotype.Component;

@Component
public class Document {
    private int DID;
    private float price;
    private String name;
    private String acronym;
    private String type;

    public Document() {
    }

    public Document(int DID, float price, String name, String acronym, String type) {
        this.DID = DID;
        this.price = price;
        this.name = name;
        this.acronym = acronym;
        this.type = type;
    }

    public int getDID() {
        return DID;
    }

    public void setDID(int dID) {
        DID = dID;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Document {DID=" + DID +
                ", price=" + price +
                ", name='" + name + "\'" +
                ", acronym='" + acronym + "\'" +
                ", type='" + type + "\'" + "}";
    }

}
