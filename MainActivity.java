package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> courses = new ArrayList<>();
    private GridLayout grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
             finish();

            }
        });

        courses.add("Lost and Found List");


        grid = findViewById(R.id.grid);
        for (int i = 0; i < grid.getChildCount(); i++) {
            grid.getChildAt(i).setVisibility(View.INVISIBLE);
        }

        int index = 0;
        for (String course : courses) {
            grid.getChildAt(index).setVisibility(View.VISIBLE);
            ((TextView) grid.getChildAt(index)).setText(course);
            grid.getChildAt(index).setOnClickListener(v -> {
                Intent i = new Intent(MainActivity.this, ContactList.class);
                i.putExtra("_COURSE_", ((TextView) v).getText().toString());
                startActivity(i);
            });
            index++;
        }
    }


}