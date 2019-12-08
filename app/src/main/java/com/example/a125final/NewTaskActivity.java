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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Represents the new task creation screen, where the user creates a new task.
 */
public class NewTaskActivity extends AppCompatActivity {
    /** The result of the activity to be sent back. */
    Intent result;

    /**
     * Runs when the activity is started.
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        result = new Intent();

        Button createTaskButton = findViewById(R.id.createTaskButton);
        createTaskButton.setOnClickListener(unused -> {
            if (createTask()) {

                setResult(1, result);
                finish();
            }
        });

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
    }

    /**
     * Attempts to create a task with given input from the user.
     * @return Whether the task was created successfully or not
     */
    private boolean createTask() {
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
            boolean[] dateRepeat = {mondayBox.isChecked(), tuesdayBox.isChecked(), wednesdayBox.isChecked(),
                    thursdayBox.isChecked(), fridayBox.isChecked(), saturdayBox.isChecked(), sundayBox.isChecked()};

            result.putExtra("repeating", true);
            result.putExtra("daterepeat", dateRepeat);
            result.putExtra("taskName", taskNameText.getText().toString());
            result.putExtra("description", descriptionText.getText().toString());
            result.putExtra("category", category.getSelectedItem().toString());

            return true;
        }

        String dateText = dueDateText.getText().toString();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        dateFormat.setLenient(false);

        try {
            dateFormat.parse(dateText);
        } catch (ParseException e) {
            dueDateText.setText("Please enter a valid date in the form: dd/mm/yyyy.");
            return false;
        }

        result.putExtra("repeating", false);
        result.putExtra("date", dateText);
        result.putExtra("taskName", taskNameText.getText().toString());
        result.putExtra("description", descriptionText.getText().toString());
        result.putExtra("category", category.getSelectedItem().toString());

        return true;
    }
}
