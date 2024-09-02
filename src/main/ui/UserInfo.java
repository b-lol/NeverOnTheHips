package ui;

import java.util.Scanner;
import persistence.JsonReader;
import persistence.JsonWriter;

import model.FoodItem;
import model.FoodItems;
import model.User;

//NutritionUser application
public class UserInfo {
    private static User currentUser;
    private Scanner response; 
   
    private static FoodItems foodtracker = new FoodItems("");


    //Effects Runs the getUserinfo
    public UserInfo() {
        getUserInfo();
        userActions();

    }

    //Modifies: this
    //Effects: processes user info
    @SuppressWarnings("methodlength")
    public void getUserInfo() {
        boolean nameunk = true;
        boolean calunk = true;
        String username = "";
        int usercal = 0;
        


        int currentCalories;

        while (nameunk) {
            startPrompt();
            response = new Scanner(System.in);
            String startresponse = response.nextLine();
            if (startresponse.equals("q")) {
                System.exit(0);
            } else if (startresponse.equals("l")) {
                loadInfo();
                welcomePrompt();
                nameunk = false;
                calunk = false;

            } else if (startresponse.equals("n")) {
                namePrompt();
                response = new Scanner(System.in);
                String nameresponse = response.nextLine();
                //currentUser.name = nameresponse;
                nameunk = false;

            }

        }
        while (calunk) {
            caloriesPrompt();
            response = new Scanner(System.in);
            String calresponse = response.nextLine();
            if (calresponse.equals("q")) {
                System.exit(0);
            } else {
                currentCalories = Integer.parseInt(calresponse);
                //currentUser.calories = currentCalories;
                calunk = false;                
            }
        }

        currentUser = new User(username, usercal);




    }

