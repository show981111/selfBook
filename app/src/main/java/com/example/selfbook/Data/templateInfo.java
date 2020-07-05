package com.example.selfbook.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class templateInfo implements viewBook, Parcelable {

    private int bookPrice;
    private int templateCode;
    private String author;
    private String templateName;
    private String madeDate;
    private String bookCover;
    private String templateIntro;


    public templateInfo(int bookPrice, int templateCode, String author, String templateName, String madeDate, String bookCover, String templateIntro) {
        this.bookPrice = bookPrice;
        this.templateCode = templateCode;
        this.author = author;
        this.templateName = templateName;
        this.madeDate =madeDate;
        this.bookCover = bookCover;
        this.templateIntro = templateIntro;
    }

    protected templateInfo(Parcel in) {
        bookPrice = in.readInt();
        templateCode = in.readInt();
        author = in.readString();
        templateName = in.readString();
        madeDate = in.readString();
        bookCover = in.readString();
        templateIntro = in.readString();
    }

    public static final Creator<templateInfo> CREATOR = new Creator<templateInfo>() {
        @Override
        public templateInfo createFromParcel(Parcel in) {
            return new templateInfo(in);
        }

        @Override
        public templateInfo[] newArray(int size) {
            return new templateInfo[size];
        }
    };

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

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public String getTemplateIntro() {
        return templateIntro;
    }

    public void setTemplateIntro(String templateIntro) {
        this.templateIntro = templateIntro;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookPrice);
        dest.writeInt(templateCode);
        dest.writeString(author);
        dest.writeString(templateName);
        dest.writeString(madeDate);
        dest.writeString(bookCover);
        dest.writeString(templateIntro);
    }
}
