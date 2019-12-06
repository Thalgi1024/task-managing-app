package com.example.a125final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int newTaskRequestCode = 1;

    private FloatingActionButton addTaskButton;

    private HashMap<String, Category> categories = new HashMap<String, Category>();

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
            Task taskToAdd = new Task();
            //insert logic to create Task based on intents here

            Category categoryToAddTo = categories.get(newCategoryName);
            categoryToAddTo.addTask(taskToAdd);
        }
    }


}
