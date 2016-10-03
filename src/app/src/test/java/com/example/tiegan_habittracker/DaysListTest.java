package com.example.tiegan_habittracker;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Here, the three methods in DaysList are tested. First, it checks to make sure that adding a day
 * will actually add that day into the ArrayList. Second, it checks to make sure that it can
 * retrieve the Day object from the class. Third, it checks to make sure it correctly counts up
 * the number of elements in the ArrayList.
 */
public class DaysListTest extends ActivityInstrumentationTestCase2 {

    public DaysListTest() {
        super(com.example.tiegan_habittracker.MainMenu.class);
    }

    public void testAddDay() {
        DaysList daysList = new DaysList();
        String text = "Monday";
        daysList.addDay(text);
        assertTrue(daysList.hasDay(text));
    }

    public void testHasDay() {
        DaysList daysList = new DaysList();
        daysList.addDay("Monday");
        Day returnDay = daysList.returnDay(0);
        Day day = new Day("Monday");
        assertTrue(day == returnDay);
    }

    public void testCount() {
        DaysList daysList = new DaysList();
        daysList.addDay("Monday");
        daysList.addDay("Flurbsday");
        assertTrue(daysList.returnCount() == 2);
    }
}
