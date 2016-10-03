package com.example.tiegan_habittracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Here, the delete habits screen makes sure the user wants to delete all data, and gives
 * two buttons: Yes and No. Clicking no will just return the user back to the main menu,
 * while clicking yes deletes all data then goes back to the main menu.
 *
 * Design rationale was basically to make sure the user really wanted to delete all habits and
 * didn't just accidentally click delete habits or something. Plus it helps cut out needing to load
 * the file in the main menu.
 *
 * No issues. Works perfectly.
 */
public class DeleteHabits extends Activity {

    private static final String FILENAME = "HabitTrackerData.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_habits);

        Button deleteButton = (Button) findViewById(R.id.deleteHabits);
        Button noDeleteButton = (Button) findViewById(R.id.doNotDelete);

        deleteButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    FileOutputStream fileStream = openFileOutput(FILENAME, 0);
                    fileStream.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException();
                } catch (IOException e) {
                    throw new RuntimeException();
                }
                finish();
            }
        });

        noDeleteButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });

    }
}
