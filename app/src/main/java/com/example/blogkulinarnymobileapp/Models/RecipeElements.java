package com.example.blogkulinarnymobileapp.Models;

public class RecipeElements {
    public RecipeElements(int id, int noOfList, String imageURL, String description, int recipeId, Recipe recipe) {
        this.id = id;
        this.noOfList = noOfList;
        this.imageURL = imageURL;
        this.description = description;
        this.recipeId = recipeId;
        this.recipe = recipe;
    }

    public int id;

    public int noOfList;

    public String imageURL;

    public String description;

    //recipe
    public int recipeId;

    public Recipe recipe;

}
