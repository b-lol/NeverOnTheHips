package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

//represents a list of fooditems, it also has an option to name the food item
public class FoodItems implements Writable {
    private String name;
    private ArrayList<FoodItem> listofFI;
    
    //MODIFIES:
    //EFFECTS: constructs an object FoodItems with given name and an empty list
    public FoodItems(String name) {
        this.name = name;
        this.listofFI = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Created FoodItems: " + name + " with an empty list"));
    }

    //MODIFIES: this
    //EFFECTS: adds a foodItem to a the foodItems
    public void addFood(FoodItem food) {
        listofFI.add(food);
        EventLog.getInstance().logEvent(new Event("Added food item: " + food.getFoodName()));
    }

    
    //EFFECTS: calculates the total calories from the list of fooditems in fooditem list
    public int totalCalories(FoodItems resto) {
        int totalcalories = 0;
        for (FoodItem i : resto.listofFI) {
            totalcalories += i.getCalories();
        }
        return totalcalories;
    }

    //EFFECTS: returns the name
    public String getName() {
        return name;
    }

    //MODIFIES: this
    //EFFECTS: updates the name toa  new name
    
    public void changeName(String newname) {
        this.name = newname;
        
    }

    //EFFECTS: returns the list of FoodItems
    public ArrayList<FoodItem> getListofFI() {
        return listofFI;
    }

    
    //EFFECTS: creates a JSON object representing FoodItems
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);

        JSONArray menuArray = new JSONArray();
        for (FoodItem foodItem : listofFI) {
            menuArray.put(foodItem.toJson());
        }
        jsonObject.put("menu", menuArray);

        return jsonObject;
    }
}
