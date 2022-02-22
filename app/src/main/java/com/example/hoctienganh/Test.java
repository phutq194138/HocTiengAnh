package com.example.hoctienganh;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "test")
public class Test {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;
    private String hourStart;
    private String dayStart;
    private String hourEnd;
    private String dayEnd;
    private int score;
    private int correct;
    private int exp;
    private int point;
    private int star;

    public Test(String username) {
        this.username = username;
        this.hourStart = "00:00:00";
        this.dayStart = "00-00-0000";
        this.hourEnd = "00:00:00";
        this.dayEnd = "00-00-0000";
        this.score = 0;
        this.correct = 0;
        this.exp = 0;
        this.point = 0;
        this.star = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHourStart() {
        return hourStart;
    }

    public void setHourStart(String hourStart) {
        this.hourStart = hourStart;
    }

    public String getDayStart() {
        return dayStart;
    }

    public void setDayStart(String dayStart) {
        this.dayStart = dayStart;
    }

    public String getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(String hourEnd) {
        this.hourEnd = hourEnd;
    }

    public String getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(String dayEnd) {
        this.dayEnd = dayEnd;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime(){
        String start[] = hourStart.split(":");
        int hh1 = Integer.parseInt(start[0]);
        int mm1 = Integer.parseInt(start[1]);
        int ss1 = Integer.parseInt(start[2]);
        int startTime = hh1*60*60 + mm1*60 + ss1;

        String end[] = hourEnd.split(":");
        int hh2 = Integer.parseInt(end[0]);
        int mm2 = Integer.parseInt(end[1]);
        int ss2 = Integer.parseInt(end[2]);
        int endTime = hh2*60*60 + mm2*60 + ss2;

        if (endTime >= startTime){
            return endTime - startTime;
        }

        return 96;
    }
}
