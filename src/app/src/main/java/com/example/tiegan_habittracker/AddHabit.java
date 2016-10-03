package com.example.tiegan_habittracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
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
import java.util.Calendar;

/**
 * Here, the activity will get the user to input a name, select a date (with it being today's date
 * by default) and check the days of the week they want to complete the habit on. They type in the
 * name, they select the date from a calendar that pops up and they select the days from a drop
 * down menu. If there is a habit with the name selected, or a day is not selected, the app will
 * not let them proceed.
 *
 * Design rationale was just to make sure I had all 3 required things (name, date, days) on the
 * same activity and that they all went into the same habit.
 *
 * In all honesty, this is probably the activity I'm least happy with. With the others I was able
 * to do it mostly all by myself, here I needed to rely on the internet to implement DatePicker and
 * a pop-up list for days. While functional, I really dislike the coding and I feel that the "flow"
 * is broken due to being unable to implement a proper day selector in time. If I ever worked on
 * this after submitting it, I'd start with cleaning up this activity.
 *
 * References (full references in README):
 * 1) http://stackoverflow.com/questions/11895606/android-datepicker-and-datepicker-dialog
 * (Author: Salim)
 * 2) https://stackoverflow.com/questions/13784088/setting-popupmenu-menu-items-programmatically
 * (Authors: Voora Tarun, yshahak)
 * 3) http://stackoverflow.com/questions/3500197/how-to-display-toast-in-android
 * (Author: Elenasys)
 */
public class AddHabit extends Activity {

    private static final String FILENAME = "HabitTrackerData.sav";
    private EditText name;
    private HabitList habitList;

    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    //From reference 1
    private int mYear, mMonth, mDay;
    //end from reference 1

    //From reference 2
    private TextView daysView;
    private Button dayButton;
    //end from reference 2
    private int Monday = 0, Tuesday = 0, Wednesday = 0, Thursday = 0, Friday = 0,
            Saturday = 0, Sunday = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit);

        //From reference 1
        dateView = (TextView) findViewById(R.id.displayDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate();

        daysView = (TextView) findViewById(R.id.displayDays);
        showDays();

        name = (EditText) findViewById(R.id.addName);
        Button dateButton = (Button) findViewById(R.id.selectDate);
        dayButton = (Button) findViewById(R.id.selectDays);
        Button returnButton = (Button) findViewById(R.id.returnToMenu);

        //Everything within here is from Reference 1.
        dateButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(AddHabit.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int newYear,
                                                  int newMonth, int newDay) {
                                year = newYear;
                                month = newMonth;
                                day = newDay;
                                showDate();
                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
        //end from reference 1

        //From reference 2
        dayButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(AddHabit.this, dayButton);
                popup.getMenuInflater()
                        .inflate(R.menu.days, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            //end from reference 2
                            case R.id.slot1:
                                if (Monday == 0) {
                                    Monday = 1;
                                } else {
                                    Monday = 0;
                                }
                                showDays();
                                return true;
                            case R.id.slot2:
                                if (Tuesday == 0) {
                                    Tuesday = 1;
                                } else {
                                    Tuesday = 0;
                                }
                                showDays();
                                return true;
                            case R.id.slot3:
                                if (Wednesday == 0) {
                                    Wednesday = 1;
                                } else {
                                    Wednesday = 0;
                                }
                                showDays();
                                return true;
                            case R.id.slot4:
                                if (Thursday == 0) {
                                    Thursday = 1;
                                } else {
                                    Thursday = 0;
                                }
                                showDays();
                                return true;
                            case R.id.slot5:
                                if (Friday == 0) {
                                    Friday = 1;
                                } else {
                                    Friday = 0;
                                }
                                showDays();
                                return true;
                            case R.id.slot6:
                                if (Saturday == 0) {
                                    Saturday = 1;
                                } else {
                                    Saturday = 0;
                                }
                                showDays();
                                return true;
                            case R.id.slot7:
                                if (Sunday == 0) {
                                    Sunday = 1;
                                } else {
                                    Sunday = 0;
                                }
                                showDays();
                                return true;
                            default:
                                return true;
                        }
                    }
                });
                //from reference 2
                popup.show();
            }
        });
        //end from reference 2

        returnButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String text = name.getText().toString();
                if (text.length() == 0) {
                    Toast.makeText(getApplicationContext(), "You need to input a name!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (Monday == 0 && Tuesday == 0 && Wednesday == 0 && Thursday == 0 && Friday == 0
                            && Saturday == 0 && Sunday == 0) {
                        Toast.makeText(getApplicationContext(), "You need to select a day!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            habitList.createHabit(text, year, month, day, Monday, Tuesday, Wednesday,
                                    Thursday, Friday, Saturday, Sunday);
                        } catch (NullPointerException e) {
                            habitList = new HabitList();
                            habitList.createHabit(text, year, month, day, Monday, Tuesday, Wednesday,
                                    Thursday, Friday, Saturday, Sunday);
                        }
                        if (habitList.hasHabit(text)) {
                            Toast.makeText(getApplicationContext(), "You need a different name!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            saveInFile();
                            finish();
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<HabitList>(){}.getType();
            habitList = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            habitList = new HabitList();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            habitList = new HabitList();
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
            Toast.makeText(getApplicationContext(), "File not found! Returning to menu.",
                    Toast.LENGTH_SHORT).show(); // from reference 3
            finish();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "IO Exception! Returning to menu.",
                    Toast.LENGTH_SHORT).show(); // from reference 3
            finish();
        }
    }

    private void showDate() {
        String text = Integer.toString(year) + "-" + Integer.toString(month+1) + "-" +
                Integer.toString(day) + "\n";
        dateView.setText(text);
        dateView.setTextSize(16);
    }

    private void showDays() {
        String text = "";
        if(Monday == 1) { text += "Monday "; }
        if(Tuesday == 1) { text += "Tuesday "; }
        if(Wednesday == 1) { text += "Wednesday "; }
        if(Thursday == 1) { text += "Thursday "; }
        if(Friday == 1) { text += "Friday "; }
        if(Saturday == 1) { text += "Saturday "; }
        if(Sunday == 1) { text += "Sunday"; }
        text += "\n";
        daysView.setText(text);
        daysView.setTextSize(16);
    }
}