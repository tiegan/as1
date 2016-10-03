package com.example.tiegan_habittracker;

/**
 * A very simple class, it contains one thing only: the day of the week given.
 * It only has a constructor and a getter. That's all it needs.
 *
 * Design rationale was to have a simple class for day. Duh.
 *
 * No issues.
 */
public class Day {
    private String name;

    public Day(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
