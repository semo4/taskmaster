package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetail extends AppCompatActivity {

    TextView title, description, state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        title =findViewById(R.id.title);
        description =findViewById(R.id.description);
        state =findViewById(R.id.state);

        Intent i = getIntent();
        String str_title = i.getStringExtra("title");
        String str_desc = i.getStringExtra("body");
        String str_state = i.getStringExtra("state");

        title.setText(str_title);
        description.setText(str_desc);
        state.setText(str_state);




    }













}