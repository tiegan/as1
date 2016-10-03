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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * In CompletionViewer, the adapter shows the list of completions from the given CompletionList.
 * If the user wants to delete a completion, all they have to do is press it. Once done, they just
 * click the return button.
 *
 * Design rationale was that I just wanted to output the completions, and didn't want some overly
 * complicated process to delete them. So I just made it a "when touched delete" thing. Simple,
 * but gets the job done.
 *
 * No issues. Only thing is if I ever worked on this again, I'd make a pop-up menu to confirm the
 * user wants to delete a completion.
 *
 * References (full references in README):
 * 1) http://stackoverflow.com/questions/17851687/how-to-handle-the-click-event-in-listview-in-android
 * (Author: Blackbelt)
 * 2) http://stackoverflow.com/questions/7074097/how-to-pass-integer-from-one-activity-to-another
 * (Author: Paresh Mayani)
 * 3) http://stackoverflow.com/questions/3500197/how-to-display-toast-in-android
 * (Author: Elenasys)
 */
public class CompletionViewer extends Activity {

    private static final String FILENAME = "HabitTrackerData.sav";
    private ListView allCompletions;
    private HabitList habitList;
    private CompletionList completionList;
    private ArrayAdapter<Completions> adapter;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completion_viewer);

        //from reference 2
        Intent intent = getIntent();
        index = intent.getIntExtra("givenHabit", 0);
        //end from reference 2

        allCompletions = (ListView) findViewById(R.id.allCompletions);
        Button returnButton = (Button) findViewById(R.id.returnToHabit);

        //From reference 1
        allCompletions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //end from reference 1
                completionList.removeCompletion(position);
                adapter.notifyDataSetChanged();
                saveInFile();
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });
    }

    /** Try/catch was necessary at one point. Not sure if it's actually necessary now.
     * CompletionList is gotten from loaded HabitList, it's set to the adapter, yet it would be null
     * before. Now it isn't?
     */
    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        try {
            Habit habit = habitList.getHabit(index);
            completionList = habit.returnCompletionList();
            ArrayList<Completions> completions = completionList.returnCompletionList();
            adapter = new ArrayAdapter<Completions>(this, R.layout.list_item, completions);
            allCompletions.setAdapter(adapter);
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "No completions recorded!", Toast.LENGTH_SHORT)
                    .show();
            finish();
        }
    }

    //New exception messages.
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<HabitList>() {}.getType();
            habitList = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "File Not Found Loading",
                    Toast.LENGTH_SHORT).show(); // from reference 3
            finish();
        } catch (IOException e) {
            // from reference 3
            Toast.makeText(getApplicationContext(), "IO Error Loading", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //New exception messages.
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(habitList, writer);
            writer.flush();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "File Not Found Saving",
                    Toast.LENGTH_SHORT).show();  // from reference 3
            finish();
        } catch (IOException e) {
            // from reference 3
            Toast.makeText(getApplicationContext(), "IO Error Saving", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
