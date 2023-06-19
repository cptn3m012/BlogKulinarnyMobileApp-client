package com.example.blogkulinarnymobileapp.Models;
import java.util.List;

public class Category {
    public Category(int id,boolean isAccepted, String name) {
        this.id = id;
        this.isAccepted = isAccepted;
        this.name = name;
    }

    public Category(boolean isAccepted, String name) {
        this.isAccepted = isAccepted;
        this.name = name;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setRecipesCategories(List<RecipesCategory> recipesCategories) {
        this.recipesCategories = recipesCategories;
    }

    public String getName() {
        return name;
    }

    public List<RecipesCategory> getRecipesCategories() {
        return recipesCategories;
    }


    private boolean isAccepted;

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    //Relationships
    public List<RecipesCategory> recipesCategories;


}
