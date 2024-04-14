package com.example.delicious.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Recipe {
    String id;
    private String name;
    private String preparationTime;
    private String category;
    private String description;
    private ArrayList<String> ingredients;
    private String steps;
    private String imageUrl;






    public Recipe(){

    }
    public Recipe(String id, String name,String preparationTime, String category,String description,ArrayList<String> ingredients,String steps,String imageUrl){
        this.id = id;
        this.name = name;
        this.preparationTime = preparationTime;
        this.category = category;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
        this.imageUrl = imageUrl;

    }
    // Getter method for name

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredientsList) {
        this.ingredients = ingredientsList;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getId(){
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


