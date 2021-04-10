package com.example.android.explorationgpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class AddSemesterActivity extends AppCompatActivity {

    private static final String LOG_TAG = AddSemesterActivity.class.getSimpleName(); // activity name.

    private TextView nameTextView; // for the basic info part in the layout.
    private TextView idTextView; // for the basic info part in the layout.
    private TextView semesterTextView; // for the basic info part in the layout.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_semester);

        // get the specific view from the xml layout.
        nameTextView = (TextView) findViewById(R.id.activity_add_semester_name);
        idTextView = (TextView) findViewById(R.id.activity_add_semester_id);
        semesterTextView = (TextView) findViewById(R.id.activity_add_semester_number_of_semester);

        // get the information which the intent (from infoActivity) that open that activity.
        Intent intent = getIntent();
        String studentName = intent.getStringExtra("student_name"); // for student name.
        int studentId = intent.getIntExtra("student_id",0); // for student id.
        int yearNumber = intent.getIntExtra("year_number", 0); // for number of the year.
        int termNumber = intent.getIntExtra("term_number", 0); // for number of the term.
        Log.i(LOG_TAG, "intent come from InfoActivity contain : " + studentName + "  " + studentId + " " + yearNumber + " " + termNumber);



        // show the user basic data on the top of the layout.
        setupTheBasicInfo(studentName, studentId, yearNumber, termNumber);

        // show the year and term name in the layout.
        setupTheYearAndTerm(yearNumber, termNumber);

        // fix the overlap between the views in the layout.
        setupTheLayoutShadows();



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


    /**
     * display the exact year and term name in the layout.
     *
     * @param yearNumber number of the year that the user want calculate the gpa in.
     * @param termNumber number of the term that the user want calculate the gpa in.
     */
    private void setupTheYearAndTerm(int yearNumber, int termNumber) {

        int yearNameResourceId;
        int termNameResourceId;

        // to get the resource id for the year name by the know the year number.
        switch (yearNumber) {
            case 1:
                yearNameResourceId = R.string.year_one;
                break;
            case 2:
                yearNameResourceId = R.string.year_two;
                break;
            case 3:
                yearNameResourceId = R.string.year_three;
                break;
            case 4:
                yearNameResourceId = R.string.year_four;
                break;
            default:
                yearNameResourceId = R.string.year_zero;

        }

        // display the year name in the layout.
        String yearName = getString(yearNameResourceId);
        TextView yearNameTextView = (TextView) findViewById(R.id.activity_add_semester_year_name);
        yearNameTextView.setText(yearName);


        // to get the resource id for the term name by the know the term number.
        if (termNumber == 1) {
            termNameResourceId = R.string.term_first;
        } else {
            termNameResourceId = R.string.term_second;
        }

        // display the term name inside bracts in the layout.
        String termName = getString(termNameResourceId);
        String termNameInBracts = " (" + termName + ")";
        TextView termNameTextView = (TextView) findViewById(R.id.activity_add_semester_term_name);
        termNameTextView.setText(termNameInBracts);

    }


    /**
     * Fix the overlap between the views (the basic info part & done button) and other views in the
     * layout by know the height of the views and make shadows behind it.
     */
    private void setupTheLayoutShadows() {

        // determine linear that contain the basic info part.
        final LinearLayout basicInfoLayout = findViewById(R.id.activity_add_semester_linear_for_student_info);

        // to get the height after the activity layout inflated.
        ViewTreeObserver observerForBasicInfoLayout = basicInfoLayout.getViewTreeObserver();
        observerForBasicInfoLayout.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                // setup the height of the shadow to be equal the height of the basic info part.
                int heightOfBasicInfoLayout = basicInfoLayout.getHeight();
                View shadowViewOfBasicInfo = findViewById(R.id.activity_add_semester_shadow_for_linear_basic_info);
                ViewGroup.LayoutParams paramsForBasicInfoLayout =  shadowViewOfBasicInfo.getLayoutParams();
                paramsForBasicInfoLayout.height = heightOfBasicInfoLayout;
                shadowViewOfBasicInfo.setLayoutParams(paramsForBasicInfoLayout);

            }
        });


        // determine done button in the activity layout.
        final Button doneButton = (Button) findViewById(R.id.activity_add_semester_button_done);

        // to get the height after the activity layout inflated.
        ViewTreeObserver observerForButton = doneButton.getViewTreeObserver();
        observerForButton.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                // setup the height of the shadow to be equal the height of the done button.
                int heightOfDoneButtonHeight = doneButton.getHeight();
                View shadowViewOfDoneButton = findViewById(R.id.activity_add_semester_shadow_for_done_button);
                ViewGroup.LayoutParams paramsForDoneButton =  shadowViewOfDoneButton.getLayoutParams();
                paramsForDoneButton.height = heightOfDoneButtonHeight;
                shadowViewOfDoneButton.setLayoutParams(paramsForDoneButton);

            }
        });


    }


}
