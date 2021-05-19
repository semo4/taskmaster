package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.example.taskmaster.AppDatabase.databaseWriteExecutor;

public class AddTask extends AppCompatActivity {

    EditText task_title,task_desc;
    Button  btn_add_task;
    TextView total;
    Spinner task_state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);



        task_title= findViewById(R.id.task_title);
        task_desc= findViewById(R.id.task_desc);
        btn_add_task= findViewById(R.id.add_task);
        total= findViewById(R.id.total);
        task_state= findViewById(R.id.task_state);

        databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Task> taskList = AppDatabase.getDatabase(getApplicationContext()).taskDao().getAll();
                total.setText("Total Tasks: "+ taskList.size());
            }
        });

        btn_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = task_title.getText().toString().trim();
                String desc = task_desc.getText().toString().trim();
                String state = task_state.getSelectedItem().toString().trim();;
                boolean title_check= false;
                boolean desc_check= false;
                boolean state_check= false;

                if(title.isEmpty()){
                    task_title.setError("the title should not be empty");
                }else{
                    task_title.setError(null);
                    title_check= true;

                }
                if(desc.isEmpty()){
                    task_desc.setError("the Description should not be empty");
                }else{
                    task_desc.setError(null);
                    desc_check=true;
                }

                if(state.equals("Select State"))
                {
                    Toast.makeText(AddTask.this,"Please select Task State ",Toast.LENGTH_SHORT).show();
                } else{
                    state_check = true;
                }


                if( title_check&& desc_check && state_check){
                    databaseWriteExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            Task t = new Task();
                            t.setTitle(title);
                            t.setBody(desc);
                            t.setState(state);
                            AppDatabase.getDatabase(getApplicationContext()).taskDao().insertAll(t);
                        }
                    });
                    Intent intent = new Intent(AddTask.this, MainActivity.class);
                    Toast.makeText(AddTask.this, "Add Task Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                }



            }
        });


    }
}