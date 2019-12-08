package com.example.a125final;

//Date API: https://docs.oracle.com/javase/8/docs/api/java/util/Date.html
import java.util.Date;


public class Task {
    private String taskName;

    /** if true at a certain index, then the task repeats on that day
     */
    private boolean[] dateRepeat = {true, true, true, true, true, true, true};

    private Date date;

    /** if true, then the task is repeatable (use dateRepeat).
     * if false, task is only done once (use date)
     */
    private boolean isRepeating;

    private String description;


    /**Constructor for if the task is a one-time thing (not repeatable)
     * @param name name input
     * @param dateToSet date type
     * @param desc description of the task
     */
    public Task(String name, Date dateToSet, String desc) {
        taskName = name;
        date = dateToSet;
        isRepeating = false;
        description = desc;
    }

    /**Constructor if the task is repeatable
     * @param name name input
     * @param repeats boolean[] dictating which days it repeats on
     * @param desc description of the task
     */
    public Task(String name, boolean[] repeats, String desc) {
        taskName = name;
        dateRepeat = repeats;
        isRepeating = true;
        description = desc;
    }

    /** default constructor. Initiates with empty name/description and repeating on all days
     *
     */
    public Task() {
        this("", "");
    }


    /** initiates with name, description. Repeats on all days
     * @param name name of the task
     * @param desc description
     */
    public Task(String name, String desc) {
        taskName = name;
        description = desc;
        isRepeating = true;
        for (int i = 0; i < dateRepeat.length; i++) {
            dateRepeat[i] = true;
        }

    }

    //getters and setters
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean[] getDateRepeat() {
        return dateRepeat;
    }

    public void setDateRepeat(boolean[] dateRepeat) {
        this.dateRepeat = dateRepeat;
    }

    public Date getDate() {
        if (isRepeating) {
            return date;
        }
        return date;
    }

    /**
     * @return date in yyyy-mm-dd format as a String
     */
    public String displayDate() {
        return date.toString();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isRepeating() {
        return isRepeating;
    }

    public void setRepeating(boolean repeating) {
        isRepeating = repeating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
