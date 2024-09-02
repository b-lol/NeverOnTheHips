package model;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//class to testFoodItem
public class FoodItemTest {
    FoodItem food;

    @BeforeEach
     public void runBefore() {
        food = new FoodItem("Sammie", 290);
    }

    @Test
    public void testFoodItemConstructor() {
        assertEquals("Sammie", food.foodName);
        assertEquals(290, food.calories);
    }

    @Test
    public void testGetCalories() {
        assertEquals(290, food.getCalories());
    }

    @Test
    public void testGetFoodName() {
        assertEquals("Sammie", food.foodName);
    }
}

