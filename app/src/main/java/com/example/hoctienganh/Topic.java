package com.example.hoctienganh;

public class Topic {
    private String name;
    private String mean;
    private int idRes;

    public Topic(String name, String mean, int idRes) {
        this.name = name;
        this.mean = mean;
        this.idRes = idRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public int getIdRes() {
        return idRes;
    }

    public void setIdRes(int idRes) {
        this.idRes = idRes;
    }
}
