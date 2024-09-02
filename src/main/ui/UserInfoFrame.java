package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.FoodItems;
import model.User;
import persistence.JsonWriter;

//Its a window for the User to interact and update their Userinformation
@SuppressWarnings("methodlength")
public class UserInfoFrame extends JFrame {
    private User user;
    private JLabel nameLabel;
    private JLabel caloriesLabel;
    private FoodItems foodItems;

    private static final String USER_DATA_FILE = "user_data.json";

    public UserInfoFrame(User user, FoodItems foodItems) {
        this.user = user;
        this.foodItems = foodItems;
        setTitle("User Information");
        setSize(300, 200);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 

        nameLabel = new JLabel("Name: " + user.getName());
        caloriesLabel = new JLabel("Calories: " + user.getCalories());

        JButton saveButton = new JButton("Save");
        JButton changeNameButton = new JButton("Change Name");
        JButton updateCaloriesButton = new JButton("Update Calories");
        JButton calculateFatBurnButton = new JButton("Calculate Fat Burn Rate");
        JButton createFoodTrackerButton = new JButton("Create Food Tracker");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDataToJson();
            }
        });

        changeNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeUsername();
            }
        });

        updateCaloriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUserCalories();
            }
        });

        calculateFatBurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateFatBurnRate();
            }
        });
        
        createFoodTrackerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFoodTracker();
            }
        });

        add(nameLabel);
        add(caloriesLabel);
        add(changeNameButton);
        add(updateCaloriesButton);
        add(calculateFatBurnButton);
        add(createFoodTrackerButton);
        add(saveButton);

       
    } //end of class

    //REQUIRES: userober is not null, fooditems not null, write permissions
    //MODIFIES: file system writes to the User_data_file
    //EFFECTS: saves current user and food iterms, displays confirmation dialog, shows an error if exception occurs

    private void saveDataToJson() {
        try {
            
            JsonWriter writer = new JsonWriter(USER_DATA_FILE);
           
            writer.writeData(user, foodItems);
            
            JOptionPane.showMessageDialog(this, "Data has been saved successfully");
        } catch (Exception e) {
            String errorMessage = "Error when saving: " + e.getMessage();
            String dialogTitle = "Error";

            JOptionPane.showMessageDialog(this, errorMessage, dialogTitle, JOptionPane.ERROR_MESSAGE);
        }
    }

    //REQUIRES: user object is not null, and user.getcalories is greater than 0
    //MODIFIES: userinterface, display dialoge with calculate fat burn rate
    //EFFECTS: calculate days to lose 1 lb of fat, displays msg
    private void calculateFatBurnRate() {
        final int CALORIES_PER_POUND = 3500;
        double daysToLoseOnePound = (double) CALORIES_PER_POUND / user.getCalories();

        String message = String.format(
                    "If you continue to burn %d calories per day, it will take you %.2f days to lose one pound of fat.",
                user.getCalories(),
                daysToLoseOnePound
        );
        
        JOptionPane.showMessageDialog(this, message);
    }

    //REQUIRES: user object is not null, nameLabel displays the Usern's name
    //MODIFIES: user objects name field, updates nameLabel
    //EFFECTS: prompts use to input new name, updates users name and name label,
    //display confirmation, display an error if input is invalid

    private void changeUsername() {
        String newName = JOptionPane.showInputDialog(this, "Enter new name:");
        if (newName != null && !newName.trim().isEmpty()) {
            user.changeName(newName);
            nameLabel.setText("Name: " + user.getName());
            JOptionPane.showMessageDialog(this, "Name updated successfully!");
        }  else {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //REQUIRES: user object is not null
    //MODIFIES: user objects calorie field, updates calLabel
    //EFFECTS: prompts use to input num of cals, updates users cals and calLabel,
    //display confirmation, display an error if input is invalid

    private void updateUserCalories() {
        String newCaloriesStr = JOptionPane.showInputDialog(this, "How many calories did you burn?");
        try {
            int newCalories = Integer.parseInt(newCaloriesStr);
            if (newCalories > 0) {
                user.changeCal(newCalories);
                caloriesLabel.setText("Calories: " + user.getCalories());
                JOptionPane.showMessageDialog(this, "Calories updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Calories must be a positive.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }  catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    //REQUIRES: application runnin with valid UI
    //MODIFIES: userinterface, opens a new foodtrackerframe window
    //EFFECTS: prompts user to input foodtrack name, create new foodITems object, opens foodtrackerframe
    //displays success message or an error message
    private void createFoodTracker() {
        
        String trackerName = JOptionPane.showInputDialog(this, "Enter food tracker name:");
        if (trackerName != null && !trackerName.trim().isEmpty()) {
                
            FoodItems foodTracker = new FoodItems(trackerName);
            JOptionPane.showMessageDialog(this, "Food tracker created successfully!");

            FoodTrackerFrame foodTrackerFrame = new FoodTrackerFrame(foodTracker);
            foodTrackerFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Food tracker name cant be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
