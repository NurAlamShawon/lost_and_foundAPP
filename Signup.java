package com.example.lostandfoundapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {

    private EditText etUserName, etEmail, etPhone, etUserId, etPW, etRPW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        decideNavigation();


        setContentView(R.layout.activity_signup);

        etUserName = findViewById(R.id.etUserName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etUserId = findViewById(R.id.etUserId);
        etPW = findViewById(R.id.etPW);
        etRPW = findViewById(R.id.etRPW);



        findViewById(R.id.btnExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signup.this, login.class);
                startActivity(i);
                finish();
            }
        });

        findViewById(R.id.btnGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processSignup();
            }
        });
    }
    private void processSignup(){
        String userName = etUserName.getText().toString().trim();
        String userId = etUserId.getText().toString().trim();
        String userEmail = etEmail.getText().toString().trim();
        String userPhone = etPhone.getText().toString().trim();
        String userPW = etPW.getText().toString().trim();
        String userRPW = etRPW.getText().toString().trim();
        String errMsg = "";
        // write code to validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            errMsg += "Invalid Email Address, ";
            System.out.println(errMsg);
            System.out.println(userEmail);
        }
        if(userName.length() < 4 ){
            errMsg += "Invalid User Name, ";
        }
        if( (userPhone.startsWith("+880") && userPhone.length() == 14) ||
                (userPhone.startsWith("880") && userPhone.length() == 13) ||
                (userPhone.startsWith("01") && userPhone.length() == 11)){

        }else{
            errMsg += "Invalid Phone Number, ";
        }
        //...
        // write code here for other validation
        //..
        if(userPW.length() < 4 || !userPW.equals(userRPW)){
            errMsg += "Invalid Password";
            System.out.println(errMsg);
            System.out.println(userPW);
        }
        if(errMsg.length()>0){
            // show the error message here
            showErrorDialog(errMsg);
            System.out.println(errMsg);

        }

        // store the data on shared preferences
        SharedPreferences sp = this.getSharedPreferences("user_info", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("USER_NAME", userName);
        e.putString("USER_ID", userId);
        e.putString("USER_EMAIL", userEmail);
        e.putString("USER_PHONE", userPhone);
        e.putString("PASSWORD", userPW);

        e.commit();
        //
        Intent i = new Intent(Signup.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void showErrorDialog(String errorMessage){
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


    private void decideNavigation(){
        SharedPreferences sp = this.getSharedPreferences("user_info", MODE_PRIVATE);
        String userName = sp.getString("USER_NAME", "NOT-CREATED");
        if(!userName.equals("NOT-CREATED")){
            Intent i = new Intent(Signup.this, login.class);
            startActivity(i);
            finish();
        }
    }
}