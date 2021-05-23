package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.example.taskmaster.AppDatabase.databaseWriteExecutor;

public class TaskDetail extends AppCompatActivity {

    TextView title, description, state;
    Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toasty.info(this, "Welcome in TaskDetail activity", Toast.LENGTH_SHORT,true).show();
        title =findViewById(R.id.title);
        description =findViewById(R.id.description);
        state =findViewById(R.id.state);
        delete =findViewById(R.id.delete_task);

        Intent i = getIntent();
        int id = i.getIntExtra("id",1);
        String str_title = i.getStringExtra("title");
        String str_desc = i.getStringExtra("body");
        String str_state = i.getStringExtra("state");

        title.setText(str_title);
        description.setText(str_desc);
        state.setText(str_state);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                databaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {

                        AppDatabase.getDatabase(getApplicationContext()).taskDao().deleteByTaskId(id);
                    }
                });
                Toasty.error(TaskDetail.this, "Delete Task Successfully", Toast.LENGTH_SHORT,true).show();
                Intent intent = new Intent(TaskDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}