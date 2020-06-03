package com.example.selfbook.Data;

public class userTemplateInfo{

    public String userID;
    public int templateCode;
    //public String type;
    public int ChapterQuestionCode;
    public String answer;
    //public String imageURL;


    public userTemplateInfo(String userID, int templateCode, int chapterQuestionCode, String answer) {
        this.userID = userID;
        this.templateCode = templateCode;
        ChapterQuestionCode = chapterQuestionCode;
        this.answer = answer;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(int templateCode) {
        this.templateCode = templateCode;
    }

    public int getChapterQuestionCode() {
        return ChapterQuestionCode;
    }

    public void setChapterQuestionCode(int chapterQuestionCode) {
        ChapterQuestionCode = chapterQuestionCode;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
