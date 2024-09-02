package model;

import org.json.JSONObject;

import persistence.Writable;


//represents the app User, can enter their name and the number of calories exercised
public class User implements Writable {
    private String name;
    private int calories;

    //MODIFIES:this
    //EFFECTS:constructs the user with the given name and calories
    public User(String name, int cal) {
        this.name = name;
        this.calories = cal;
        EventLog.getInstance().logEvent(new Event("Created User: " + name + " with " + cal + " calories"));
    
    }

    //MODIFIES: this
    //EFFECTS: changes the username to new name
    public void changeName(String name) {
        this.name = name;
        EventLog.getInstance().logEvent(new Event("Changed User name to: " + name));
    }
    
    //MODIFIES: this
    //EFFECTS: returns the username
    public String getName() {
        return this.name;
    }

    //MODIFIES: this
    //EFFECTS: changes the User calorie expended to something else
    public int getCalories() {
        return this.calories;
    }


    //MODIFIES: this
    //EFFECTS: updates the User calories to new
    public void changeCal(int cal) {
        this.calories = cal;
        EventLog.getInstance().logEvent(new Event("Changed User calories to: " + cal));
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("calories", calories);
        return jsonObject;
    }
}