    @SuppressWarnings("methodlength")
    public void userActions() {
        boolean repeatUserOptions = true;

        while (repeatUserOptions) {

            actionPrompt();
            response = new Scanner(System.in);
            int actionreply;
            try {
                actionreply = Integer.parseInt(response.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number corresponding to the actions menu.");
                continue;
            }

            switch (actionreply) {
                case 1:
                    changeName();

                    break;

                case 2:
                    updateCal();
                
                    responsePending();
                    break;


                case 3:
                    fatLossRate();            
                    responsePending();
              
                    break;
                case 4:
                    boolean repeatMenu = true;
                    while (repeatMenu) {
                        menuPrompt();
                        int menureply;
                        try {
                            menureply = Integer.parseInt(response.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input! Please enter a valid menu option.");
                            continue;
                        }
                        switch (menureply) {
                            case 1:
                                System.out.println("\nSorry! This feature is still being developed");
                                System.out.println("Type 'b' to go back");
                                if (response.nextLine().equals('b')) {
                                    break;
                                }
                            case 2:
                                while (foodtracker.getName().equals("")) {
                                    System.out.println("\nWhat would you like to call your calorie tracker?");
                                    String menuname = response.nextLine();
                                    foodtracker.changeName(menuname);
                                }
                                addingFood();
                                break;
                            case 3:
                                exerciseResults();
                                break;
                            case 4:
                                showAllFood(foodtracker);
                                break;
                            
                            case 5:
                                repeatMenu = false;
                                break;
                        }
                    }
                    break;
                
                case 5:
                    saveInfo();
                    break;
                
                case 6:
                    loadInfo();
                    break;

                case 7:
                    System.exit(0);
            }
        }      
    }

    //EFFECTSL displays all the food the user has entered into the food tracker
    public void showAllFood(FoodItems resto) {
        System.out.println("\nYou ate:");
        for (FoodItem i : resto.getListofFI()) {
            System.out.println(i.getFoodName());
        }
    }

    //Modifies:this
    //EFFECTS: replaces the previous calories with new calories entered
    private void updateCal() {
        int newcal; 
        
        System.out.println("\n How many calories did you burn?");
        try {
            newcal = Integer.parseInt(response.nextLine());
            currentUser.changeCal(newcal);
            System.out.println("\nCalories updated.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid number.");
        }
        System.out.println("Type 'b' to go back.");
    }

    //Requires: calories > 0
    //Effects: Calculates the fatloss rate and displays to user
    private void fatLossRate() {
        int fatloss; 
        fatloss = (3500 / currentUser.getCalories());
    
        System.out.println("\n If you continue to burn " + currentUser.getCalories() + " everyday."); 
        System.out.println("It will take you " + fatloss + " days to lose one pound of fat.");
        System.out.println("Type 'b' to go back.");
    }

    //REQUIRES only characters entered
    //EFFECTS: repeats until the approriate response is added
    private void responsePending() {
        String exit2 = response.nextLine();
        while (!exit2.equals("b")) {
            System.out.println("Type 'b' to go back.");
            exit2 = response.nextLine();
        }
    }
    
    //REQUIRES: only characters/strings entered
    //MODIFIES: this
    //EFFECTS:changes the user's name

    private void changeName() {
        String newname; 
        System.out.println("\n What would you like to name to change to?");
        newname = response.nextLine();
        currentUser.changeName(newname);
                
        System.out.println("Welcome " + currentUser.getName());
        String exit1 = "";

        while (!exit1.equals("b")) {
            System.out.println("Type 'b' to go back.");
            exit1 = response.nextLine();
        }
    }
//REQUIRES a food tracker to be made
//MODIFIES foodtracker
//EFFECTS adds a food item to the foodtracker
    
    @SuppressWarnings("methodlength")
    private void addingFood() {
        boolean keepadding = true;
        while (keepadding) {
            String foodname = "";
            int foodcal = 0;
            System.out.println("\nLet's add to " + foodtracker.getName() + "\nWhat's the food called?");
            foodname = response.nextLine();
            System.out.println("\nHow many calories?");
            try {
                foodcal = Integer.parseInt(response.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number for calories.");
                continue;
            }
            FoodItem newfood = new FoodItem(foodname, foodcal);
            foodtracker.addFood(newfood);

            System.out.println("\n Do you want to continue adding? \n 1: Yes \n 2: No");
            int cont;
            try {
                cont = Integer.parseInt(response.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter 1 for Yes or 2 for No.");
                continue;
            }
            switch (cont) {
                case 1: 
                    break;

                case 2:
                    keepadding = false;
                    break;
            }

        }
        
    }
    

    //REQUIRES the currentUser.calories are > 0
    //MODIFIES:
    //EFFECTS: Calculates the netbalance of food eaten and calories burned. And displays appropriate message
    private void exerciseResults() {
        int caloriesremaining = (foodtracker.totalCalories(foodtracker) - currentUser.getCalories());
        if (caloriesremaining > 0) {
            System.out.println("\n Keep working! you've got " + caloriesremaining + " more calories to burn.");

        } else if (caloriesremaining == 0) {
            System.out.println("\n Good job! You're all balanced out.");
        } else {
            System.out.println("\nWooohoo. You've got room to indulge. \nLet's Feast!!");
        }
        
    }

    //Effects: Asks user what htey would do for the foodtracker object
    private void menuPrompt() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1: Look up a restaurant");
        System.out.println("2: Add a food item to your tracker");
        System.out.println("3: Calculate if I've burned all my junkfood");
        System.out.println("4: See a list of all food I had");
        System.out.println("5: Go back to previous screen");
    }

    //Effects: Asks user what they would like to do
    private void actionPrompt() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1: Change your name");
        System.out.println("2: Update your calories burned");
        System.out.println("3: Calculate fat burn rate");
        System.out.println("4: Enter the Food tracker page");
        System.out.println("5: Save User Info");
        System.out.println("6: Load User Info");
        System.out.println("7: Quit");   
    }
    //Effects: Asks user if they want to load old data or create new

    private void startPrompt() {
        System.out.println("\nWelcome to Never On the Hips! ");
        System.out.println("Do you want to load previous data? Type 'l' ");
        System.out.println("Or do you want to start new? Type 'n' ");
        System.out.println("Type 'q' to quit");

    }

    //Effects: Displays the name and calories entered to user
    private void welcomePrompt() {
        System.out.println("\nWelcome " + currentUser.getName() + "!");
        System.out.println("You burned " + currentUser.getCalories() + " calories.");
    }

    //Effects: prompts user to enter their name
    private void namePrompt() {
        System.out.println("\nWelcome to Never On the Hips! ");
        System.out.println("What is your name?");
        System.out.println("Type 'q' to quit");
    }

    //Effects: prompts user to enter their calories burned
    private void caloriesPrompt() {
        System.out.println("\nHow many calories did you burn?");
        System.out.println("Type 'q' to quit");
    }
    // Modifies: this
    // Effects: Saves User and FoodItems information from JSON files

    private void saveInfo() {
        // Specify file paths for saving User and FoodItems
        String userFilePath = "user.json";
        String foodItemsFilePath = "food_items.json";

        try {
            // Create JsonWriter instances for both User and FoodItems
            JsonWriter userWriter = new JsonWriter(userFilePath);
            JsonWriter foodItemsWriter = new JsonWriter(foodItemsFilePath);

            // Write the current user and food tracker to their respective files
            userWriter.writeUser(currentUser);
            foodItemsWriter.writeFoodItems(foodtracker);

            // Notify the user of the successful save
            System.out.println("User and FoodItems have been saved successfully!");
        } catch (Exception e) {
            System.out.println("An error occurred while saving data: " + e.getMessage());
        }
    }

    // Modifies: this
    // Effects: Load User and FoodItems information from JSON files
    public static void loadInfo() {
        // Specify file paths for loading User and FoodItems
        String userFilePath = "user.json";
        String foodItemsFilePath = "food_items.json";

        try {
            // Create JsonReader instances for both User and FoodItems
            JsonReader userReader = new JsonReader(userFilePath);
            JsonReader foodItemsReader = new JsonReader(foodItemsFilePath);

            // Load the user and food tracker from their respective files
            currentUser = userReader.readUser();
            foodtracker = foodItemsReader.readFoodItems();

            // Notify the user of the successful load
            System.out.println("User and FoodItems have been loaded successfully!");
        } catch (Exception e) {
            System.out.println("An error occurred while loading data: " + e.getMessage());
        }
    }
}



