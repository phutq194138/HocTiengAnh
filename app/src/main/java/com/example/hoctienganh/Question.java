package com.example.hoctienganh;

public class Question {
    private Word question;
    private Word answer1;
    private Word answer2;
    private Word answer3;
    private Word answer4;

    public Question(Word question, Word answer1, Word answer2, Word answer3, Word answer4) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
    }

    public Question(){
    }

    public Word getQuestion() {
        return question;
    }

    public void setQuestion(Word question) {
        this.question = question;
    }

    public Word getAnswer1() {
        return answer1;
    }

    public void setAnswer1(Word answer1) {
        this.answer1 = answer1;
    }

    public Word getAnswer2() {
        return answer2;
    }

    public void setAnswer2(Word answer2) {
        this.answer2 = answer2;
    }

    public Word getAnswer3() {
        return answer3;
    }

    public void setAnswer3(Word answer3) {
        this.answer3 = answer3;
    }

    public Word getAnswer4() {
        return answer4;
    }

    public void setAnswer4(Word answer4) {
        this.answer4 = answer4;
    }
}
