package com.example.blogkulinarnymobileapp.Models;

import java.util.List;

public class Recipe {

    public Recipe(int id, boolean isAccepted, String title, String imageURL, String description, int difficulty, int avgTime, int portions, int userId, User user, List<RecipesCategory> recipesCategories) {
        this.id = id;
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
    }

    public int id;
    public boolean isAccepted;
    public String title;
    public String imageURL;
    public String description;
    public int difficulty;
    public int avgTime;

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

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public int portions;
    public int userId;
    public User user;

    //Relationships
    public List<RecipesCategory> recipesCategories;
}
