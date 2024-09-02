package model;



import org.json.JSONObject;

import persistence.Writable;

//represents some food it has the name and the number of calories within it
public class FoodItem implements Writable {
    String foodName;
    int calories;

    //EFFECTS: constructs a foodItem with given name and calories count
    public FoodItem(String name, int cal) {
        this.foodName = name;
        this.calories = cal;
        EventLog.getInstance().logEvent(new Event("Created FoodItem: " + name + " with " + cal + " calories"));
    }

    //EFFECTS: returns the name of the food
    public String getFoodName() {
        return foodName;
    }
    
    //EFFECTS: returns the calories
    public int getCalories() {
        return calories;
    }

    //EFFECTS: creates a JSON object representing FoodItem
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("foodName", foodName);
        jsonObject.put("calories", calories);
        return jsonObject;
    }
}


