package model;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//class to test foodItems
public class FoodItemsTest {
    FoodItems resto;
    FoodItem pizza;
    FoodItem burger;

    @BeforeEach
    public void runBefore() {
        resto = new FoodItems("Yum");
        pizza = new FoodItem("pizza", 240);
        burger = new FoodItem("boiga", 450);
    }

    @Test
    public void testAddFood() {
        resto.addFood(pizza);
        assertEquals(1, resto.getListofFI().size());
        assertEquals(pizza, resto.getListofFI().get(0));
    }

    @Test
    public void testTotalCalories() {
        resto.addFood(pizza);
        resto.addFood(burger);

        assertEquals(690, resto.totalCalories(resto));
    }

    @Test
    public void testGetName() {
        assertEquals("Yum", resto.getName());
    }

    @Test
    public void testGetMenu() {
        resto.addFood(pizza);
        resto.addFood(burger);

        ArrayList<FoodItem> menu = resto.getListofFI();

        assertEquals(2, menu.size());
        assertEquals(pizza, menu.get(0));
        assertEquals(burger, menu.get(1));

    }
}
