package com.example.android.explorationgpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class AddSemesterActivity extends AppCompatActivity {

    private static final String LOG_TAG = AddSemesterActivity.class.getSimpleName();

    private TextView nameTextView; // for the basic info part in the layout
    private TextView idTextView; // for the basic info part in the layout
    private TextView semesterTextView; // for the basic info part in the layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_semester);

        // get the specific view from the xml layout
        nameTextView = (TextView) findViewById(R.id.activity_add_semester_name);
        idTextView = (TextView) findViewById(R.id.activity_add_semester_id);
        semesterTextView = (TextView) findViewById(R.id.activity_add_semester_number_of_semester);

        // get the information which the intent (from infoActivity) that open that activity
        Intent intent = getIntent();
        String studentName = intent.getStringExtra("student_name"); // for student name
        int studentId = intent.getIntExtra("student_id",0); // for student id
        int yearNumber = intent.getIntExtra("year_number", 0); // for number of the year
        int termNumber = intent.getIntExtra("term_number", 0); // for number of the term
        Log.i(LOG_TAG, "intent come from InfoActivity contain : " + studentName + "  " + studentId + " " + yearNumber + " " + termNumber);



        // show the user basic data on the top of the layout.
        setupTheBasicInfo(studentName, studentId, yearNumber, termNumber);



    }


    /**
     * show the user basic data on the top of the layout which include the student name & id and
     * number of the semester that he want calculate the gpa in.
     *
     * @param studentName the student name which comes from the InfoActivity.
     * @param studentId the student id which comes from the InfoActivity.
     * @param yearNumber the number of the year which comes from the InfoActivity.
     * @param termNumber the number of the term comes from the InfoActivity.
     */
    private void setupTheBasicInfo(String studentName, int studentId, int yearNumber, int termNumber) {

        // get the exact number of the semester by the year and term which the user choose
        int semester = SemesterInfo.getNumberOfSemester(yearNumber, termNumber);

        // write the user information in the top part of the layout (basic info part)
        nameTextView.setText(String.valueOf(studentName));
        idTextView.setText(String.valueOf(studentId));
        semesterTextView.setText(String.valueOf(semester));

    }
}
