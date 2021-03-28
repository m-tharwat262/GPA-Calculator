package com.example.android.explorationgpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class InfoActivity extends AppCompatActivity {


    private static final String LOG_TAG = InfoActivity.class.getSimpleName();

    private EditText mNameEditText, mIdEditText;
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
        mIdEditText = (EditText) findViewById(R.id.activity_info_student_id);
        mLevelSpinner = (Spinner) findViewById(R.id.activity_info_spinner_year);
        mRadioGroup = (RadioGroup) findViewById(R.id.activity_info_radio_buttons);
        mFirstSemester = (RadioButton) findViewById(R.id.activity_info_radio_first);
        mSecondSemester = (RadioButton) findViewById(R.id.activity_info_radio_second);


        setupYearSpinner();
        setupSemesterRadio();
        checkPreferences();

        // click on the next button send intent to open the AddSemesterActivity
        Button nextButton = (Button) findViewById(R.id.activity_info_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // open the AddSemesterActivity
                Intent intent = new Intent(InfoActivity.this, AddSemesterActivity.class);
                intent = makeContentOfIntent(intent); // get the intent contain the info which will be send
                startActivity(intent);

            }
        });
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
        mIdEditText.setText(studentIdPreference);
    }


    /**
     * put the information that should be send from that activity to the next one
     * like (student name & id - number of the year & term).
     *
     * @param intent for the intent which will be send
     *
     * @return intent include information
     */
    private Intent makeContentOfIntent(Intent intent) {

        String studentName = mNameEditText.getText().toString(); // get student name from EditText.
        String studentIdString = mIdEditText.getText().toString(); // get student id from EditText.
        int studentId = Integer.parseInt(studentIdString); // convert the id form string to integer

        // put the information which will be send inside the intent
        intent.putExtra("student_name", studentName); // for student name
        intent.putExtra("student_id", studentId); // for student id
        intent.putExtra("year_number", mYear); // for number of the year
        intent.putExtra("term_number",mTerm); // for number of the term

        return intent;
    }

}
