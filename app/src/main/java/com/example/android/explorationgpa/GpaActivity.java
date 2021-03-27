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

    private SharedPreferences mSharedPrefs;
    private FloatingActionButton mFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);

        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mFab = (FloatingActionButton) findViewById(R.id.activity_gpa_fab);

        checkPreference();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            // TODO: intent to open the next activity.
                Intent intent = new Intent(GpaActivity.this, InfoActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // for inflate the menu that we made
        getMenuInflater().inflate(R.menu.menu_gpa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_settings:
                // start setting activity.
                Intent intent = new Intent(GpaActivity.this , SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        // in case you want make change in the menu bar.

        return true;
    }


    private void checkPreference () {


        String studentNamePreference = mSharedPrefs.getString(
                getString(R.string.settings_student_name_key),
                getString(R.string.settings_student_name_default));

        if (studentNamePreference.equals(getString(R.string.settings_student_name_default))) {
            Intent numbersIntent = new Intent(GpaActivity.this, LoginActivity.class);
            startActivity(numbersIntent);
            finish();
        }

    }

}
