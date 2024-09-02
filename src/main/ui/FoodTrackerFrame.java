package ui;

import javax.swing.*;

import model.FoodItem;
import model.FoodItems;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//Its a window to interact with the food tracker
@SuppressWarnings("methodlength")
public class FoodTrackerFrame extends JFrame {
    private FoodItems foodItems;
    private User user;

    private static final String FOOD_TRACKER_DATA_FILE = "food_tracker.json";

    public FoodTrackerFrame(FoodItems foodItems) {
        this.foodItems = foodItems;

        setTitle("Food Tracker: " + foodItems.getName());
        setSize(400, 300);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 

        JLabel trackerNameLabel = new JLabel("Tracker: " + foodItems.getName());
        JButton addFoodButton = new JButton("Add Food");
        JButton saveTrackerButton = new JButton("Save Tracker");
        JButton displayTrackerButton = new JButton("Show what I've eaten");

        add(trackerNameLabel);
        add(addFoodButton);
        add(saveTrackerButton);
        add(displayTrackerButton);
        addFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFoodItem();
            }
        });

        saveTrackerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTrackerToJson();
            }
        });

        displayTrackerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayTracker();
            }
        });
    } //end of constructor

        


    //REQUIRES:foodItems object is not null and must contain valid list of fooditems, 
    //userinterface must be in a valid state
    //MODIFIES: foodItems by adding a new foodItem
    //EFFECTS: prompts user to input foodname and calorie, 
    //creats new fooditem and adds it to the foodItems list, displays appropriate message
    private void addFoodItem() {
        String foodName = JOptionPane.showInputDialog(this, "Enter food name:");
        String caloriesStr = JOptionPane.showInputDialog(this, "Enter calories:");

        try {
            int calories = Integer.parseInt(caloriesStr);
            if (calories > 0 && foodName != null && !foodName.trim().isEmpty()) {
                FoodItem foodItem = new FoodItem(foodName, calories);
                foodItems.addFood(foodItem);
                JOptionPane.showMessageDialog(this, "Food item added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please provide valid inputs.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //REQUIRES: foodItems not null and must contain valid list of foodItem
    //MODIFIES: the file system: writes to FOOD_TRACK_DATA_FILE
    //EFFECTS: serializes fooditems list into JSON format, 
    //and writes to FOOD_TRACK_DATE_FILE, displace confirmation or error message

    private void saveTrackerToJson() {
        try {
            JsonWriter writer = new JsonWriter(FOOD_TRACKER_DATA_FILE);
            writer.writeFoodItems(foodItems);
            JOptionPane.showMessageDialog(this, "Food tracker has been saved successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Eror occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //REQUIRES:fooditems object is nto null and contains list of valid foodItem
    //MODIFIES: userinterface, opens a new display tracker window
    //EFFECTS:instantiates a new displayTrack object, 
    //displays the Display tracker window, provides user with an interface
    private void displayTracker() {
        DisplayTracker trackerDisplay = new DisplayTracker(foodItems);
        trackerDisplay.setVisible(true);
    }
}
