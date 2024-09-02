package ui;

import java.awt.*;
import javax.swing.*;

import model.FoodItems;
import model.User;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.event.*;
import java.io.File;

@SuppressWarnings("methodlength")
public class App extends JFrame {
    private JLabel directionsLabel = new JLabel("Click Load to load previous user information");
    private JLabel directionsLabel2 = new JLabel("Click New to create a user");
    private JLabel directionsLabel3 = new JLabel("Click Load to load food tracker");
    private JLabel directionsLabel4 = new JLabel("Click New to create a new food tracker");
    private JButton loadButton = new JButton("Load Userinfo");
    private JButton newButton = new JButton("New Userinfo");
    private JButton loadFoodTrackerButton = new JButton("Load Food Tracker");
    private JButton newFoodTrackerButton = new JButton("New Food Tracker");

    private static final String USER_DATA_FILE = "user_data.json";

    public App() {
        setTitle("Never on the Hips");
        setSize(500, 600);
        setMinimumSize(new Dimension(300, 400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        String imgPath = "lib/images.jpg";
        ImageIcon imageIcon = new ImageIcon(imgPath);
        JLabel imageLabel = new JLabel(imageIcon);

        setLayout(new BorderLayout());

        add(imageLabel, BorderLayout.NORTH);

        JPanel labelPanel = new JPanel(new GridLayout(4, 1));
        labelPanel.add(directionsLabel);
        labelPanel.add(directionsLabel2);
        labelPanel.add(directionsLabel3);
        labelPanel.add(directionsLabel4);

        add(labelPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.add(loadButton);
        buttonPanel.add(newButton);
        buttonPanel.add(loadFoodTrackerButton);
        buttonPanel.add(newFoodTrackerButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadButtonClick(e);
            }
        });

        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newButtonClick(e);
            }
        });

        loadFoodTrackerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadFoodTrackerButtonClick(e);
            }
        });

        newFoodTrackerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newFoodTrackerButtonClick(e);
            }
        });

        setLocationRelativeTo(null);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            displayEvents();
        }));

    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App window = new App();
            window.setVisible(true);
        });
    }

    //EFFECTS: Displays the logged events when the app is existed
    private void displayEvents() {
        EventLog eventLog = EventLog.getInstance();
        System.out.println("Logged Events:");
        for (model.Event event : eventLog) {
            System.out.println(event);
        }
    }

    // REQUIRES: actionEvent e is not null, user_data_file is readable and accessible
    // MODIFIES: opens a new UserinfoFrame window displaying Userinfo
    // EFFECTS: displays loaded user data in Userinfoframe window, loads the userinfo data,
    // displays error msg if file dne

    public void loadButtonClick(ActionEvent e) {
        //EventLog.getInstance().logEvent(new Event("Load Userinfo button clicked"));
        try {
            File file = new File(USER_DATA_FILE);
            if (!file.exists()) {
                JOptionPane.showMessageDialog(this, "No saved user data found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JsonReader reader = new JsonReader(USER_DATA_FILE);
            User loadedUser = reader.readUser();

            // Create and show the UserInfoFrame with the loaded user
            UserInfoFrame userInfoFrame = new UserInfoFrame(loadedUser, null);
            userInfoFrame.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load user data.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // REQUIRES: actionEvent e is not null, userinput is available, application must have write permissions
    // MODIFIES: creates/overwrites user_data.json file
    // EFFECTS: opens a Userinfoframe, displays user information

    public void newButtonClick(ActionEvent e) {
        //EventLog.getInstance().logEvent(new Event("New Userinfo button clicked"));
        String name = JOptionPane.showInputDialog(this, "Enter your name:");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.");
            return;
        }

        String caloriesStr = JOptionPane.showInputDialog(this, "Enter how many calories you burned:");
        int calories;
        try {
            calories = Integer.parseInt(caloriesStr);
            if (calories <= 0) {
                JOptionPane.showMessageDialog(this, "Calories must be a positive number.");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for calories.");
            return;
        }
        User newUser = new User(name, calories);

        JsonWriter writer = new JsonWriter(USER_DATA_FILE);
        writer.writeUser(newUser);
        JOptionPane.showMessageDialog(this, "User data saved successfully!");

        UserInfoFrame userInfoFrame = new UserInfoFrame(newUser, null);
        userInfoFrame.setVisible(true);
    }

    // REQUIRES: actionEvent e is not null, food_tracker.json is readable
    // MODIFIES: opens a new foodtracker window
    // EFFECTS: opens a newFoodtrackerframe, loads the fooditems data, displays error msg if file dne

    public void loadFoodTrackerButtonClick(ActionEvent e) {
        //EventLog.getInstance().logEvent(new Event("Load Food Tracker button clicked"));
        try {
            File file = new File("food_tracker.json");
            if (!file.exists()) {
                String message = "No saved food tracker data found.";
                String title = "Error";

// Display the message dialog
                JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
                return;
            }

            JsonReader reader = new JsonReader("food_tracker.json");
            FoodItems loadedFoodItems = reader.readFoodItems();

            FoodTrackerFrame foodTrackerFrame = new FoodTrackerFrame(loadedFoodItems);
            foodTrackerFrame.setVisible(true);

        } catch (Exception ex) {
            String message = "Failed to load food tracker data.";
            String title = "Error";
            JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // REQUIRES: actionEvent e is not null
    // MODIFIES: fileSystem: create/overwrites food_tracker.json with new data, opens a new foodtracker window
    // EFFECTS: new fooditems object is created, new foodtracker window is opened, displays appropriate msgs
    public void newFoodTrackerButtonClick(ActionEvent e) {
        //EventLog.getInstance().logEvent(new Event("New Food Tracker button clicked"));
        // Prompt the user for food tracker details
        String name = JOptionPane.showInputDialog(this, "Enter tracker name:");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tracker name cannot be empty.");
            return;
        }

        FoodItems newFoodItems = new FoodItems(name);

        JsonWriter writer = new JsonWriter("food_tracker.json");
        writer.writeFoodItems(newFoodItems);
        JOptionPane.showMessageDialog(this, "Food tracker data saved successfully!");

        FoodTrackerFrame foodTrackerFrame = new FoodTrackerFrame(newFoodItems);
        foodTrackerFrame.setVisible(true);
    }
}