package com.example.blogkulinarnymobileapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Recipe implements Parcelable {

    public int id;
    public boolean isAccepted;
    public String title;
    public String imageURL;
    public String description;
    public int difficulty;
    public int avgTime;
    public int portions;

    public String author;
    public int userId;
    public User user;
    public List<RecipesCategory> recipesCategories;

    public List<RecipeElements> stepsList;
    public List<String> recipeStringCategories;


    public List<Comments> commentsList;

    public Recipe() {
    }

    public Recipe(int id, boolean isAccepted, String title, String imageURL, String description, String author,
                  int difficulty, int avgTime, int portions, int userId, User user, List<String> recipeStringCategories,
                  List<RecipesCategory> recipesCategories, List<RecipeElements> stepsList, List<Comments> commentsList) {
        this.id = id;
        this.author = author;
        this.isAccepted = isAccepted;
        this.title = title;
        this.imageURL = imageURL;
        this.description = description;
        this.difficulty = difficulty;
        this.avgTime = avgTime;
        this.portions = portions;
        this.userId = userId;
        this.user = user;
        this.recipesCategories = recipesCategories;
        this.stepsList = stepsList;
        this.commentsList = commentsList;
        this.recipeStringCategories = recipeStringCategories;

    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        isAccepted = in.readByte() != 0;
        title = in.readString();
        imageURL = in.readString();
        description = in.readString();
        difficulty = in.readInt();
        avgTime = in.readInt();
        portions = in.readInt();
        userId = in.readInt();
        stepsList = in.createTypedArrayList(RecipeElements.CREATOR);
        commentsList = in.createTypedArrayList(Comments.CREATOR);
        author = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(int avgTime) {
        this.avgTime = avgTime;
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUserFromRecipe() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<RecipesCategory> getRecipesCategories() {
        return recipesCategories;
    }

    public void setRecipesCategories(List<RecipesCategory> recipesCategories) {
        this.recipesCategories = recipesCategories;
    }

    public List<RecipeElements> getStepsList() {
        return stepsList;
    }

    public void setStepsList(List<RecipeElements> stepsList) {
        this.stepsList = stepsList;
    }

    public List<String> getRecipeStringCategories() {
        return recipeStringCategories;
    }

    public void setRecipeStringCategories(List<String> recipeStringCategories) {
        this.recipeStringCategories = recipeStringCategories;
    }

    @NonNull
    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }


    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (isAccepted ? 1 : 0));
        dest.writeString(title);
        dest.writeString(imageURL);
        dest.writeString(description);
        dest.writeInt(difficulty);
        dest.writeInt(avgTime);
        dest.writeInt(portions);
        dest.writeInt(userId);
        dest.writeTypedList(stepsList);
        dest.writeTypedList(commentsList);
        dest.writeString(author);
    }
}
