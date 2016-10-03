package com.example.tiegan_habittracker;

import android.test.ActivityInstrumentationTestCase2;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Here, there are 5 tests, testing adding a completion, adding multiple completions, increasing
 * completions, removing a completion and checking last date passed.
 */
public class CompletionsListTest extends ActivityInstrumentationTestCase2 {

    public CompletionsListTest() {
        super(com.example.tiegan_habittracker.MainMenu.class);
    }

    public void testAddCompletion() {
        CompletionList completionList = new CompletionList();
        completionList.createCompletion(new Date());
        Completions completion = new Completions(new Date());
        assertTrue(completionList.hasCompletion(completion));
    }

    public void testAddCompletions() {
        CompletionList completionList = new CompletionList();
        completionList.createCompletion(new Date());
        completionList.createCompletion(new Date(1995, 0, 17));
        assertTrue(completionList.returnSize() == 2);
    }

    public void testGetTotalCompletions() {
        CompletionList completionList = new CompletionList();
        completionList.createCompletion(new Date());
        completionList.increaseCompletion();
        completionList.increaseCompletion();
        completionList.createCompletion(new Date(1995, 0, 17));
        completionList.increaseCompletion();
        completionList.increaseCompletion();
        assertTrue(completionList.returnTotalCompletions() == 4);
    }


    public void testRemoveCompletion() {
        CompletionList completionList = new CompletionList();
        completionList.createCompletion(new Date());
        completionList.increaseCompletion();
        completionList.increaseCompletion();
        completionList.createCompletion(new Date(1995, 0, 17));
        completionList.increaseCompletion();
        completionList.removeCompletion(0);
        assertTrue(completionList.returnTotalCompletions() == 1);
        assertTrue(completionList.returnSize() == 1);
    }

    public void testCheckLastDate() {
        CompletionList completionList = new CompletionList();
        completionList.createCompletion(new Date());
        completionList.createCompletion(new Date(1995, 0, 17));
        Date date = completionList.returnLastCheckedDay();
        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
        assertTrue(dateString.equals("1995-01-17"));
    }
}

