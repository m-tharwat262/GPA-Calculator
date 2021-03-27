package com.example.android.explorationgpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class InfoActivity extends AppCompatActivity {

    private EditText mNameEditText, mCodeEditText;
    private Spinner mLevelSpinner;
    private ArrayAdapter levelSpinnerAdapter;
    private RadioGroup mRadioGroup;
    private RadioButton mFirstSemester, mSecondSemester;

    private int mYear;
    private int mTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mNameEditText = (EditText) findViewById(R.id.activity_info_student_name);
        mCodeEditText = (EditText) findViewById(R.id.activity_info_student_id);
        mLevelSpinner = (Spinner) findViewById(R.id.activity_info_spinner_year);
        mRadioGroup = (RadioGroup) findViewById(R.id.activity_info_radio_buttons);
        mFirstSemester = (RadioButton) findViewById(R.id.activity_info_radio_first);
        mSecondSemester = (RadioButton) findViewById(R.id.activity_info_radio_second);


        setupYearSpinner();
        setupSemesterRadio();
        checkPreferences();


    }

    /**
     * setup the spinner fot years (0-1-2-3-4) and know which year the user select
     */
    private void setupYearSpinner() {

        levelSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_level_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        levelSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mLevelSpinner.setAdapter(levelSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.spinner_year_zero))) {
                        mYear = 0; // primary year
                    } else if (selection.equals(getString(R.string.spinner_year_one))) {
                        mYear = 1; // firs year
                    } else if (selection.equals(getString(R.string.spinner_year_two))) {
                        mYear = 2; // second year
                    } else if (selection.equals(getString(R.string.spinner_year_three))) {
                        mYear = 3; // third year
                    } else {
                        mYear = 4; // four year
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mYear = 0;
            }
        });
    }



    /**
     * setup the two radio buttons fot terms (1-2) and know which radio buttons the user select
     */
    private void setupSemesterRadio() {

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // find which radio button is selected
                if(checkedId == R.id.activity_info_radio_first) {
                    mTerm = 1;
                } else if (checkedId == R.id.activity_info_radio_second){
                    mTerm = 2;
                }
            }

        });

    }



    /**
     * setup the EditText field to automatically write the user data (name and id) from the preference settings
     */
    private void checkPreferences () {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // get the student name from the preference settings
        String studentNamePreference = sharedPrefs.getString(
                getString(R.string.settings_student_name_key),
                getString(R.string.settings_student_name_default));

        // get the student id from the preference settings
        String studentIdPreference = sharedPrefs.getString(
                getString(R.string.settings_student_id_key),
                getString(R.string.settings_student_id_default));

        // make the EditTexts write the student name & id that saved in the prefernce
        mNameEditText.setText(studentNamePreference);
        mCodeEditText.setText(studentIdPreference);
    }

}
