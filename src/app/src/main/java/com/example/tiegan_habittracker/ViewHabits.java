package com.example.tiegan_habittracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * In this activity, the list of habits is viewed by loading the HabitList from the file and loading
 * their names to the ListView. Then, when the user clicks on a habit name, it'll take them to the
 * habit page.
 *
 * Design rationale was to have the list with it clickable and for the user to be able to go back.
 * Simple.
 *
 * No issues.
 *
 * References:
 * 1) http://stackoverflow.com/questions/17851687/how-to-handle-the-click-event-in-listview-in-android
 * (Author: Blackbelt)
 * 2) http://stackoverflow.com/questions/7074097/how-to-pass-integer-from-one-activity-to-another
 * (Author: Paresh Mayani)
 * 3) http://stackoverflow.com/questions/3500197/how-to-display-toast-in-android
 * (Author: Elenasys)
 */
public class ViewHabits extends Activity {

    private static final String FILENAME = "HabitTrackerData.sav";
    private ListView allHabits;
    private HabitList habitList;
    private ArrayAdapter<Habit> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_habits);

        allHabits = (ListView) findViewById(R.id.allHabits);
        Button returnButton = (Button) findViewById(R.id.returnToMenu);

        //From reference 1
        allHabits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //end from reference 1

                //from reference 2
                Intent intent = new Intent(ViewHabits.this, HabitViewer.class);
                intent.putExtra("givenHabit", position);
                startActivity(intent);
                //end from reference 2
            }
        });


        returnButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });
    }

    // Necessary try/catch if file exists but there's no habits stored in HabitList.
    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        try {
            ArrayList<Habit> test = habitList.returnHabits();
            adapter = new ArrayAdapter<Habit>(this, R.layout.list_item, test);
            allHabits.setAdapter(adapter);
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Please add a habit first",
                    Toast.LENGTH_SHORT).show(); // from reference 3
            finish();
        }
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<HabitList>() {}.getType();
            habitList = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Please add a habit first",
                    Toast.LENGTH_SHORT).show(); // from reference 3
            finish();
        } catch (IOException e) {
            // from reference 3
            Toast.makeText(getApplicationContext(), "IO Error", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
