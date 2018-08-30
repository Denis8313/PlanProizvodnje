package com.example.denis.planproizvodnje.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

//POJO - PLAIN OLD JAVA OBJECT

//convertnig this object to Entity

@Entity(tableName = "task")
public class TaskEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String taskDescription;
    private int priority;
    private Date date;

    @Ignore
    public TaskEntry(String taskDescription, int priority, Date date) {
        this.taskDescription = taskDescription;
        this.priority = priority;
        this.date = date;
    }

    public TaskEntry(int id, String taskDescription, int priority, Date date) {
        this.id = id;
        this.taskDescription = taskDescription;
        this.priority = priority;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public int getPriority() {
        return priority;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
