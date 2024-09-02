package persistence;

import model.FoodItem;
import model.FoodItems;
import model.User;

import java.io.FileReader;
import java.io.IOException;


import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    
    
    // EFFECTS: reads user from file and returns it;
    // throws IOException if an error occurs reading data from file
    // Read User from JSON
    public User readUser() {
        JSONObject jsonObject = readFromFile();
        String name = jsonObject.getString("name");
        int calories = jsonObject.getInt("calories");
        return new User(name, calories);
    }

   
    
    // MODIFIES: Userinfo
    // EFFECTS: reads FoodItems from JSON object and returns it
    public  FoodItems readFoodItems() {
        JSONObject jsonObject = readFromFile();
        String name = jsonObject.getString("name");
        JSONArray menuArray = jsonObject.getJSONArray("menu");
        
        FoodItems foodItems = new FoodItems(name);
        for (int i = 0; i < menuArray.length(); i++) {
            JSONObject foodJson = menuArray.getJSONObject(i);
            FoodItem foodItem = new FoodItem(foodJson.getString("foodName"), foodJson.getInt("calories"));
            foodItems.addFood(foodItem);
        }
        return foodItems;
    }


    // MODIFIES: UserInfo
    // EFFECTS: reads FoodItem from JSON object and adds it to Userinfo
    private  FoodItem readFoodItem() {
        JSONObject jsonObject = readFromFile();
        String foodName = jsonObject.getString("foodName");
        int calories = jsonObject.getInt("calories");
        return new FoodItem(foodName, calories);
    }



    // EFFECTS: reads User and FoodItems from file and returns them as an array;
    // throws IOException if an error occurs reading data from file
    public Object[] readData() {
        JSONObject jsonObject = readFromFile();
        String userName = jsonObject.getJSONObject("user").getString("name");
        int userCalories = jsonObject.getJSONObject("user").getInt("calories");
        User user = new User(userName, userCalories);

        FoodItems foodItems = null;
        if (!jsonObject.isNull("foodItems")) {
            JSONObject foodItemsJson = jsonObject.getJSONObject("foodItems");
            String foodItemsName = foodItemsJson.getString("name");
            JSONArray menuArray = foodItemsJson.getJSONArray("menu");

            foodItems = new FoodItems(foodItemsName);
            for (int i = 0; i < menuArray.length(); i++) {
                JSONObject foodJson = menuArray.getJSONObject(i);
                FoodItem foodItem = new FoodItem(foodJson.getString("foodName"), foodJson.getInt("calories"));
                foodItems.addFood(foodItem);
            }
        }

        return new Object[]{user, foodItems};
    }


    // EFFECTS: reads JSONObject from file
    private JSONObject readFromFile() {
        StringBuilder jsonString = new StringBuilder();
        try (FileReader reader = new FileReader(source)) {
            int i;
            while ((i = reader.read()) != -1) {
                jsonString.append((char) i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject(jsonString.toString());
    }
}
