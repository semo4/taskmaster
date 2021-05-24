package com.example.taskmaster;


import android.view.autofill.AutofillId;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Tasks {
    @PrimaryKey(autoGenerate = true)
     int id;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "body")
    String body;
    @ColumnInfo(name = "state")
    String state;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
