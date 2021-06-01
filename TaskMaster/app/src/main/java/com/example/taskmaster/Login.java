package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class Login extends AppCompatActivity {

    EditText  username, password;
    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.name);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_username = username.getText().toString().trim();
                String str_password = password.getText().toString().trim();

                boolean name_check= false;
                boolean pass_check= false;


                if(str_username.isEmpty()){
                    username.setError("the Username should not be empty");
                }else if(str_username.length()<2){
                    username.setError("the Username should not be less than two");
                }
                else{
                    username.setError(null);
                    name_check=true;
                }

                if(str_password.isEmpty())
                {
                    password.setError("Password should not be empty");
                } else if(str_password.length()<8){
                    username.setError("the Password should not be less than 8");
                }else{
                    password.setError(null);
                    pass_check = true;
                }


                if( name_check && pass_check){
                    Amplify.Auth.signIn(
                            str_username,
                            str_password,
                            result -> Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete"),
                            error -> Log.e("AuthQuickstart", error.toString())
                    );
                    Intent i = new Intent(Login.this, MainActivity.class);
                    i.putExtra("username", str_username);
                    startActivity(i);
                }
            }
        });
    }
}