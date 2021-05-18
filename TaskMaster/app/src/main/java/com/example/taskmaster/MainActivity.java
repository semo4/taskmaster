package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn_add_task, btn_all_task, btn_task_one, btn_task_two,btn_task_three, btn_setting;
    TextView username;
    SharedPreferences sharedPreferences;

    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add_task= findViewById(R.id.add_task);
        btn_all_task= findViewById(R.id.all_task);
        btn_task_one= findViewById(R.id.titleOne);
        btn_task_two= findViewById(R.id.titleTwo);
        btn_task_three= findViewById(R.id.titleThree);
        btn_setting= findViewById(R.id.setting);
        username= findViewById(R.id.username);
        recyclerView= findViewById(R.id.recycler_view);
        taskList = new ArrayList<>(); //set it's properties

        recyclerView.setLayoutManager(new LinearLayoutManager(this));//linear layout


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sharedPreferences.getString("NAME", "User");
        username.setText(name + "\'s Tasks");
        String text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s," +
                " when an unknown printer took a galley of type and scrambled it to make a type specimen book. ";
        Task t = new Task();
        t.setTitle("Task One");
        t.setBody(text);
        t.setState("new");
        taskList.add(t);
        t = new Task();
        t.setTitle("Task Two");
        t.setBody(text);
        t.setState("assigned");
        taskList.add(t);
        t = new Task();
        t.setTitle("Task Three");
        t.setBody(text);
        t.setState("in progress");
        taskList.add(t);
        t = new Task();
        t.setTitle("Task Four");
        t.setBody(text);
        t.setState("complete");
        taskList.add(t);

        adapter = new Adapter(MainActivity.this,taskList);
        recyclerView.setAdapter(adapter);



        btn_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddTask.class);
                startActivity(i);
            }
        });

        btn_all_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AllTask.class);
                startActivity(i);
            }
        });
        btn_task_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TaskDetail.class);
                i.putExtra("title", "Task One Detail");
                i.putExtra("body", text);
                startActivity(i);
            }
        });
        btn_task_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TaskDetail.class);
                i.putExtra("title", "Task Two Detail");
                i.putExtra("body", text);
                startActivity(i);
            }
        });
        btn_task_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TaskDetail.class);
                i.putExtra("title", "Task Three Detail");
                i.putExtra("body", text);
                startActivity(i);
            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SettingsPage.class);
                startActivity(i);
            }
        });

    }
}