package com.example.selfbook.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class Content implements Parcelable {
    private int ID;
    private String name;
    private String hint;
    private String answer;
    private int status;


    public Content(int ID, String name, String hint, String answer, int status) {
        this.ID = ID;
        this.name = name;
        this.hint = hint;
        this.answer = answer;
        this.status = status;
    }

    protected Content(Parcel in) {
        ID = in.readInt();
        name = in.readString();
        hint = in.readString();
        answer = in.readString();
        status = in.readInt();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static final Creator<Content> CREATOR = new Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
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
        dest.writeInt(status);
    }
}
