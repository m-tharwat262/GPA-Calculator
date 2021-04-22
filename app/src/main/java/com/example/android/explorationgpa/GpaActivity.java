package com.example.android.explorationgpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.explorationgpa.settings.SettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GpaActivity extends AppCompatActivity {


    private static final String LOG_TAG = GpaActivity.class.getSimpleName(); // activity name.

    private SharedPreferences mSharedPrefs; // to access to the preference settings.

    private FloatingActionButton mFloatingActionButton; // icon that start to add a new semester.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);


        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.activity_gpa_floating_action_button);


        // check if the user log in before or not.
        checkPreference();

        // handle clicks on the floating action button.
        setupFloatingActionButton();


    }



    /**
     * Override method to inflate our custom menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // for inflate the menu that we made for GpaActivity.
        getMenuInflater().inflate(R.menu.menu_gpa, menu);

        return true;
    }


    /**
     * Override method to handle the clicks on the menu icons.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // handle clicks on any menu icons.
        switch (item.getItemId()) {

            // for settings icon.
            case R.id.action_settings:

                // start SettingActivity.
                Intent intent = new Intent(GpaActivity.this , SettingsActivity.class);
                startActivity(intent);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Check if the user logged in or not when the app open.
     * Make the user log in when he open the app for the first time.
     */
    private void checkPreference () {

        // get the user name from preference settings.
        String studentNamePreference = mSharedPrefs.getString(
                getString(R.string.settings_student_name_key),
                getString(R.string.settings_student_name_default));

        // check if the user did not log in and in this case send him to the log in layout.
        if (studentNamePreference.equals(getString(R.string.settings_student_name_default))) {

            // start LoginActivity to make the user login.
            Intent numbersIntent = new Intent(GpaActivity.this, LoginActivity.class);
            startActivity(numbersIntent);
            finish();
        }

    }


    /**
     * Handle clicks on the floating action button.
     */
    private void setupFloatingActionButton() {

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // start the InfoActivity.
                Intent intent = new Intent(GpaActivity.this, InfoActivity.class);
                startActivity(intent);

            }
        });

    }



}
