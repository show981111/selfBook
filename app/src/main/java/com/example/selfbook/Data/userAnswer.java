package com.example.selfbook.Data;

public class userAnswer {
    private int Q_ID;
    private String name;
    private int P_ID;
    private String type;
    private String hint;
    private String answer;

    public userAnswer(int q_ID, String name, int p_ID, String type, String hint, String answer) {
        Q_ID = q_ID;
        this.name = name;
        P_ID = p_ID;
        this.type = type;
        this.hint = hint;
        this.answer = answer;
    }

    public int getQ_ID() {
        return Q_ID;
    }

    public void setQ_ID(int q_ID) {
        Q_ID = q_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getP_ID() {
        return P_ID;
    }

    public void setP_ID(int p_ID) {
        P_ID = p_ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
