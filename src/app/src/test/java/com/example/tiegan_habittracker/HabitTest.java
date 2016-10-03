package com.example.tiegan_habittracker;

import android.test.ActivityInstrumentationTestCase2;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Here, there are 5 tests. Makes sure that it adds its name, gets its status, gets its date,
 * has a CompletionList and has a DaysList.
 */
public class HabitTest extends ActivityInstrumentationTestCase2 {

    public HabitTest() {
        super(com.example.tiegan_habittracker.MainMenu.class);
    }

    public void testAddHabit() {
        Habit habit = new Habit("test");
        assertTrue(habit.toString().equals("test"));
    }

    public void testGetStatus() {
        Habit habit = new Habit("test");
        habit.setStatus(true);
        assertTrue(habit.returnStatus());
    }

    public void testGetDate() {
        Habit habit = new Habit("test");
        habit.setDate(1995, 0, 17);
        Date date = habit.returnDate();
        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
        assertTrue(dateString.equals("1995-01-17"));
    }

    public void testHasDaysList() {
        Habit habit = new Habit("test");
        habit.setDays(1, 0, 0, 0, 0, 0, 0);
        assertTrue(habit.returnDaysList().hasDay("Monday"));
    }

    public void testHasCompletionList() {
        Habit habit = new Habit("test");
        habit.setCompletions(new Date());
        Date date = habit.returnCompletionList().returnLastCheckedDay();
        assertTrue(new Date().equals(date));
    }
}
