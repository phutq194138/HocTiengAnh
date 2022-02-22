package com.example.hoctienganh;

public class Word {
    private String word;
    private String mean;
    private int idRes;

    public Word(String word, String mean, int idRes) {
        this.word = word;
        this.mean = mean;
        this.idRes = idRes;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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
