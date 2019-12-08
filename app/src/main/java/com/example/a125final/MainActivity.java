package com.example.a125final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final int newTaskRequestCode = 1;

    private FloatingActionButton addTaskButton;

    private Map<String, Category> categories = new HashMap<String, Category>();

    private List<String> categoryNames = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryNames = Arrays.asList(getResources().getStringArray(R.array.categories_array));

        //fill the categories Hashmap with category objects based on the categories_array
        for (int i = 0; i < categoryNames.size(); i++) {
            String categoryName = categoryNames.get(i);
            categories.put(categoryName, new Category(categoryName));
        }

        setTitle("Your Tasks");
        addTaskButton = findViewById(R.id.createTaskButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                System.out.println("Hello There");
                addTask();

            }

        });
    }

    private void addTask() {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivityForResult(intent, newTaskRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == newTaskRequestCode) {
            String newCategoryName = data.getStringExtra("category");
            Task taskToAdd;

            //insert logic to create Task based on intents here
            if (data.getBooleanExtra("repeating", true)) {
                boolean[] repeatingDates = data.getBooleanArrayExtra("daterepeat");
                String description = data.getStringExtra("description");
                String taskName = data.getStringExtra("taskName");
                taskToAdd = new Task(taskName, repeatingDates, description);
            } else {
                //parse date from string date
                String dateAsString = data.getStringExtra("date");
                String[] dateValues = dateAsString.split("/");
                int day = Integer.parseInt(dateValues[0]);
                int month = Integer.parseInt(dateValues[1]);
                int year = Integer.parseInt(dateValues[2]);
                Date date = new Date(year, month, day);
                String description = data.getStringExtra("description");
                String taskName = data.getStringExtra("taskName");
                taskToAdd = new Task(taskName, date, description);
            }

            Category categoryToAddTo = categories.get(newCategoryName);
            categoryToAddTo.addTask(taskToAdd);

            updateUI();
        }
    }

    private void updateUI() {
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        mainLayout.removeAllViews();

        for (Map.Entry<String, Category> entry : categories.entrySet()) {
            if (entry.getValue().getTasks().size() > 0) {
                View categoryChunk = getLayoutInflater().inflate(R.layout.chunk_category, mainLayout, false);

                TextView categoryText = categoryChunk.findViewById(R.id.categoryText);
                categoryText.setText(entry.getKey());

                LinearLayout categoryLayout = categoryChunk.findViewById(R.id.categoryLayout);
                for (Task t : entry.getValue().getTasks()) {
                    View taskChunk = getLayoutInflater().inflate(R.layout.chunk_task, categoryLayout, false);

                    TextView taskName = taskChunk.findViewById(R.id.taskName);
                    TextView dueDate = taskChunk.findViewById(R.id.dueDate);

                    String taskNameText = t.getTaskName();
                    while (taskNameText.length() < 20) {
                        taskNameText += " ";
                    }

                    taskName.setText(taskNameText);
                    dueDate.setText(t.getDate().toString());

                    categoryLayout.addView(taskChunk);
                }

                mainLayout.addView(categoryChunk);
            }
        }
    }
}
