package persistance;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.FoodItem;
import model.FoodItems;
import model.User;
import persistence.JsonReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JsonReaderTest {
    private static final String USER_FILE_PATH = "test_user.json";
    private static final String FOOD_ITEMS_FILE_PATH = "test_food_items.json";

    @BeforeEach
    public void setUp() throws IOException {
        // Prepare test JSON data

        // User JSON
        JSONObject userJson = new JSONObject();
        userJson.put("name", "Alice");
        userJson.put("calories", 1800);

        try (FileWriter file = new FileWriter(USER_FILE_PATH)) {
            file.write(userJson.toString());
        }

        // FoodItems JSON
        JSONObject foodItemsJson = new JSONObject();
        foodItemsJson.put("name", "Healthy Menu");

        JSONArray menuArray = new JSONArray();
        JSONObject salad = new JSONObject();
        salad.put("foodName", "Salad");
        salad.put("calories", 150);
        menuArray.put(salad);

        JSONObject chicken = new JSONObject();
        chicken.put("foodName", "Chicken Breast");
        chicken.put("calories", 250);
        menuArray.put(chicken);

        foodItemsJson.put("menu", menuArray);

        try (FileWriter file = new FileWriter(FOOD_ITEMS_FILE_PATH)) {
            file.write(foodItemsJson.toString());
        }
    }

    @AfterEach
    public void tearDown() {
        // Clean up test files after tests are run
        new File(USER_FILE_PATH).delete();
        new File(FOOD_ITEMS_FILE_PATH).delete();
    }

    @Test
    public void testReadUser() throws Exception {
        JsonReader jsonReader = new JsonReader(USER_FILE_PATH);

        // Read user from JSON
        User user = jsonReader.readUser();

        // Validate user data
        assertEquals("Alice", user.getName());
        assertEquals(1800, user.getCalories());
    }

    @Test
    public void testReadFoodItems() throws Exception {
        JsonReader jsonReader = new JsonReader(FOOD_ITEMS_FILE_PATH);

        // Read food items from JSON
        FoodItems foodItems = jsonReader.readFoodItems();

        // Validate food items data
        assertEquals("Healthy Menu", foodItems.getName());
        assertEquals(2, foodItems.getListofFI().size());

        FoodItem salad = foodItems.getListofFI().get(0);
        assertEquals("Salad", salad.getFoodName());
        assertEquals(150, salad.getCalories());

        FoodItem chicken = foodItems.getListofFI().get(1);
        assertEquals("Chicken Breast", chicken.getFoodName());
        assertEquals(250, chicken.getCalories());
    }
}
