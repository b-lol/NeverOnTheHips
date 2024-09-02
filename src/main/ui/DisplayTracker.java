package ui;

import javax.swing.*;
import model.FoodItem;
import model.FoodItems;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//Its a window to display and interact with the FoodItems in the food tracker
@SuppressWarnings("methodlength")
public class DisplayTracker extends JFrame {
    private FoodItems foodItems;
    
    private JTextArea foodItemsArea;

    public DisplayTracker(FoodItems foodItems) {
        this.foodItems = foodItems;
        setTitle("Food Items Viewer");
        setSize(400, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 

        foodItemsArea = new JTextArea();
        foodItemsArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(foodItemsArea);
        add(scrollPane, BorderLayout.CENTER);

        updateFoodItemsDisplay();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton sortByNameButton = new JButton("Sort by Name");
        sortByNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortByName();
            }
        });
        buttonPanel.add(sortByNameButton);
     
        JButton sortByCaloriesButton = new JButton("Sort by Calories");
        sortByCaloriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortByCalories();
            }
        });
        buttonPanel.add(sortByCaloriesButton);
     
        JButton removeSpecificFoodButton = new JButton("Remove Food");
        removeSpecificFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSpecificFood();
            }
        });
        buttonPanel.add(removeSpecificFoodButton);
        add(buttonPanel, BorderLayout.SOUTH);
    } //end of constructor

    //REQUIRES: fooditems not null and contain a valid list of food
    //MODIFIES: foodItemsArea: clears and updates the text
    //EFFECTS: clears fooditerms area, appends name and calories of each food item, displays update list
    private void updateFoodItemsDisplay() {
        foodItemsArea.setText(""); // Clear the text area

        for (FoodItem item : foodItems.getListofFI()) {
            foodItemsArea.append("Name: " + item.getFoodName() + ", Calories: " + item.getCalories() + "\n");
        }
    }

    //REQUIRES: fooditems not null and contain a valid list of food
    //MODIFIES: order of FoodItem in the FoodItem.getMenu list
    //EFFECTS: sorts food items objects alphabetically, refreshes the display to reflect
    private void sortByName() {
        ArrayList<FoodItem> menu = foodItems.getListofFI();
        Collections.sort(menu, Comparator.comparing(FoodItem::getFoodName));
        updateFoodItemsDisplay(); // Refresh the display
    }

    //REQUIRES: fooditems not null and contain a valid list of food
    //MODIFIES: order of FoodItem in the FoodItem.getMenu list
    //EFFECTS: sorts food items objects calorically, refreshes the display to reflect
    private void sortByCalories() {
        ArrayList<FoodItem> menu = foodItems.getListofFI();
        Collections.sort(menu, Comparator.comparingInt(FoodItem::getCalories));
        updateFoodItemsDisplay(); // Refresh the display
    }

    //REQUIRES: fooditems not null and contain a valid list of food
    //MODIFIES:foodItems.getMenu() list be removing specific food item, user interface updates 
    //EFFECTS:prompts user to input name of food, removes specified fooditem, 
    //refreshes display, displays appropriate messsage

    private void removeSpecificFood() {
        String foodName = JOptionPane.showInputDialog(this, "Enter the name of the food to remove:");

        if (foodName != null && !foodName.trim().isEmpty()) {
            boolean removed = false;
            ArrayList<FoodItem> menu = foodItems.getListofFI();

            // Iterate over the list and remove the item if found
            for (int i = 0; i < menu.size(); i++) {
                if (menu.get(i).getFoodName().equalsIgnoreCase(foodName.trim())) {
                    menu.remove(i);
                    removed = true;
                    break;
                }
            }

            // Update the display and notify the user
            if (removed) {
                updateFoodItemsDisplay();
                JOptionPane.showMessageDialog(this, "Food item removed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Food item not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a valid food name.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }  
}