package com.example.delicious;

import android.os.StrictMode;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public class DataService {
    private static   ArrayList<Recipe> recipes = new ArrayList<>();
    private static String apiKey = "edd56f0b1a844195b4a8971a571ea2ef";
    public static ArrayList<Recipe> getArrRecipes(String q) throws IOException{
        Random random = new Random();
        Integer randomNumber = random.nextInt(10);
        String sURL = "https://api.spoonacular.com/recipes/complexSearch?apiKey=" + apiKey +
                "&addRecipeInformation=true&instructionsRequired=true&fillIngredients=true&number=100&offset="+randomNumber.toString() +"&query=" + q;

        JsonArray resultsArray = getJsonObject(sURL).get("results").getAsJsonArray();
        return fromJsonToArr(resultsArray);
    }

    public static ArrayList<Recipe> getRandomArrRecipes() throws IOException {
        Random random = new Random();
        Integer randomNumber = random.nextInt(10);
        String sURL = "https://api.spoonacular.com/recipes/random?apiKey=" + apiKey + "&addRecipeInformation=true&instructionsRequired=true&fillIngredients=true&number=12&offset=" + randomNumber.toString();
        JsonArray resultsArray = getJsonObject(sURL).get("recipes").getAsJsonArray();
        return fromJsonToArr(resultsArray);
    }

    private static JsonObject getJsonObject(String sURL) throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = new URL(sURL);
        HttpURLConnection request = (HttpsURLConnection) url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        return root.getAsJsonObject();
    }
    private static ArrayList<Recipe> fromJsonToArr(JsonArray resultsArray){
        recipes.clear();
        for (JsonElement result : resultsArray) {
            JsonObject recipeObj = result.getAsJsonObject();
            String name = recipeObj.get("title").getAsString();
            if (name == null || name.isEmpty()) {
                name = "Title not included";
            }
            String totalTime = recipeObj.get("readyInMinutes").getAsString();
            if (totalTime == null || totalTime.isEmpty()) {
                totalTime = "Preparation time not included";
            }
            else
                totalTime= totalTime + " min";
            JsonArray categoryArr = recipeObj.getAsJsonArray("dishTypes");
            String category = "Category not included";
            if (categoryArr != null && categoryArr.size() > 0) {
                JsonElement categoryElement = categoryArr.get(0);
                if (categoryElement != null && !categoryElement.isJsonNull()) {
                    category = categoryElement.getAsString();
                }
            }
            String description = recipeObj.get("summary").getAsString();
            if (description == null || description.isEmpty()) {
                description = "Description not included";
            }
            String imageUrl = recipeObj.get("image").getAsString();
            if (imageUrl == null || imageUrl.isEmpty()) {
                imageUrl = "android.resource://com.package com.example.delicious/" + R.drawable.image_missing;

            }
            ArrayList<String> ingredientsList = new ArrayList<>();
            JsonArray ingredientsArray = recipeObj.get("extendedIngredients").getAsJsonArray();
            if (ingredientsArray == null || ingredientsArray.size()==0) {
                ingredientsList.add("Ingredients is missing");

            }
            else {
                for (JsonElement ingredient : ingredientsArray) {
                    String ingredientName = ingredient.getAsJsonObject().get("name").getAsString();
                    ingredientsList.add(ingredientName);
                }
            }

            JsonArray analyzedInstructionsArray = recipeObj.getAsJsonArray("analyzedInstructions");
            String instructions = "Instructions not includeג";
            if (analyzedInstructionsArray != null && analyzedInstructionsArray.size() > 0) {
                JsonObject firstInstruction = analyzedInstructionsArray.get(0).getAsJsonObject();
                JsonArray stepsArray = firstInstruction.getAsJsonArray("steps");
                if (stepsArray != null && stepsArray.size() > 0) {
                    StringBuilder instructionsBuilder = new StringBuilder();
                    for (JsonElement step : stepsArray) {
                        JsonObject stepObj = step.getAsJsonObject();
                        String stepText = stepObj.get("step").getAsString();
                        instructionsBuilder.append(stepText).append("\n");
                    }
                    instructions = instructionsBuilder.toString();
                }
            }

            Recipe recipe = new Recipe(name, totalTime, category, description, ingredientsList, instructions, imageUrl);
            recipes.add(recipe);
        }

        return recipes;

    }

}
