package com.example.android.explorationgpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AddSemesterActivity extends AppCompatActivity {

    private static final String LOG_TAG = AddSemesterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_semester);


        // get the information which the intent (from infoActivity) that open that activity
        Intent intent = getIntent();
        String studentName = intent.getStringExtra("student_name"); // for student name
        int studentId = intent.getIntExtra("student_id",0); // for student id
        int yearNumber = intent.getIntExtra("year_number", 0); // for number of the year
        int termNumber = intent.getIntExtra("term_number", 0); // for number of the term
        Log.i(LOG_TAG, "intent come from InfoActivity : " + studentName + "  " + studentId + " " + yearNumber + " " + termNumber);

    }
}
