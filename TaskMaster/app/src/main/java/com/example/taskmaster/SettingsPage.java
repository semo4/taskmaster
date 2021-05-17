package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

public class SettingsPage extends AppCompatActivity {

    EditText username;
    Button btn_save;
    SharedPreferences sharedPreferences;
    int PRIVATE_MODE = 0;
    private  static final String PREF_NAME ="USER";
    public SharedPreferences.Editor editor;
    public  static final String NAME ="NAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        username= findViewById(R.id.username);
        btn_save= findViewById(R.id.save_name);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString().trim();
                boolean check = false;
                if(name.isEmpty()){
                    username.setError("the name should not be empty");
                }else{
                    username.setError(null);
                    check = true;
                }
                if(check){
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsPage.this);
                    editor = sharedPreferences.edit();
                    editor.putString(NAME, name);
                    editor.commit();

//                    HashMap<String, String> user = new HashMap<String, String>();
//                    // user name
//                    user.put(NAME,sharedPreferences.getString(NAME,null));
                    Intent i = new Intent(SettingsPage.this, MainActivity.class);
                    startActivity(i);
                }

            }
        });
    }
}