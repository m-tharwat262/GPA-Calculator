package com.example.android.explorationgpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {


    public static final String LOG_TAG = LoginActivity.class.getSimpleName(); // activity name.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // handle clicks on the NEXT button.
        setupNextButton();

    }



    /**
     * Handle clicks on the NEXT button.
     */
    private void setupNextButton() {

        // all the layout except the ProgressBar.
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.activity_main_full_layout);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.activity_main_progress_bar);

        // know when the NEXT button clicked.
        TextView mNextButton = (TextView) findViewById(R.id.activity_main_next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // make only the progress bar visible when the button click.
                linearLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                // link the data that the user insert it to the preference settings.
                setPreference();

                // destroy the current Activity and start the GpaActivity.
                Intent intent = new Intent(LoginActivity.this , GpaActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }


    /**
     * Save the student info (name & ID) inside the preference settings.
     */
    private void setPreference() {

        // to access to the preference settings.
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // get the student name from EditText.
        EditText studentNameEditText = (EditText) findViewById(R.id.activity_main_student_name);
        String studentName = studentNameEditText.getText().toString();

        // get the student ID from EditText.
        EditText studentIdEditText = (EditText) findViewById(R.id.activity_main_student_id);
        String studentId = studentIdEditText.getText().toString();

        // save the student name and the id in preference settings.
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(getString(R.string.settings_student_name_key), studentName);
        editor.putString(getString(R.string.settings_student_id_key), studentId);
        editor.apply();

    }



}
