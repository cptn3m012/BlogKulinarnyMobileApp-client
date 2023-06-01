package com.example.blogkulinarnymobileapp.Models;
import java.util.List;

public class Category {
    public Category(int id, String name, List<RecipesCategory> recipesCategories) {
        this.id = id;
        this.name = name;
        this.recipesCategories = recipesCategories;
    }

    public int id;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<RecipesCategory> getRecipesCategories() {
        return recipesCategories;
    }

    public String name;
    //Relationships
    public List<RecipesCategory> recipesCategories;
}
