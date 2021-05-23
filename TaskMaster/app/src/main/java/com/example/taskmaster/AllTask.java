package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.example.taskmaster.AppDatabase.databaseWriteExecutor;

public class AllTask extends AppCompatActivity {

    Button btn_back_task;
    RecyclerView recyclerView;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toasty.warning(this, "Welcome in AllTask activity", Toast.LENGTH_SHORT,true).show();

        btn_back_task= findViewById(R.id.back_task);
        recyclerView= findViewById(R.id.recycler_view);
//        taskList = new ArrayList<>(); //set it's properties

        recyclerView.setLayoutManager(new LinearLayoutManager(this));//linear layout

        databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Task> taskList = AppDatabase.getDatabase(getApplicationContext()).taskDao().getAll();
                adapter = new Adapter(AllTask.this,taskList);
                recyclerView.setAdapter(adapter);

            }
        });
        btn_back_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AllTask.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}