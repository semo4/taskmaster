package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class SignUp extends AppCompatActivity {


    EditText email, username, password;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = findViewById(R.id.email);
        username = findViewById(R.id.name);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_email = email.getText().toString().toLowerCase().trim();
                String str_username = username.getText().toString().trim();
                String str_password = password.getText().toString().trim();

                boolean email_check= false;
                boolean name_check= false;
                boolean pass_check= false;

                if(str_email.isEmpty()){
                    email.setError("the Email should not be empty");
                }
                else{
                    email.setError(null);
                    email_check= true;

                }
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


                if(email_check && name_check && pass_check){
                        AuthSignUpOptions options = AuthSignUpOptions.builder()
                                .userAttribute(AuthUserAttributeKey.email(), str_email)
                                .build();
                        Amplify.Auth.signUp(str_username, str_password, options,
                                result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
                                error -> Log.e("AuthQuickStart", "Sign up failed", error)
                        );
                        Intent i = new Intent(SignUp.this, CheckOtp.class);
                        i.putExtra("username", str_username);
                        startActivity(i);
                }
            }
        });

    }
}