package com.example.tiegan_habittracker;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Here is a simple class for completions. In the constructor, it takes in the date to separate
 * it from all other completions, and sets the count to 0. Whenever the user presses to increase
 * the count, it goes up by 1. For the adapter, it contains a toString() method to print the wanted
 * string.
 *
 * Design rationale was, since we needed completions to be separated, I thought separating them by
 * their date would be the best way to handle them. I actually think it's a smart and good way to
 * handle them, since that way the user will know "Hey, on *day* I completed *habit* *count* times",
 * and will feel good about themselves.
 *
 * No issues.
 */
public class Completions {
    private Date date;
    private int count;

    public Completions(Date date) {
        this.date = date;
        count = 0;
    }

    public void increaseCompletions() {
        count += 1;
    }

    public int returnCompletions() {
        return count;
    }

    public Date returnDate() {
        return date;
    }

    // Returns string to be printed by the array.
    public String toString() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(returnDate());
        String text = "On " + date + ", this habit was completed " + Integer.toString(count) +
                " time(s).";
        return text;
    }
}
