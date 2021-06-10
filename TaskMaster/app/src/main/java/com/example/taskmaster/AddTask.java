package com.example.taskmaster;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;

import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.example.taskmaster.AppDatabase.databaseWriteExecutor;

public class AddTask extends AppCompatActivity {

    EditText task_title,task_desc;
    Button  btn_add_task, btn_add_image;
    TextView total;
    Spinner task_state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
//        try {
//            Amplify.addPlugin(new AWSDataStorePlugin());
//            Amplify.configure(getApplicationContext());
//
//            Log.i("Tutorial", "Initialized Amplify");
//        } catch (AmplifyException e) {
//            Log.e("Tutorial", "Could not initialize Amplify", e);
//        }

        try {
            // Add these lines to add the AWSCognitoAuthPlugin and AWSS3StoragePlugin plugins
//            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toasty.info(this, "Welcome in AddTask activity", Toast.LENGTH_SHORT,true).show();

        task_title= findViewById(R.id.task_title);
        task_desc= findViewById(R.id.task_desc);
        btn_add_task= findViewById(R.id.add_task);
        total= findViewById(R.id.total);
        task_state= findViewById(R.id.task_state);
        btn_add_image= findViewById(R.id.add_image);

        databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Tasks> taskList = AppDatabase.getDatabase(getApplicationContext()).taskDao().getAll();
                total.setText("Total Tasks: "+ taskList.size());
            }
        });

        String image = "";



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
                final String[] image = {""};
                if( title_check&& desc_check && state_check){
                    Amplify.Storage.getUrl(
                            "MyKey",
                            result -> image[0] = result.getUrl().toString(),
                            error -> Log.e("MyAmplifyApp", "URL generation failure", error)
                    );

                    Task item = Task.builder()
                            .title(title)
                            .body(desc)
                            .state(state)
                            .image(image[0])
                            .build();
                    Amplify.DataStore.save(item,
                            success -> Log.i("Tutorial", "Saved item: " + success.item().getTitle()),
                            error -> Log.e("Tutorial", "Could not save item to DataStore", error)
                    );

//                    databaseWriteExecutor.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            Tasks t = new Tasks();
//                            t.setTitle(title);
//                            t.setBody(desc);
//                            t.setState(state);
//
//                            AppDatabase.getDatabase(getApplicationContext()).taskDao().insertAll(t);
//                        }
//                    });
                    Toasty.success(AddTask.this, "Add Task Successfully", Toast.LENGTH_SHORT,true).show();
                    Intent intent = new Intent(AddTask.this, MainActivity.class);
                    startActivity(intent);

                }



            }
        });

        btn_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFileFromMobileStorage();

            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

//    private void uploadFile() {
//        File exampleFile = new File(getApplicationContext().getFilesDir(), "ExampleKey");
//
//        try {
//            BufferedWriter writer = new BufferedWriter(new FileWriter(exampleFile));
//            writer.append("Example file contents");
//            writer.close();
//        } catch (Exception exception) {
//            Log.e("MyAmplifyApp", "Upload failed", exception);
//        }
//
//        Amplify.Storage.uploadFile(
//                "ExampleKey",
//                exampleFile,
//                result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
//                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
//        );
//    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data);
        }
        if(requestCode==9999){
            File file=new File(getApplicationContext().getFilesDir(),"uploads");
            try{
                InputStream inputStream=getContentResolver().openInputStream(data.getData());
                FileUtils.copy(inputStream,new FileOutputStream(file));
                uploadFile(file,"MyKey");
                Toasty.success(AddTask.this, "Add Image Successfully", Toast.LENGTH_SHORT,true).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getFileFromMobileStorage(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("*/*");
        startActivityForResult(i,9999);
    }

    public void uploadFile(File file, String fileName){


        Amplify.Storage.uploadFile(
                fileName,
                file,
                result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
        );

    }

}