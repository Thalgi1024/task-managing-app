package com.example.a125final;

import java.util.ArrayList;

public class Category {
    //list of tasks in the category
    private ArrayList<Task> tasks = new ArrayList<Task>();
    //name of category
    private String name;

    public Category(String n, ArrayList<Task> firstTasks) {
        name = n;
        tasks = firstTasks;
    }

    public Category(String n) {
        name = n;
    }

    public int getTaskCount() {
        return tasks.size();
    }

    //adds a task to the front of an array
    public void addTask(Task t) {
        tasks.add(0, t);
    }

    public void addTask(int index, Task t) {
        tasks.add(index, t);
    }

    public void removeTask(Task toRemove) {
        if (tasks.contains(toRemove)) {
            tasks.remove(toRemove);
        }

    }

    public void removeTask(int indexToRemove) {
        if (indexToRemove >= 0 && indexToRemove < tasks.size()) {
            tasks.remove(indexToRemove);
        }
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int getTaskIndex(Task t) {
        return tasks.indexOf(t);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
