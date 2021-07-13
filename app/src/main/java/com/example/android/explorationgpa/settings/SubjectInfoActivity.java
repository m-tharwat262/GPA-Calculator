package com.example.android.explorationgpa.settings;


import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.android.explorationgpa.R;
import com.example.android.explorationgpa.SemesterInfo;

import java.util.ArrayList;


public class SubjectInfoActivity extends AppCompatActivity {


    private static final String LOG_TAG = SubjectInfoActivity.class.getSimpleName(); // the class name.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_subject_info);


        // the main linear layout in the activity that we will add to it the semester items.
        LinearLayout mSemesterItemsContainer = findViewById(R.id.activity_setting_subject_details);


        // make Semester items with all details about the subjects inside each semester and add
        // those items to the main linear layout above.
        for(int i = 1 ; i < 11 ; i++) {

            // inflate the semester item.
            View semesterItem = View.inflate(this,R.layout.settings_semester_item, null);


            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

            String subjectLanguage = preferences.getString(getString(R.string.settings_subject_language_key),
                    getString(R.string.settings_subject_language_default));

            if ( !(subjectLanguage.equals(getString(R.string.settings_subject_language_default))) ) {
                LinearLayout mainLinearLayout = semesterItem.findViewById(R.id.settings_semester_item_main_layout);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    mainLinearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                }
            }

            

            // show the semester number in the item.
            TextView semesterNumberTextView = semesterItem.findViewById(R.id.settings_semester_item_semester_number);
            String semesterNumber = getString(R.string.semester_number_between_brackets, i);
            semesterNumberTextView.setText(semesterNumber);


            // get the ArrayList of SubjectInfoObject contain the subject info in the semester
            // that info (subject name - subject hours - subject success degree).
            ArrayList<SubjectInfoObject> subjectInfoObjects = getSubjectInfoObjects(i);


            // show all subject info in the semester as subject items in a list view.
            ListView subjectListView = semesterItem.findViewById(R.id.settings_semester_item_ListView);
            SettingSemesterAdapter semesterAdapter = new SettingSemesterAdapter(this, subjectInfoObjects);
            subjectListView.setAdapter(semesterAdapter);


            // add the semester item to the main linear layout in the activity.
            mSemesterItemsContainer.addView(semesterItem);

        }


    }



    /**
     * Get ArrayList of SubjectInfoObject contain all subject info (name - hours - required degree
     * to pass in it) in the semester that inserted to the method.
     *
     * @param semesterNumber the semester number we want get all info about the subjects inside it.
     *
     * @return ArrayList of SubjectInfoObject contain all subject info.
     */
    private ArrayList<SubjectInfoObject> getSubjectInfoObjects (int semesterNumber) {


        // initialize an ArrayList that will contain SubjectInfoObjects.
        ArrayList<SubjectInfoObject> subjectInfoObjects = new ArrayList<>();

        // get the subject names.
        int[] allSubjectNamesResourceIds = SemesterInfo.getSubjectsOfSemester(this, semesterNumber);

        // get the subject hours.
        double[] allSubjectHours = SemesterInfo.getHoursForSemester(semesterNumber);

        // get the required degree to pass in the subject.
        double[] allSubjectsSuccessDegree = SemesterInfo.getSuccessDegreeForSemester(semesterNumber);

        // array size.
        int arraySize = allSubjectNamesResourceIds.length;


        // get each subject info (name - hours - success degree) and make from it a SubjectInfoObject
        // and put that object in the ArrayList.
        for (int i = 0 ; i < arraySize ; i++) {

            // get the subject details (name - hours - success degree) in the position (i).
            int subjectNameResourceIds = allSubjectNamesResourceIds[i];
            double subjectHour = allSubjectHours[i];
            double subjectSuccessDegree = allSubjectsSuccessDegree[i];

            // create an object of SubjectInfoObject contain the subject details.
            SubjectInfoObject subjectInfoObject = new SubjectInfoObject(subjectNameResourceIds, subjectHour, subjectSuccessDegree );

            // add the object above to the ArrayList.
            subjectInfoObjects.add(subjectInfoObject);

        }


        // return ArrayList contain all subject info in the semester.
        return subjectInfoObjects;

    }



}
