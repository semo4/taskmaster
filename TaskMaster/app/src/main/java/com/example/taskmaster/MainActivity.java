package com.example.taskmaster;

import androidx.annotation.NonNull;
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
;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignOutOptions;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.example.taskmaster.AppDatabase.databaseWriteExecutor;

public class MainActivity extends AppCompatActivity {

    Button btn_add_task, btn_all_task, btn_task_one, btn_task_two,btn_task_three, btn_setting, signup,signin, logout;
    TextView username;
    SharedPreferences sharedPreferences;

    RecyclerView recyclerView;
    Adapter adapter;
//    ArrayList<Task> taskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM token ...", "Fetching FCM is failed", task.getException());
                            return;
                        }
                        String token = task.getResult();
                        Log.d("FCM TOKEN ...",task.getResult());
                    }
                });



        try {
            // Add these lines to add the AWSCognitoAuthPlugin and AWSS3StoragePlugin plugins
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }










        btn_add_task= findViewById(R.id.add_task);
        btn_all_task= findViewById(R.id.all_task);
        btn_task_one= findViewById(R.id.titleOne);
        btn_task_two= findViewById(R.id.titleTwo);
        btn_task_three= findViewById(R.id.titleThree);
        btn_setting= findViewById(R.id.setting);
        username= findViewById(R.id.usernamemain);
        recyclerView= findViewById(R.id.recycler_view);
        signup= findViewById(R.id.signup_page);
        signin= findViewById(R.id.login_page);
        logout= findViewById(R.id.logout);

        Intent i = getIntent();
        String str_username = i.getStringExtra("username");

        if(str_username != null){
            signup.setVisibility(View.GONE);
            signin.setVisibility(View.GONE);
        }else{
            signup.setVisibility(View.VISIBLE);
            signin.setVisibility(View.VISIBLE);
            logout.setVisibility(View.GONE);
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUp.class);
                startActivity(i);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amplify.Auth.signOut(
                        AuthSignOutOptions.builder().globalSignOut(true).build(),
                        () -> Log.i("AuthQuickstart", "Signed out globally"),
                        error -> Log.e("AuthQuickstart", error.toString())
                );
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));//linear layout


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sharedPreferences.getString("NAME", "User");
        username.setText(str_username + "'s Tasks");
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