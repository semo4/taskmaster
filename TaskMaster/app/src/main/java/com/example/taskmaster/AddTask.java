package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddTask extends AppCompatActivity {

    EditText task_title,task_desc;
    Button  btn_add_task;
    TextView total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        task_title= findViewById(R.id.task_title);
        task_desc= findViewById(R.id.task_desc);
        btn_add_task= findViewById(R.id.add_task);
        total= findViewById(R.id.total);

        btn_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = task_title.getText().toString().trim();
                String desc = task_desc.getText().toString().trim();
                boolean title_check= false;
                boolean desc_check= false;

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
                if( title_check&& desc_check){
                    total.setText("Title : "+ title+ "\n"+ "Description :"+desc );
                }



            }
        });


    }
}