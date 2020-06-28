package com.example.selfbook.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class userAnswer implements Parcelable {
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

    protected userAnswer(Parcel in) {
        ID = in.readInt();
        name = in.readString();
        hint = in.readString();
        answer = in.readString();
    }

    public static final Creator<userAnswer> CREATOR = new Creator<userAnswer>() {
        @Override
        public userAnswer createFromParcel(Parcel in) {
            return new userAnswer(in);
        }

        @Override
        public userAnswer[] newArray(int size) {
            return new userAnswer[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(name);
        dest.writeString(hint);
        dest.writeString(answer);
    }
}
