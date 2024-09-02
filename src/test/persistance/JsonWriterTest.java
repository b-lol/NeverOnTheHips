package persistance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import model.FoodItem;
import model.FoodItems;
import model.User;
import persistence.JsonWriter;

public class JsonWriterTest {
    private static final String USER_FILE_PATH = "test_user.json";
    private static final String FOOD_ITEMS_FILE_PATH = "test_food_items.json";

    @BeforeEach
    public void setUp() {
        // Ensure test files are deleted before each test
        new File(USER_FILE_PATH).delete();
        new File(FOOD_ITEMS_FILE_PATH).delete();
    }

    @AfterEach
    public void tearDown() {
        // Clean up test files after tests are run
        new File(USER_FILE_PATH).delete();
        new File(FOOD_ITEMS_FILE_PATH).delete();
    }

    @Test
    public void testWriteUser() throws Exception {
        User user = new User("Alice", 1800);
        JsonWriter jsonWriter = new JsonWriter(USER_FILE_PATH);

        // Write user to JSON
        jsonWriter.writeUser(user);

        // Verify the file is created
        assertTrue(new File(USER_FILE_PATH).exists());

        // Read file content and parse it
        String content = new String(Files.readAllBytes(Paths.get(USER_FILE_PATH)));
        JSONObject jsonObject = new JSONObject(content);

        // Validate JSON content
        assertEquals("Alice", jsonObject.getString("name"));
        assertEquals(1800, jsonObject.getInt("calories"));
    }

    @Test
    public void testWriteFoodItems() throws Exception {
        FoodItems foodItems = new FoodItems("Healthy Menu");
        foodItems.addFood(new FoodItem("Salad", 150));
        foodItems.addFood(new FoodItem("Chicken Breast", 250));
        JsonWriter jsonWriter = new JsonWriter(FOOD_ITEMS_FILE_PATH);

        // Write food items to JSON
        jsonWriter.writeFoodItems(foodItems);

        // Verify the file is created
        assertTrue(new File(FOOD_ITEMS_FILE_PATH).exists());

        // Read file content and parse it
        String content = new String(Files.readAllBytes(Paths.get(FOOD_ITEMS_FILE_PATH)));
        JSONObject jsonObject = new JSONObject(content);

        // Validate JSON content
        assertEquals("Healthy Menu", jsonObject.getString("name"));

        // Check the menu array
        assertEquals(2, jsonObject.getJSONArray("menu").length());

        JSONObject salad = jsonObject.getJSONArray("menu").getJSONObject(0);
        assertEquals("Salad", salad.getString("foodName"));
        assertEquals(150, salad.getInt("calories"));

        JSONObject chicken = jsonObject.getJSONArray("menu").getJSONObject(1);
        assertEquals("Chicken Breast", chicken.getString("foodName"));
        assertEquals(250, chicken.getInt("calories"));
    }
}
