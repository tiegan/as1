package com.example.tiegan_habittracker;

import java.util.ArrayList;

/**
 * Here, we create a list of habits that is accessed through this class. It takes in the name, date
 * and days and creates a habit from them. Then it stores it in the ArrayList, and get the habit(s)
 * when called.
 *
 * Design rationale was to make sure the habits could easily be controlled and contained by one
 * class, and having been inspired by the TweetList from LonelyTwitter, I decided implementing a
 * similar class for Habit (and then for Day and Completions) was a good idea.
 *
 * Only issue was hasHabit wasn't doing what I wanted it to originally, so I needed to change it to
 * the design you see below.
 */
public class HabitList {
    private ArrayList<Habit> habits = new ArrayList<Habit>();
    private int count;

    public HabitList() {
        count = 0;
    }

    public void createHabit(String name, int year, int month, int day, int Mon, int Tue,
                            int Wed, int Thu, int Fri, int Sat, int Sun) {
        Habit newHabit = new Habit(name);
        newHabit.setDate(year-1900, month, day);
        newHabit.setDays(Mon, Tue, Wed, Thu, Fri, Sat, Sun);
        habits.add(newHabit);
        count += 1;
    }

    public boolean hasHabit(String name) {
        for(int i = 0; i < count-1; i++) {
            if (name.equals(getHabit(i).toString())) {
                habits.remove(count-1);
                count -= 1;
                return true;
            }
        }
        return false;
    }

    public Habit getHabit(int index) {
        return habits.get(index);
    }

    public ArrayList<Habit> returnHabits() {
        return habits;
    }

    //Solely for testing purposes.
    public void createHabitName(String name) {
        Habit habit = new Habit(name);
    }

}
