package persistence;

import model.FoodItem;
import model.FoodItems;
import model.User;
import org.json.JSONObject;


import java.io.*;

// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: writes User to JSON
    public void writeUser(User user) {
        JSONObject jsonObject = user.toJson();
        writeToFile(jsonObject);
    }

    
    // MODIFIES: this
    // EFFECTS: writes FoodItems to JSON
    public void writeFoodItems(FoodItems foodItems) {
        JSONObject jsonObject = foodItems.toJson();
        writeToFile(jsonObject);
    }

    // MODIFIES: this
    // EFFECTS: writes FoodItem to JSON
    public void writeFoodItem(FoodItem foodItem) {
        JSONObject jsonObject = foodItem.toJson();
        writeToFile(jsonObject);
    }

    // MODIFIES: this
    // EFFECTS: writes User and FoodItems to JSON
    public void writeData(User user, FoodItems foodItems) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", user.toJson());

        if (foodItems != null && !foodItems.getListofFI().isEmpty()) {
            jsonObject.put("foodItems", foodItems.toJson());
        } else {
            jsonObject.put("foodItems", JSONObject.NULL);
        }

        writeToFile(jsonObject);
    }

    // MODIFIES: this
    // EFFECTS: generic method to write JSONObject to file
    private void writeToFile(JSONObject jsonObject) {
        try (FileWriter file = new FileWriter(destination)) {
            file.write(jsonObject.toString(4)); // 4 is the indent factor for pretty printing
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
