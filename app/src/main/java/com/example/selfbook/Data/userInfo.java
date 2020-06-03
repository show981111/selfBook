package com.example.selfbook.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class userInfo implements Parcelable, viewBook {
    //public String status;
    //public String ImageUrl;
    public String userID;
    public String userName;
    public int userTemplateCode;

    public userInfo(String userID, String userName, int userTemplateCode) {
        this.userID = userID;
        this.userName = userName;
        this.userTemplateCode=userTemplateCode;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userID);
        dest.writeString(this.userName);
        dest.writeInt(this.userTemplateCode);
    }
}
