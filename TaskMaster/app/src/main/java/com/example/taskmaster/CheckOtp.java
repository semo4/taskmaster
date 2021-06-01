package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;

public class CheckOtp extends AppCompatActivity {

    EditText check;
    Button btn_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_otp);

        check = findViewById(R.id.code);
        btn_check = findViewById(R.id.check);

        Intent i = getIntent();
        String username = i.getStringExtra("username");

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = check.getText().toString().trim();

                boolean otp_check= false;

                if(otp.isEmpty()){
                    check.setError("the check should not be empty");
                }else if(otp.length()<2){
                    check.setError("the check should not be less than two");
                }
                else{
                    check.setError(null);
                    otp_check=true;
                }

                if(otp_check){
                    Amplify.Auth.confirmSignUp(
                            username,
                            otp,
                            result -> Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
                            error -> Log.e("AuthQuickstart", error.toString())
                    );

                    Intent i = new Intent(CheckOtp.this, Login.class);
                    startActivity(i);
                }
            }
        });
    }
}