package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.example.taskmaster.AppDatabase.databaseWriteExecutor;

public class MainActivity extends AppCompatActivity {

    Button btn_add_task, btn_all_task, btn_task_one, btn_task_two,btn_task_three, btn_setting;
    TextView username;
    SharedPreferences sharedPreferences;

    RecyclerView recyclerView;
    Adapter adapter;
//    ArrayList<Task> taskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());

            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("Tutorial", "Could not initialize Amplify", e);
        }


        btn_add_task= findViewById(R.id.add_task);
        btn_all_task= findViewById(R.id.all_task);
        btn_task_one= findViewById(R.id.titleOne);
        btn_task_two= findViewById(R.id.titleTwo);
        btn_task_three= findViewById(R.id.titleThree);
        btn_setting= findViewById(R.id.setting);
        username= findViewById(R.id.usernamemain);
        recyclerView= findViewById(R.id.recycler_view);
//        taskList = new ArrayList<>(); //set it's properties

        recyclerView.setLayoutManager(new LinearLayoutManager(this));//linear layout


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sharedPreferences.getString("NAME", "User");
        username.setText(name + "'s Tasks");
        String text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s," +
                " when an unknown printer took a galley of type and scrambled it to make a type specimen book. ";


        Toasty.info(this, "Welcome in Main activity", Toast.LENGTH_SHORT,true).show();

//        databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                List<Tasks> taskList = AppDatabase.getDatabase(getApplicationContext()).taskDao().getAll();
//                adapter = new Adapter(MainActivity.this,taskList);
//                recyclerView.setAdapter(adapter);
//
//            }
//        });

        Amplify.DataStore.query(Task.class,
                todos -> {

                    List<Tasks> taskList = new ArrayList<>();
                    while (todos.hasNext()) {
                        Task todo = todos.next();
                        Tasks t = new Tasks();
                        Log.i("Tutorial", "==== Todo ====");
                        Log.i("Tutorial", "Name: " + todo.getTitle());
                        Log.i("Tutorial", "Name: " + todo.getBody());
                        Log.i("Tutorial", "Name: " + todo.getState());
                        t.setTitle(todo.getTitle());
                        t.setBody(todo.getBody());
                        t.setState(todo.getState());
                        taskList.add(t);
                        adapter = new Adapter(MainActivity.this,taskList);


                    }
                    recyclerView.setAdapter(adapter);
                },
                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
        );



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
                i.putExtra("state", "New");
                startActivity(i);
            }
        });
        btn_task_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TaskDetail.class);
                i.putExtra("title", "Task Two Detail");
                i.putExtra("body", text);
                i.putExtra("state", "Assigned");
                startActivity(i);
            }
        });
        btn_task_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TaskDetail.class);
                i.putExtra("title", "Task Three Detail");
                i.putExtra("body", text);
                i.putExtra("state", "Completed");
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