package com.example.blogkulinarnymobileapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Comment implements Parcelable {
    public int id;
    public String text;
    public int rate;
    public int recipeId;
    public int userId;
    public int isBlocked;

    List<Comment> commentsList;

    public Comment() {
    }

    public Comment(int id, String text, int rate, int recipeId, int userId, int isBlocked) {
        this.id = id;
        this.text = text;
        this.rate = rate;
        this.recipeId = recipeId;
        this.userId = userId;
        this.isBlocked = isBlocked;
    }

    protected Comment(Parcel in) {
        id = in.readInt();
        text = in.readString();
        rate = in.readInt();
        recipeId = in.readInt();
        userId = in.readInt();
        isBlocked = in.readInt();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(int isBlocked) {
        this.isBlocked = isBlocked;
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comment> commentsList) {
        this.commentsList = commentsList;
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
        dest.writeInt(userId);
        dest.writeInt(isBlocked);
    }
}
