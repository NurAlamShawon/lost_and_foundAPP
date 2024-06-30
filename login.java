package com.example.lostandfoundapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity  {

    private EditText etUserId, etPW;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);



        etUserId = findViewById(R.id.etUserId);
        etPW = findViewById(R.id.etPW);




        findViewById(R.id.btnExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this, Signup.class);
                startActivity(i);
                finish();
            }
        });

        findViewById(R.id.btnGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processLogin();
            }
        });
    }


    private void processLogin() {
        String userId = etUserId.getText().toString().trim();
        String userPW = etPW.getText().toString().trim();
        String errMsg = "";

        if (userPW.length() < 4) {
            errMsg += "Invalid Password";
        }

        if (errMsg.length() > 0) {
            showErrorDialog(errMsg);
        } else {
            SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
            String userIdStored = sp.getString("USER_ID", "");
            String userPWStored = sp.getString("PASSWORD", "");

            if (userId.equals(userIdStored) && userPW.equals(userPWStored)) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("IS_SIGNED_IN", true);
                editor.apply();

                Intent i = new Intent(login.this, MainActivity.class);
                startActivity(i);
                finish();


            } else {
                showErrorDialog("Invalid credentials");
            }
        }
    }

    private void showErrorDialog(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(errorMessage);
        builder.setTitle("Error");
        builder.setCancelable(true);
        builder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }




}