package com.example.selfbook.Data;

public class userAnswer {
    private int ID;
    private String name;
    private String hint;
    private String answer;

    public userAnswer(int ID, String name, String hint, String answer) {
        this.ID = ID;
        this.name = name;
        this.hint = hint;
        this.answer = answer;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
