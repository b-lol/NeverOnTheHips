package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//class to test User
public class UserTest {
    User user;
    
    @BeforeEach
    public void runBefore() {
        user = new User("Bilal",400);

    }

    @Test
    public void testUserConstructor() {
        assertEquals("Bilal", user.getName());
        assertEquals(400, user.getCalories());
    }

    @Test
   public void testgetName() {
        assertTrue(user.getName().equals("Bilal"));
    }

    @Test
   public void testgetCalories() {
        assertEquals(400, user.getCalories());
    }

}

