package com.example.blogkulinarnymobileapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeElements implements Parcelable {

    public int id;
    public int noOfList;
    public String imageURL;
    public String description;
    public int recipeId;
    public Recipe recipe;

    public RecipeElements() {
    }

    public RecipeElements(int id, int noOfList, String imageURL, String description, int recipeId, Recipe recipe) {
        this.id = id;
        this.noOfList = noOfList;
        this.imageURL = imageURL;
        this.description = description;
        this.recipeId = recipeId;
        this.recipe = recipe;
    }

    protected RecipeElements(Parcel in) {
        id = in.readInt();
        noOfList = in.readInt();
        imageURL = in.readString();
        description = in.readString();
        recipeId = in.readInt();
        recipe = in.readParcelable(Recipe.class.getClassLoader());
    }

    public static final Creator<RecipeElements> CREATOR = new Creator<RecipeElements>() {
        @Override
        public RecipeElements createFromParcel(Parcel in) {
            return new RecipeElements(in);
        }

        @Override
        public RecipeElements[] newArray(int size) {
            return new RecipeElements[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getNoOfList() {
        return noOfList;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNoOfList(int noOfList) {
        this.noOfList = noOfList;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(noOfList);
        dest.writeString(imageURL);
        dest.writeString(description);
        dest.writeInt(recipeId);
        dest.writeParcelable(recipe, flags);
    }
}