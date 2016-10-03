package com.example.tiegan_habittracker;

import java.util.ArrayList;

/**
 *
 * Here, a list for all the days of the week is set up.
 * It contains a constructor which does nothing, a setter and two getters.
 *
 * The setter will add a day.
 * The getter hasDay will return a boolean value as to whether or not the day is in there.
 * The getter getDays will return an arrayList of the days in week.
 *
 * Design rationale was to contain all the days in one convenient place.
 *
 * Only issue is the hasDay function could be simpler, similar to what happened in HabitList. Time
 * constraints! They hurt when things go wrong and you need to fix them quickly!
 */
public class DaysList {
    private ArrayList<Day> week = new ArrayList<Day>();
    private int count;

    public DaysList() {
        count = 0;
    }

    public void addDay(String name) {
        Day day = new Day(name);
        week.add(day);
        count += 1;
    }

    public boolean hasDay(String name) {
        for(int i = 0; i < count; i++) {
            String day = returnDay(i).getName();
            if(name.equals(day)) {
                return true;
            }
        }
        return false;
    }

    public Day returnDay(int index) { return week.get(index);}

    public int returnCount() { return count; }
}
