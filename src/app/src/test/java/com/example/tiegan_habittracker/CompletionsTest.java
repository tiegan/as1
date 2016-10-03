package com.example.tiegan_habittracker;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

/**
 * Here, only two tests are used. One is to check that the date is equivalent to the one inputted.
 * The second is to check that the count actually increases using increaseCompletions.
 */
public class CompletionsTest extends ActivityInstrumentationTestCase2 {

    public CompletionsTest() {
        super(com.example.tiegan_habittracker.MainMenu.class);
    }

    public void testAddCompletion() {
        Completions completion = new Completions(new Date());
        Date date = completion.returnDate();
        assertTrue(date.equals(new Date()));
    }

    public void testGetCompletions() {
        Completions completion = new Completions(new Date());
        completion.increaseCompletions();
        completion.increaseCompletions();
        completion.increaseCompletions();
        completion.increaseCompletions();
        assertTrue(completion.returnCompletions() == 4);
    }
}
