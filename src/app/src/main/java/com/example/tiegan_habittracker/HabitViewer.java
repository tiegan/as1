package com.example.tiegan_habittracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * In this activity, the content of the selected habit from ViewHabits is viewed in detail by
 * showing the name, date given, days to be completed, number of completions and whether it not it
 * has been completed for that day. The user can also click to add a completion for that habit, and
 * view the days the habit was completed on by clicking the second button and going into another
 * activity.
 *
 * Design rationale was to output the info necessary from the habit. Make sure it was all outputted,
 * make sure it updated. Make it clean, make it simple. Don't make it complex. That's all I wanted
 * to do.
 *
 * Other than needing some cleaning up the code, no real issues here.
 *
 * References:
 * 1) http://stackoverflow.com/questions/7074097/how-to-pass-integer-from-one-activity-to-another
 * (Author: Paresh Mayani)
 */
public class HabitViewer extends Activity {

    private static final String FILENAME = "HabitTrackerData.sav";
    private TextView nameView;
    private TextView dateView;
    private TextView daysView;
    private TextView countView;
    private TextView completionView;
    private int index;
    private Habit habit;
    private HabitList habitList;
    private CompletionList completionList;
    private String weekday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_viewer);

        //from reference 1
        Intent intent = getIntent();
        index = intent.getIntExtra("givenHabit", 0);
        //end from reference 1

        nameView = (TextView) findViewById(R.id.HabitName);
        dateView = (TextView) findViewById(R.id.DateGiven);
        daysView = (TextView) findViewById(R.id.DaysGiven);
        countView = (TextView) findViewById(R.id.CurrentCount);
        completionView = (TextView) findViewById(R.id.isCompleted);
        Button addButton = (Button) findViewById(R.id.addCount);
        Button viewButton = (Button) findViewById(R.id.viewCount);
        Button returnButton = (Button) findViewById(R.id.returnToList);

        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                checkDates();
                if(!habit.returnStatus()) {
                    habit.setCompletions(new Date());
                    habit.setStatus(true);
                    completionList.increaseCompletion();
                }
                else {
                    completionList.increaseCompletion();
                }
                showCount(completionList.returnTotalCompletions());
                showCompletionStatus();
                saveInFile();
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //from reference 1
                Intent intent = new Intent(HabitViewer.this, CompletionViewer.class);
                intent.putExtra("givenHabit", index);
                startActivity(intent);
                //end from reference 1
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });
    }

    //Loads all values from habitList into the textViews.
    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        habit = habitList.getHabit(index);
        completionList = habit.returnCompletionList();
        checkDates();
        showName(habit.toString());
        showDate(habit.returnDate());
        showDays(habit.returnDaysList());
        showCount(completionList.returnTotalCompletions());
        showCompletionStatus();
    }

    //Comes back from CompletionViewer. Only activity where this is necessary.
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile();
        habit = habitList.getHabit(index);
        completionList = habit.returnCompletionList();
        checkDates();
        showCount(completionList.returnTotalCompletions());
        showCompletionStatus();
    }

    private void showName(String name) {
        nameView.setText(name);
        nameView.setTextSize(16);
    }

    private void showDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateView.setText(dateFormat.format(date));
        dateView.setTextSize(16);
    }

    private void showDays(DaysList days) {
        String text = "";
        for(int i = 0; i < days.returnCount(); i++) {
            Day day = days.returnDay(i);
            text = text + day.getName() + " ";
        }
        daysView.setText(text);
        daysView.setTextSize(16);
    }

    private void showCount(int count) {
        String text = Integer.toString(completionList.returnSize()) + " day(s) (" +
                Integer.toString(completionList.returnTotalCompletions()) + " total completion(s))";
        countView.setText(text);
        countView.setTextSize(16);
    }

    //Three options for showCompletionStatus since what if they aren't supposed to complete the
    //habit that day?
    private void showCompletionStatus() {
        weekday = new SimpleDateFormat("EEEE").format(new Date());
        if(habit.returnDaysList().hasDay(weekday) && habit.returnStatus()) {
            completionView.setText("Yes");
        }
        else if (habit.returnDaysList().hasDay(weekday) && !habit.returnStatus()) {
            completionView.setText("No");
        }
        else{
            completionView.setText("n/a");
        }
        completionView.setTextSize(16);
    }

    //Makes sure the status is correct.
    private void checkDates() {
        String date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").format(completionList.returnLastCheckedDay());
        } catch (NullPointerException e) {
            date = "";
        }
        String date2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if(!date.equals(date2)) {
            habit.setStatus(false);
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
            // from reference 3
            Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_SHORT).show();
            finish();
        } catch (IOException e) {
            // from reference 3
            Toast.makeText(getApplicationContext(), "IO Error", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(habitList, writer);
            writer.flush();
        } catch (FileNotFoundException e) {
            // from reference 3
            Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_SHORT).show();
            finish();
        } catch (IOException e) {
            // from reference 3
            Toast.makeText(getApplicationContext(), "IO Error", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
