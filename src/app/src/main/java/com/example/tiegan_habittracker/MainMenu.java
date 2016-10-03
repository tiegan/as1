package com.example.tiegan_habittracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

/**
 * Here is where the program starts. It's a simple menu with three buttons. One takes you to an
 * activity that lets you add a habit. One takes you to an activity that views habits if there is
 * a habit (if there isn't, it'll pop-up a message saying no habits found). One takes you to an
 * activity that lets you delete all the habits saved in the file.
 *
 * Design rationale was to have a hub that connected to the three main sections: Add, View and
 * Delete. I feel that it works well for its simplicity.
 *
 * No issues.
 */

public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        Button addHabitButton = (Button) findViewById(R.id.add_habit);
        Button viewHabitsButton = (Button) findViewById(R.id.view_habits);
        Button deleteHabitsButton = (Button) findViewById(R.id.delete_habits);

        addHabitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, AddHabit.class);
                startActivity(intent);
            }
        });

        viewHabitsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, ViewHabits.class);
                startActivity(intent);
            }
        });

        deleteHabitsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, DeleteHabits.class);
                startActivity(intent);
            }
        });
    }
}
