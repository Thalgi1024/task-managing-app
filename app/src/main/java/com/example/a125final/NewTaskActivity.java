package com.example.a125final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class NewTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        Button createTaskButton = findViewById(R.id.createTaskButton);
        createTaskButton.setOnClickListener(unused -> {
            System.out.println("Created Task");
        });
    }
}
