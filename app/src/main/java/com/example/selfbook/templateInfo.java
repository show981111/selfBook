package com.example.selfbook;

public class templateInfo {

    private int bookPrice;
    private int templateCode;
    private String author;
    private String templateName;
    private String madeDate;


    public templateInfo(int bookPrice, int templateCode, String author, String templateName, String madeDate) {
        this.bookPrice = bookPrice;
        this.templateCode = templateCode;
        this.author = author;
        this.templateName = templateName;
        this.madeDate =madeDate;
    }

    public int getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(int bookPrice) {
        this.bookPrice = bookPrice;
    }

    public int getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(int templateCode) {
        this.templateCode = templateCode;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getMadeDate() {
        return madeDate;
    }

    public void setMadeDate(String madeDate) {
        this.madeDate = madeDate;
    }
}
