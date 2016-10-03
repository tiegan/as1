package com.example.tiegan_habittracker;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Since day only contains one variable and one getter, there's only one test to make sure the name
 * is the same.
 */
public class DayTest extends ActivityInstrumentationTestCase2 {

    public DayTest() {
        super(com.example.tiegan_habittracker.MainMenu.class);
    }

    public void testAddTweet() {
        String text = "Monday";
        Day day = new Day(text);
        String text2 = day.getName();
        assertTrue(text.equals(text2));
    }
}
