package com.example.tiegan_habittracker;

import java.util.Date;

/**
 * Here, in habit, it will create a new habit with a name, a date, a DaysList, a CompletionList and
 * a status. It takes in name to set name, integers for year/month/day to set the date and integers
 * for the days to set the days in DaysList. Later, it will actually take in the date for
 * initializing the completionList.
 *
 * Design rationale was basically to follow the specifications from the Assignment 1 page. Have the
 * name? Check. Have the date? Check. Have the days? Done by using an ArrayList and classes, so
 * check. Have the completions? Also done using an ArrayList and classes, so check. Plus status is
 * on top of that so the user knows whether or not they completed the habit for that day (if they
 * were supposed to).
 *
 * Only issue is that setDays could be done much better/cleaner. But that involves changing
 * AddHabit, something I mention in that class that I wish I did much better.
 */
public class Habit {
    private String name;
    private Date date;
    private DaysList daysList;
    private CompletionList completionList;
    private boolean status;

    public Habit(String newName) {
        name = newName;
        completionList = new CompletionList();
        daysList = new DaysList();
        status = false;
    }

    public void setDate(int year, int month, int day) {
        date = new Date(year, month, day);
    }

    public void setDays(int Mon, int Tue, int Wed, int Thu, int Fri, int Sat, int Sun) {
        if(Mon == 1) { daysList.addDay("Monday"); }
        if(Tue == 1) { daysList.addDay("Tuesday"); }
        if(Wed == 1) { daysList.addDay("Wednesday"); }
        if(Thu == 1) { daysList.addDay("Thursday"); }
        if(Fri == 1) { daysList.addDay("Friday"); }
        if(Sat == 1) { daysList.addDay("Saturday"); }
        if(Sun == 1) { daysList.addDay("Sunday"); }
    }

    public void setCompletions(Date date) {
        completionList.createCompletion(date);
    }

    public void setStatus(boolean value) {
        status = value;
    }

    public Date returnDate() {
        return date;
    }

    public DaysList returnDaysList() { return daysList; }

    public CompletionList returnCompletionList() { return completionList; }

    public boolean returnStatus() {
        return status;
    }

    // This is here to make sure the array adapter actually displays the name.
    public String toString() {
        return name;
    }
}
