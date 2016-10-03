package com.example.tiegan_habittracker;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Here, there are 2 tests. One to make sure the habit is created, one to make sure the habit is
 * returned.
 */
public class HabitListTest extends ActivityInstrumentationTestCase2 {

    public HabitListTest() {
        super(com.example.tiegan_habittracker.MainMenu.class);
    }

    public void testHasHabit() {
        HabitList habitList = new HabitList();
        habitList.createHabitName("test");
        assertTrue(habitList.hasHabit("test"));
    }

    public void testGetHabit() {
        HabitList habitList = new HabitList();
        habitList.createHabitName("test");
        Habit habit = new Habit("test");
        assertTrue(habitList.getHabit(0).equals(habit));
    }
}
