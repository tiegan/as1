package com.example.tiegan_habittracker;

import java.util.ArrayList;
import java.util.Date;

/**
 * In this class, a list of completions is created and controlled by the class. It contains
 * size for indexing, totalCompletions for the total number of completions by the completions within
 * the ArrayList and lastCheckedDay to help check whether or not a new Completions needs to be
 * added.
 *
 * Design rationale was to have a class that handled all the completions and that could help
 * separate them based off their date.
 *
 * No issues.
 */
public class CompletionList {
    private ArrayList<Completions> completions = new ArrayList<Completions>();
    private int totalCompletions;
    private int size;
    private Date lastCheckedDay;

    public CompletionList() {
        totalCompletions = 0;
        size = 0;
    }

    public void createCompletion(Date date) {
        Completions completion = new Completions(date);
        completions.add(completion);
        size += 1;
        lastCheckedDay = date;
    }

    public void increaseCompletion() {
        completions.get(size-1).increaseCompletions();
        totalCompletions += 1;
    }

    public void removeCompletion(int index) {
        if(index == size-1) {
            lastCheckedDay = new Date(0, 0, 0);
        }
        size -= 1;
        totalCompletions -= completions.get(index).returnCompletions();
        completions.remove(index);
    }

    public Date returnLastCheckedDay() {
        return lastCheckedDay;
    }

    public int returnSize() {return size;}

    public int returnTotalCompletions() {
        return totalCompletions;
    }

    public ArrayList<Completions> returnCompletionList() {
        return completions;
    }

    //Used only for testing.
    public boolean hasCompletion(Completions completion) { return completions.contains(completion);}
}
