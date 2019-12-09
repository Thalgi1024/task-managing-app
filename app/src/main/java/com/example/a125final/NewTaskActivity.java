package com.example.a125final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Represents the new task creation screen, where the user creates a new task.
 */
public class NewTaskActivity extends AppCompatActivity {
    /** The result of the activity to be sent back. */
    private Intent result;

    /** Intent passed in from MainActivity */
    private Intent thisIntent;

    private int requestCode;

    /**
     * Runs when the activity is started.
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        result = new Intent();
        thisIntent = getIntent();
        requestCode = thisIntent.getIntExtra("requestCode", 1);
        result.putExtra("requestCode", requestCode);



        EditText dueDateText = findViewById(R.id.dueDateText);
        dueDateText.setVisibility(View.VISIBLE);

        LinearLayout weekDayGroup = findViewById(R.id.weekDayGroup);
        weekDayGroup.setVisibility(View.GONE);

        Switch repeatingSwitch = findViewById(R.id.repeatingSwitch);
        repeatingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean checked) {
                if (checked) {
                    weekDayGroup.setVisibility(View.VISIBLE);
                    dueDateText.setVisibility(View.GONE);
                } else {
                    weekDayGroup.setVisibility(View.GONE);
                    dueDateText.setVisibility(View.VISIBLE);
                }
            }
        });

        String titleText;
        if (requestCode == 1) {
            titleText = "Create Task";

        } else {
            //at this point, requestCode has to be equal to 2 (updateTask)
            titleText = "Update Task";
            boolean repeatable = thisIntent.getBooleanExtra("repeating", true);
            repeatingSwitch.setChecked(repeatable);
            if (repeatable) {
                boolean[] repeatingDates = thisIntent.getBooleanArrayExtra("dateRepeat");
                CheckBox monday = (CheckBox) findViewById(R.id.mondayBox);
                monday.setChecked(repeatingDates[0]);
                CheckBox tuesday = (CheckBox) findViewById(R.id.tuesdayBox);
                tuesday.setChecked(repeatingDates[1]);
                CheckBox wednesday = (CheckBox) findViewById(R.id.wednesdayBox);
                wednesday.setChecked(repeatingDates[2]);
                CheckBox thursday = (CheckBox) findViewById(R.id.thursdayBox);
                thursday.setChecked(repeatingDates[3]);
                CheckBox friday = (CheckBox) findViewById(R.id.fridayBox);
                friday.setChecked(repeatingDates[4]);
                CheckBox saturday = (CheckBox) findViewById(R.id.saturdayBox);
                saturday.setChecked(repeatingDates[5]);
                CheckBox sunday = (CheckBox) findViewById(R.id.sundayBox);
                sunday.setChecked(repeatingDates[6]);
            } else {
                String day = thisIntent.getStringExtra("date");
                TextView dateTextBox = findViewById(R.id.dueDateText);
                dateTextBox.setText(day);
            }
            result.putExtra("oldCategoryName", thisIntent.getStringExtra("category"));
            result.putExtra("oldCategoryIndex",
                    thisIntent.getIntExtra("indexInCategory", 0));
            result.putExtra("oldRepeat", thisIntent.getBooleanExtra("dateRepeat", true));

        }

        Button createTaskButton = findViewById(R.id.createTaskButton);
        createTaskButton.setText(titleText);
        TextView title = findViewById(R.id.titleText);
        title.setText(titleText);
        createTaskButton.setOnClickListener(unused -> {
            if (doneWithActivity()) {

                setResult(requestCode, result);
                finish();
            }
        });
    }

    /**
     * Attempts to create a task with given input from the user.
     * @return Whether the task was created successfully or not
     */
    private boolean doneWithActivity() {
        EditText taskNameText = findViewById(R.id.taskNameText);
        EditText dueDateText = findViewById(R.id.dueDateText);
        EditText descriptionText = findViewById(R.id.descriptionText);

        CheckBox mondayBox = findViewById(R.id.mondayBox);
        CheckBox tuesdayBox = findViewById(R.id.tuesdayBox);
        CheckBox wednesdayBox = findViewById(R.id.wednesdayBox);
        CheckBox thursdayBox = findViewById(R.id.thursdayBox);
        CheckBox fridayBox = findViewById(R.id.fridayBox);
        CheckBox saturdayBox = findViewById(R.id.saturdayBox);
        CheckBox sundayBox = findViewById(R.id.sundayBox);

        Spinner category = findViewById(R.id.category);
        Switch repeatingSwitch = findViewById(R.id.repeatingSwitch);

        //Checks if the task is set to be repeating.
        if (repeatingSwitch.isChecked()) {

            //if none of the boxes are checked
            if (!mondayBox.isChecked() && !tuesdayBox.isChecked() && !wednesdayBox.isChecked()
                && !thursdayBox.isChecked() && !fridayBox.isChecked() && !saturdayBox.isChecked()
                && !sundayBox.isChecked()) {
                Toast.makeText(getApplicationContext(),"Please select a day to repeat on",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
            boolean[] dateRepeat = {mondayBox.isChecked(), tuesdayBox.isChecked(), wednesdayBox.isChecked(),
                    thursdayBox.isChecked(), fridayBox.isChecked(), saturdayBox.isChecked(), sundayBox.isChecked()};
            result.putExtra("repeating", true);
            result.putExtra("daterepeat", dateRepeat);
        } else {
            String dateText = dueDateText.getText().toString();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            dateFormat.setLenient(false);

            try {
                dateFormat.parse(dateText);
            } catch (ParseException e) {
                Toast.makeText(getApplicationContext(),
                        "Please enter a valid date in the form: dd/mm/yyyy.",
                        Toast.LENGTH_SHORT).show();
                return false;
            }

            result.putExtra("repeating", false);
            result.putExtra("date", dateText);
        }

        //Task name should not be longer than 28 characters.
        if (taskNameText.getText().toString().length() > 20) {
            Toast.makeText(getApplicationContext(),
                    "Maximum task name length is 20 characters.", Toast.LENGTH_SHORT).show();
            return false;
        }

        result.putExtra("taskName", taskNameText.getText().toString());
        result.putExtra("description", descriptionText.getText().toString());
        result.putExtra("category", category.getSelectedItem().toString());

        return true;
    }
}
