package com.example.blogkulinarnymobileapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Comments implements Parcelable {
    public int id;
    public String text;
    public String login;
    public int rate;
    public int recipeId;
    public int usId;
    public int isBlocked;

    public Comments() {

    }

    public Comments(int id, String text, int rate, int recipeId, int usId, int isBlocked, String login) {
        this.id = id;
        this.text = text;
        this.rate = rate;
        this.recipeId = recipeId;
        this.usId = usId;
        this.isBlocked = isBlocked;
        this.login = login;
    }

    protected Comments(Parcel in) {
        id = in.readInt();
        text = in.readString();
        rate = in.readInt();
        recipeId = in.readInt();
        usId = in.readInt();
        isBlocked = in.readInt();
        login = in.readString();
    }

    public static final Creator<Comments> CREATOR = new Creator<Comments>() {
        @Override
        public Comments createFromParcel(Parcel in) {
            return new Comments(in);
        }

        @Override
        public Comments[] newArray(int size) {
            return new Comments[size];
        }
    };

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getUsId() {
        return usId;
    }

    public void setUsId(int usId) {
        this.usId = usId;
    }

    public int getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(int isBlocked) {
        this.isBlocked = isBlocked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text);
        dest.writeInt(rate);
        dest.writeInt(recipeId);
        dest.writeInt(usId);
        dest.writeInt(isBlocked);
        dest.writeString(login);
    }
}
