package com.example.selfbook.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class userInfo implements Parcelable, viewBook {
    //public String status;
    //public String ImageUrl;
    public String userID;
    public String userName;
    public int userTemplateCode;
    public String userBookName;
    public String userBookPublishDate;
    public String userBookCover;
    public userInfo(String userID, String userName, int userTemplateCode, String userBookName, String userBookPublishDate, String userBookCover) {
        this.userID = userID;
        this.userName = userName;
        this.userTemplateCode = userTemplateCode;
        this.userBookName = userBookName;
        this.userBookPublishDate = userBookPublishDate;
        this.userBookCover = userBookCover;
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

//    public String getImageUrl() {
//        return ImageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        ImageUrl = imageUrl;
//    }

    protected userInfo(Parcel in) {
        this.userID = in.readString();
        this.userName = in.readString();
        this.userTemplateCode = in.readInt();
        this.userBookName = in.readString();
        this.userBookPublishDate = in.readString();
        this.userBookCover = in.readString();
    }

    public static final Creator<userInfo> CREATOR = new Creator<userInfo>() {
        @Override
        public userInfo createFromParcel(Parcel in) {
            return new userInfo(in);
        }

        @Override
        public userInfo[] newArray(int size) {
            return new userInfo[size];
        }
    };

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userEmail) {
        this.userID = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserTemplateCode() {
        return userTemplateCode;
    }

    public void setUserTemplateCode(int userTemplateCode) {
        this.userTemplateCode = userTemplateCode;
    }
    public String getUserBookName() {
        return userBookName;
    }

    public void setUserBookName(String userBookName) {
        this.userBookName = userBookName;
    }

    public String getUserBookPublishDate() {
        return userBookPublishDate;
    }

    public void setUserBookPublishDate(String userBookPublishDate) {
        this.userBookPublishDate = userBookPublishDate;
    }

    public String getUserBookCover() {
        return userBookCover;
    }

    public void setUserBookCover(String userBookCover) {
        this.userBookCover = userBookCover;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userID);
        dest.writeString(this.userName);
        dest.writeInt(this.userTemplateCode);
        dest.writeString(this.userBookName);
        dest.writeString(this.userBookPublishDate);
        dest.writeString(this.userBookCover);
    }
}
