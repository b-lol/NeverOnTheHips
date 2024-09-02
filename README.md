# NeverOnTheHips


## An application to equilibrate calories in and out.

This application at its most simple basic level will do one thing: **calories are supplied and the equivalent food item is presented.**

The motivation behind this application is making the ingestion of calories more visceral for the average user. For people trying to lose weight its difficult to contextualize how much a calorie is, at a higher level what eating a donut worth 280 calories will translate to. As individualize start their fitness journey they can use this to set a goal of how much they need to work out to burn of that cheat meal.

There are additional ideas I would like to implement but they will be introduced as time allows. 

Some classes that I would include:
1. A class representing the user
2. A class thats represents restaurants (reach restaurant is made of name and list of food item)
3. A class that represents fooditems (name and calories)


### User Stories:
- Make my user profile with name and calories burned
- Update name and calories burned
- calculate how many days it will take to burn one pound of fat at the current calorie burned amount
- see what food items user has eaten (that which was added to their tracker)
- Load my previous data (username, calories and fooditems consumed)
- Save my data (username, calories and fooditems consumed)

# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by entering your creating a user, and then creating a foodtracker
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking on DisplayTrackerButton
- You can locate my visual component by running the app. Its on the first menu.
- You can save the state of my application by choosing the save option at multiple screens
- You can reload the state of my application by choosing the load option at multiple screens


# Phase4: Task 2:
Logged Events:
Thu Aug 08 09:44:49 PDT 2024
Created User: Andrew with 375 calories
Thu Aug 08 09:45:02 PDT 2024
Created FoodItems: Ribfest! with an empty list
Thu Aug 08 09:45:15 PDT 2024
Created FoodItem: 4Ribs with 800 calories
Thu Aug 08 09:45:15 PDT 2024
Added food item: 4Ribs
Thu Aug 08 09:45:24 PDT 2024
Created FoodItem: 8 ribs with 1600 calories
Thu Aug 08 09:45:24 PDT 2024
Added food item: 8 ribs

# Phase4: Task 3:
-I might refactor the FoodTracker Frame and the Display FoodTracker into one class and have a section that displays the tracker. it would make sense to hae all the FoodTracker in one area where you can add and remove fooditems and see it update. 