package com.example.android.explorationgpa.settings;


import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.content.ContextCompat;

import com.example.android.explorationgpa.R;


public class SubjectOrderActivity extends AppCompatActivity {


    private static final String LOG_TAG = SubjectOrderActivity.class.getSimpleName(); // class name.

    private LinearLayout mMainLinearLayout; // the main layout of the activity.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_subject_order);


        // initialize the main linear layout that we will put inside it
        // the rest of the views in the activity.
        mMainLinearLayout = findViewById(R.id.activity_setting_subject_order_main_linear_layout);


        // display all semesters details (the subject order on the semesters).
        displayAllSemestersDetails();


    }



    /**
     * Display all semesters details (how the subject in each semester ordering).
     */
    private void displayAllSemestersDetails() {

        // Semester (2)
        displaySemester_2();

        // Semester (3)
        displaySemester_3();

        // Semester (4)
        displaySemester_4();

        // Semester (5)
        displaySemester_5();

        // Semester (6)
        displaySemester_6();

        // Semester (7)
        displaySemester_7();

        // Semester (8)
        displaySemester_8();

        // Semester (9)
        displaySemester_9();

        // Semester (10)
        displaySemester_10();

    }


    /**
     * Create a TextView contain the semester number.
     *
     *@param semesterNumber number of the semester.
     *
     *  @return TextView contain a word (Semester) with the semester number in brackets.
     */
    private TextView getSemesterNumberTextView(int semesterNumber) {

        // create ContextThemeWrapper with a specific style.
        ContextThemeWrapper themeWrapper = new ContextThemeWrapper(
                this, R.style.settings_semester_word_with_number_in_brackets);


        // create a TextView with the style above.
        TextView textView = new TextView(themeWrapper, null , 0);

        // display a word (Semester) with the semester number in brackets.
        String semesterWord = getString(R.string.semester_number_between_brackets, semesterNumber);
        textView.setText(semesterWord);


        return textView;

    }


    /**
     * Make a horizontal Linear Layout contain two parts :
     * the first part is the required subject that must student study first before stat studying
     * the subject in the second part.
     *
     * @param subjectResourceIds array contain all subject will be put in the linear layout
     *
     * @return LinearLayout contain two horizontal parts.
     */
    private LinearLayout getSubjectsLayout(int... subjectResourceIds) {


        // determine the subjectResourceIds array size.
        int arraySize = subjectResourceIds.length;

        // create a vertical layout to be a container to a TextView(s) that wil be in the left side
        // in the main LinearLayout.
        LinearLayout leftLayout = getVerticalLinearLayout();

        // determine the params (width - height) to a TextView(s) that will be in the left side.
        LinearLayout.LayoutParams leftTextViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);


        // add the TextView(s) with the subject names to the vertical LinearLayout above.
        for (int i = 0 ; i < (arraySize-1) ; i++) {

            // get the subject name resource id.
            int subjectName = subjectResourceIds[i];

            // create a TextView contain the subject name above.
            TextView leftTextView = getSubjectTextView(subjectName);

            // set the params to the TextView.
            leftTextView.setLayoutParams(leftTextViewParams);

            // put the TextView to the vertical LinearLayout.
            leftLayout.addView(leftTextView);

        }



        // get the last subject name resource id that will be in the right side in the main
        // LinearLayout.
        int LastSubjectName = subjectResourceIds[(arraySize-1)];

        // create a TextView contain the subject name above.
        TextView RightTextView = getSubjectTextView(LastSubjectName);

        // determine the params (width - height - weight) to a TextView that will be in the right side.
        LinearLayout.LayoutParams rightTextViewParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT, 1);

        // set the params to the TextView.
        RightTextView.setLayoutParams(rightTextViewParams);


        // the final LinearLayout will contain a two parts the first part is the required subject
        // that must student study first before stat studying the subject in the second part.
        LinearLayout finalLinearLayout = getHorizontalLinearLayout();


        // add the (right - left) side to the main LinearLayout.
        finalLinearLayout.addView(leftLayout);
        finalLinearLayout.addView(RightTextView);


        // return the main LinearLayout contains the (right - left) side.
        return finalLinearLayout;

    }


    /**
     * Create a horizontal linear layout with a specific states.
     *
     * @return LinearLayout with horizontal orientation.
     */
    private LinearLayout getHorizontalLinearLayout() {

        // initialize a new LinearLayout.
        LinearLayout linearLayout = new LinearLayout(this);


        // set the LinearLayout orientation to be horizontal.
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);


        // set the LinearLayout params (width - height).
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        // set the Linear Layout margin.
        int marginPx = convertDpToPx(16);
        layoutParams.setMargins(marginPx, marginPx, marginPx, marginPx);

        // set params (width - height - margin) above to the LinearLayout.
        linearLayout.setLayoutParams(layoutParams);



        // return a LinearLayout with a required states.
        return linearLayout;

    }


    /**
     * Create a vertical linear layout with a specific states.
     *
     * @return LinearLayout with vertical orientation.
     */
    private LinearLayout getVerticalLinearLayout() {

        // initialize a new LinearLayout.
        LinearLayout linearLayout = new LinearLayout(this);


        // set the LinearLayout orientation to be vertical.
        linearLayout.setOrientation(LinearLayout.VERTICAL);


        // set the LinearLayout params (width - height - weight).
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

        // set params (width - height - weight) above to the LinearLayout.
        linearLayout.setLayoutParams(layoutParams);


        // return a LinearLayout with a required states.
        return linearLayout;

    }


    /**
     * Create a TextView with a specific states to be more flexible to display the subject name.
     *
     * @param stringResourceId the subject name resource id.
     *
     *  @return a TextViw with subject name inserted to the method.
     */
    private TextView getSubjectTextView(int stringResourceId) {

        // initialize a new TextViw.
        TextView textView = new TextView(this);

        // set the subject name to the TextView.
        textView.setText(stringResourceId);

        // set params (width - height - gravity) to the TextView.
        // the (width - height) will be changed outside the method so we make it (0 - 0) just to
        // initialize the LayoutParams.
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 0);
        layoutParams.gravity = Gravity.CENTER; // layout_gravity

        // set params (width - height - gravity) above to the TextView.
        textView.setLayoutParams(layoutParams);

        // make the TextView hint the user that the view not able to display all the subject name
        // by add three dots (...) at the end of the subject name.
        textView.setEllipsize(TextUtils.TruncateAt.END);

        // make the max line is (1) to the TextView.
        textView.setMaxLines(1);

        // add padding equal (4dp) to the TextView.
        int paddingPx = convertDpToPx(4);
        textView.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);

        // set the gravity to the text to be (center_vertical) to the TextView.
        textView.setGravity(Gravity.CENTER_VERTICAL);

        // set text size (16sp)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        // set text color (Black).
        int textColor = getResources().getColor(R.color.black_color);
        textView.setTextColor(textColor);

        // set text style appear (Bold).
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);

        // make the TextViw has a border around it (look like a rectangle).
        Drawable backgroundDrawable = ContextCompat.getDrawable(this, R.drawable.border);
        textView.setBackground(backgroundDrawable);



        // if the phone that run the app use (Oreo-Android.8) or above the code below will execute.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // make the text size changes depend on the width of the TextViw:
            // - if the width not able to display all the subject name make the text size
            // decrease until fit the TextViw.
            // - max text size can be is (16sp).
            // - min text size can be is (8sp).
            // - the text size gradually decreases by (1sp).
            // - if that not enough to display the all the subject name then the Ellipsize features
            // will execute to crop the text and add a (...) in the end of the subject name.
            textView.setAutoSizeTextTypeUniformWithConfiguration(
                    8,
                    16,
                    1,
                    TypedValue.COMPLEX_UNIT_SP);

        }


        // return a TextView with a required states.
        return textView;

    }


    /**
     * Convert the dp value to the pixel value.
     *
     * @param dp the number we want convert it to px.
     *
     * @return the px value from the dp value inserted to the method.
     */
    private int convertDpToPx(int dp) {

        float pxFloat = ( dp * getResources().getDisplayMetrics().density );

        return Math.round(pxFloat);

    }







    /**
     * Display subjects ordering in the semester(2).
     */
    private void displaySemester_2() {

        // create a TextView contain a word (Semester) with brackets contain the semester number(2).
        TextView semesterWordTextView = getSemesterNumberTextView(2);

        // add the TextView above to the main LinearLayout.
        mMainLinearLayout.addView(semesterWordTextView);

        // display subject that required studying other subject(s) before start study it
        // in the semester(2).
        setMechanicsIILayout();
        setMathematicsIILayout();
        setPhysicsIILayout();

    }


    /**
     * Display subjects ordering in the semester(3).
     */
    private void displaySemester_3() {

        // create a TextView contain a word (Semester) with brackets contain the semester number(3).
        TextView semesterWordTextView = getSemesterNumberTextView(3);

        // add the TextView above to the main LinearLayout.
        mMainLinearLayout.addView(semesterWordTextView);

        // display subject that required studying other subject(s) before start study it
        // in the semester(3).
        setMathematicsIIILayout();
        setPhysicsIIILayout();
        setPhysicalChemistryLayout();
        setSedimentologyLayout();
        setComputerProgrammingLayout();
        setEnglishIILayout();

    }


    /**
     * Display subjects ordering in the semester(4).
     */
    private void displaySemester_4() {


        // create a TextView contain a word (Semester) with brackets contain the semester number(4).
        TextView semesterWordTextView = getSemesterNumberTextView(4);

        // add the TextView above to the main LinearLayout.
        mMainLinearLayout.addView(semesterWordTextView);

        // display subject that required studying other subject(s) before start study it
        // in the semester(4).
        setStructureGeologyLayout();
        setOrganicChemistryLayout();
        setDrawingAndAutoCadLayout();

    }


    /**
     * Display subjects ordering in the semester(5).
     */
    private void displaySemester_5() {

        // create a TextView contain a word (Semester) with brackets contain the semester number(7).
        TextView semesterWordTextView = getSemesterNumberTextView(5);

        // add the TextView above to the main LinearLayout.
        mMainLinearLayout.addView(semesterWordTextView);

        // display subject that required studying other subject(s) before start study it
        // in the semester(5).
        setCorrosionLayout();
        setRockPropertiesLayout();
        setEnglishIIILayout();

    }


    /**
     * Display subjects ordering in the semester(6).
     */
    private void displaySemester_6() {

        // create a TextView contain a word (Semester) with brackets contain the semester number(6).
        TextView semesterWordTextView = getSemesterNumberTextView(6);

        // add the TextView above to the main LinearLayout.
        mMainLinearLayout.addView(semesterWordTextView);

        // display subject that required studying other subject(s) before start study it
        // in the semester(6).
        setDrillingILayout();
        setFluidPropertiesLayout();
        setCrudeOilLayout();

    }


    /**
     * Display subjects ordering in the semester(7).
     */
    private void displaySemester_7() {

        // create a TextView contain a word (Semester) with brackets contain the semester number(7).
        TextView semesterWordTextView = getSemesterNumberTextView(7);

        // add the TextView above to the main LinearLayout.
        mMainLinearLayout.addView(semesterWordTextView);

        // display subject that required studying other subject(s) before start study it
        // in the semester(7).
        setComputerApplicationLayout();
        setPetroleumGeologyLayout();
        setGeophysicsILayout();
        setNaturalGasILayout();
        setHorizontalDrillingLayout();

    }


    /**
     * Display subjects ordering in the semester(8).
     */
    private void displaySemester_8() {

        // create a TextView contain a word (Semester) with brackets contain the semester number(8).
        TextView semesterWordTextView = getSemesterNumberTextView(8);

        // add the TextView above to the main LinearLayout.
        mMainLinearLayout.addView(semesterWordTextView);

        // display subject that required studying other subject(s) before start study it
        // in the semester(8).
        setEquipmentsLayout();
        setGeophysicsIILayout();
        setProductionILayout();
        setWellCompletionLayout();
        setAppliedLayout();

    }


    /**
     * Display subjects ordering in the semester(9).
     */
    private void displaySemester_9() {

        // create a TextView contain a word (Semester) with brackets contain the semester number(9).
        TextView semesterWordTextView = getSemesterNumberTextView(9);

        // add the TextView above to the main LinearLayout.
        mMainLinearLayout.addView(semesterWordTextView);

        // display subject that required studying other subject(s) before start study it
        // in the semester(9).
        setProductionIILayout();
        setWellLoggingLayout();
        setDrillingIILayout();
        setFormationStimulationLayout();

    }


    /**
     * Display subjects ordering in the semester(10).
     */
    private void displaySemester_10() {

        // create a TextView contain a word (Semester) with brackets contain the semester number(10).
        TextView semesterWordTextView = getSemesterNumberTextView(10);

        // add the TextView above to the main LinearLayout.
        mMainLinearLayout.addView(semesterWordTextView);

        // display subject that required studying other subject(s) before start study it
        // in the semester(10).
        setWellDesignLayout();
        setReservoirSimulationLayout();
        setFormationEvaluationLayout();


    }







    /**
     * Display required subjects which the student must be studied before start studying
     * (Mathematics II).
     */
    private void setMathematicsIILayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.MECHANICS_I,
                SubjectNames.MECHANICS_II);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Mechanics II).
     */
    private void setMechanicsIILayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.MATHEMATICS_I,
                SubjectNames.MATHEMATICS_II);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Physics II).
     */
    private void setPhysicsIILayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.PHYSICS_I,
                SubjectNames.PHYSICS_II);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }




    /**
     * Display required subjects which the student must be studied before start studying
     * (Mathematics III).
     */
    private void setMathematicsIIILayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.MATHEMATICS_I,
                SubjectNames.MATHEMATICS_II,
                SubjectNames.MATHEMATICS_III);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Physics III).
     */
    private void setPhysicsIIILayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.PHYSICS_I,
                SubjectNames.PHYSICS_II,
                SubjectNames.PHYSICS_III);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Physical Chemistry).
     */
    private void setPhysicalChemistryLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.GENERAL_CHEMISTRY,
                SubjectNames.PHYSICAL_CHEMISTRY);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Sedimentology & Stratigraphy - Structure Geology).
     */
    private void setSedimentologyLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_GEOLOGY,
                SubjectNames.SEDIMENTOLOGY_AND_STRATIGRAPHY);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Computer Programming).
     */
    private void setComputerProgrammingLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_COMPUTER,
                SubjectNames.COMPUTER_PROGRAMMING);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (English II).
     */
    private void setEnglishIILayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.TECHNICAL_ENGLISH_I,
                SubjectNames.TECHNICAL_ENGLISH_II);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }




    /**
     * Display required subjects which the student must be studied before start studying
     * (Structure Geology).
     */
    private void setStructureGeologyLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_GEOLOGY,
                SubjectNames.STRUCTURE_GEOLOGY);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Organic Chemistry).
     */
    private void setOrganicChemistryLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.GENERAL_CHEMISTRY,
                SubjectNames.ORGANIC_CHEMISTRY);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Engineering Drawing & AutoCAD).
     */
    private void setDrawingAndAutoCadLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.ENGINEERING_DRAWING_AND_PROJECTION,
                SubjectNames.ENGINEERING_DRAWING_AND_AUTO_CAD);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }




    /**
     * Display required subjects which the student must be studied before start studying
     * (Reservoir Rock Properties).
     */
    private void setRockPropertiesLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_GEOLOGY,
                SubjectNames.SEDIMENTOLOGY_AND_STRATIGRAPHY,
                SubjectNames.STRUCTURE_GEOLOGY,
                SubjectNames.INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                SubjectNames.RESERVOIR_ROCK_PROPERTIES);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Corrosion).
     */
    private void setCorrosionLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_MATERIAL,
                SubjectNames.CORROSION);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (English III).
     */
    private void setEnglishIIILayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.TECHNICAL_ENGLISH_I,
                SubjectNames.TECHNICAL_ENGLISH_II,
                SubjectNames.TECHNICAL_ENGLISH_III);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }




    /**
     * Display required subjects which the student must be studied before start studying
     * (Reservoir Fluid Properties).
     */
    private void setFluidPropertiesLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                SubjectNames.RESERVOIR_FLUIDS_PROPERTIES);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Drilling Engineering).
     */
    private void setDrillingILayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_GEOLOGY,
                SubjectNames.SEDIMENTOLOGY_AND_STRATIGRAPHY,
                SubjectNames.STRUCTURE_GEOLOGY,
                SubjectNames.DRILLING_ENGINEERING_I);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Crude Oil Evaluation).
     */
    private void setCrudeOilLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                SubjectNames.CRUDE_OIL_EVALUATION);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }




    /**
     * Display required subjects which the student must be studied before start studying
     * (Computer Application in petroleum Engineering).
     */
    private void setComputerApplicationLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_COMPUTER,
                SubjectNames.COMPUTER_PROGRAMMING,
                SubjectNames.COMPUTER_APPLICATION_IN_PETROLEUM_ENGINEER);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Petroleum Geology).
     */
    private void setPetroleumGeologyLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_GEOLOGY,
                SubjectNames.SEDIMENTOLOGY_AND_STRATIGRAPHY,
                SubjectNames.STRUCTURE_GEOLOGY,
                SubjectNames.PETROLEUM_GEOLOGY);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Natural Gas).
     */
    private void setNaturalGasILayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                SubjectNames.RESERVOIR_FLUIDS_PROPERTIES,
                SubjectNames.NATURAL_GAS_ENGINEERING);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Exploration Geophysics I).
     */
    private void setGeophysicsILayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_GEOLOGY,
                SubjectNames.SEDIMENTOLOGY_AND_STRATIGRAPHY,
                SubjectNames.STRUCTURE_GEOLOGY,
                SubjectNames.EXPLORATION_GEOPHYSICS_I);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Horizontal Drilling Technology).
     */
    private void setHorizontalDrillingLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_GEOLOGY,
                SubjectNames.SEDIMENTOLOGY_AND_STRATIGRAPHY,
                SubjectNames.STRUCTURE_GEOLOGY,
                SubjectNames.DRILLING_ENGINEERING_I,
                SubjectNames.HORIZONTAL_DRILLING_TECHNOLOGY);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }




    /**
     * Display required subjects which the student must be studied before start studying
     * (Drilling and Production Equipments).
     */
    private void setEquipmentsLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                SubjectNames.DRILLING_AND_PRODUCTION_EQUIPMENTS);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Petroleum Production Engineering I).
     */
    private void setProductionILayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                SubjectNames.PETROLEUM_PRODUCTION_ENGINEERING_I);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Exploration Geophysics II).
     */
    private void setGeophysicsIILayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_GEOLOGY,
                SubjectNames.SEDIMENTOLOGY_AND_STRATIGRAPHY,
                SubjectNames.STRUCTURE_GEOLOGY,
                SubjectNames.EXPLORATION_GEOPHYSICS_I,
                SubjectNames.EXPLORATION_GEOPHYSICS_II);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (WellCompletion).
     */
    private void setWellCompletionLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_GEOLOGY,
                SubjectNames.SEDIMENTOLOGY_AND_STRATIGRAPHY,
                SubjectNames.STRUCTURE_GEOLOGY,
                SubjectNames.INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                SubjectNames.RESERVOIR_ROCK_PROPERTIES,
                SubjectNames.DRILLING_ENGINEERING_I,
                SubjectNames.WELL_COMPLETION_ENGINEERING);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Applied Reservoir Engineering).
     */
    private void setAppliedLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_GEOLOGY,
                SubjectNames.SEDIMENTOLOGY_AND_STRATIGRAPHY,
                SubjectNames.STRUCTURE_GEOLOGY,
                SubjectNames.INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                SubjectNames.RESERVOIR_ROCK_PROPERTIES,
                SubjectNames.RESERVOIR_FLUIDS_PROPERTIES,
                SubjectNames.APPLIED_RESERVOIR_ENGINEERING);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }




    /**
     * Display required subjects which the student must be studied before start studying
     * (Petroleum Production Engineering II).
     */
    private void setProductionIILayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                SubjectNames.PETROLEUM_PRODUCTION_ENGINEERING_I,
                SubjectNames.PETROLEUM_PRODUCTION_ENGINEERING_II);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (WellLogging).
     */
    private void setWellLoggingLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_GEOLOGY,
                SubjectNames.SEDIMENTOLOGY_AND_STRATIGRAPHY,
                SubjectNames.STRUCTURE_GEOLOGY,
                SubjectNames.INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                SubjectNames.RESERVOIR_ROCK_PROPERTIES,
                SubjectNames.DRILLING_ENGINEERING_I,
                SubjectNames.WELL_LOGGING);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Drilling Engineering).
     */
    private void setDrillingIILayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_GEOLOGY,
                SubjectNames.SEDIMENTOLOGY_AND_STRATIGRAPHY,
                SubjectNames.STRUCTURE_GEOLOGY,
                SubjectNames.DRILLING_ENGINEERING_I,
                SubjectNames.DRILLING_ENGINEERING_II);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Computer Programming).
     */
    private void setFormationStimulationLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_GEOLOGY,
                SubjectNames.SEDIMENTOLOGY_AND_STRATIGRAPHY,
                SubjectNames.STRUCTURE_GEOLOGY,
                SubjectNames.INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                SubjectNames.RESERVOIR_ROCK_PROPERTIES,
                SubjectNames.RESERVOIR_FLUIDS_PROPERTIES,
                SubjectNames.APPLIED_RESERVOIR_ENGINEERING,
                SubjectNames.FORMATION_STIMULATION);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }




    /**
     * Display required subjects which the student must be studied before start studying
     * (Well Design).
     */
    private void setWellDesignLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                SubjectNames.DRILLING_AND_PRODUCTION_EQUIPMENTS,
                SubjectNames.WELL_DESIGN);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Reservoir Simulation).
     */
    private void setReservoirSimulationLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_COMPUTER,
                SubjectNames.COMPUTER_PROGRAMMING,
                SubjectNames.RESERVOIR_SIMULATION);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }

    /**
     * Display required subjects which the student must be studied before start studying
     * (Formation Evaluation).
     */
    private void setFormationEvaluationLayout() {

        // make a linear layout contain two parts :
        // first one : the required subjects must be studied.
        // second one : the subject that finished the part above allow the student to start study it.
        LinearLayout linearLayout = getSubjectsLayout(
                SubjectNames.INTRODUCTION_TO_GEOLOGY,
                SubjectNames.SEDIMENTOLOGY_AND_STRATIGRAPHY,
                SubjectNames.STRUCTURE_GEOLOGY,
                SubjectNames.INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                SubjectNames.RESERVOIR_ROCK_PROPERTIES,
                SubjectNames.CRUDE_OIL_EVALUATION,
                SubjectNames.WELL_LOGGING,
                SubjectNames.FORMATION_EVALUATION);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }







    // inner class contain all subject names (resource ids).
    private static class SubjectNames {


        // all subject in semester (1).
        public static final int MATHEMATICS_I = R.string.semester_1_subject_1;
        public static final int MECHANICS_I = R.string.semester_1_subject_2;
        public static final int PHYSICS_I = R.string.semester_1_subject_3;
        public static final int GENERAL_CHEMISTRY = R.string.semester_1_subject_4;
        public static final int INTRODUCTION_TO_GEOLOGY = R.string.semester_1_subject_5;
        public static final int INTRODUCTION_TO_COMPUTER = R.string.semester_1_subject_6;
        public static final int ENGINEERING_DRAWING_AND_PROJECTION = R.string.semester_1_subject_7;


        // all subject in semester (2).
        public static final int MATHEMATICS_II = R.string.semester_2_subject_1;
        public static final int MECHANICS_II = R.string.semester_2_subject_2;
        public static final int PHYSICS_II = R.string.semester_2_subject_3;
        public static final int INTRODUCTION_TO_PRODUCTION_ENGINEERING = R.string.semester_2_subject_4;
        public static final int HISTORY_OF_ENGINEERING = R.string.semester_1_subject_1;
        public static final int TECHNICAL_ENGLISH_I = R.string.semester_2_subject_6;


        // all subject in semester (3).
        public static final int MATHEMATICS_III = R.string.semester_3_subject_1;
        public static final int PHYSICS_III = R.string.semester_3_subject_2;
        public static final int PHYSICAL_CHEMISTRY = R.string.semester_3_subject_3;
        public static final int STRENGTH_OF_MATERIALS = R.string.semester_3_subject_4;
        public static final int SEDIMENTOLOGY_AND_STRATIGRAPHY = R.string.semester_3_subject_5;
        public static final int COMPUTER_PROGRAMMING = R.string.semester_3_subject_6;
        public static final int TECHNICAL_ENGLISH_II = R.string.semester_3_subject_7;


        // all subject in semester (4).
        public static final int ORGANIC_CHEMISTRY = R.string.semester_4_subject_1;
        public static final int STRUCTURE_GEOLOGY = R.string.semester_4_subject_2;
        public static final int PLAN_SURVEYING_AND_TOPOGRAPHY = R.string.semester_4_subject_3;
        public static final int INTRODUCTION_TO_MATERIAL = R.string.semester_4_subject_4;
        public static final int INTRODUCTION_TO_PETROLEUM_ENGINEERING = R.string.semester_4_subject_5;
        public static final int ENGINEERING_DRAWING_AND_AUTO_CAD = R.string.semester_4_subject_6;
        public static final int HUMAN_RIGHTS = R.string.semester_4_subject_7;


        // all subject in semester (5).
        public static final int ANALYTICAL_CHEMISTRY = R.string.semester_5_subject_1;
        public static final int FLUID_MECHANICS = R.string.semester_5_subject_2;
        public static final int RESERVOIR_ROCK_PROPERTIES = R.string.semester_5_subject_3;
        public static final int FUNDAMENTAL_OF_ELECTRICAL_ENGINEERING = R.string.semester_5_subject_4;
        public static final int AUTOMATIC_CONTROL = R.string.semester_5_subject_5;
        public static final int GAS_TREATMENT = R.string.semester_5_subject_6;
        public static final int TECHNICAL_ENGLISH_III = R.string.semester_5_subject_7;


        // all subject in semester (6).
        public static final int DRILLING_ENGINEERING_I = R.string.semester_6_subject_1;
        public static final int ROCK_MECHANICS = R.string.semester_6_subject_2;
        public static final int THERMODYNAMICS = R.string.semester_6_subject_3;
        public static final int RESERVOIR_FLUIDS_PROPERTIES = R.string.semester_6_subject_4;
        public static final int CRUDE_OIL_EVALUATION = R.string.semester_6_subject_5;
        public static final int ECONOMICS_AND_MANAGEMENT = R.string.semester_6_subject_6;


        // all subject in semester (7).
        public static final int EXPLORATION_GEOPHYSICS_I = R.string.semester_7_subject_1;
        public static final int HORIZONTAL_DRILLING_TECHNOLOGY = R.string.semester_7_subject_2;
        public static final int PETROLEUM_GEOLOGY = R.string.semester_7_subject_3;
        public static final int WELL_LOGGING = R.string.semester_7_subject_4;
        public static final int NATURAL_GAS_ENGINEERING = R.string.semester_7_subject_5;
        public static final int SAFETY_AND_LABORS_LAW = R.string.semester_7_subject_6;


        // all subject in semester (8).
        public static final int EXPLORATION_GEOPHYSICS_II = R.string.semester_8_subject_1;
        public static final int PETROLEUM_PRODUCTION_ENGINEERING_I = R.string.semester_8_subject_2;
        public static final int DRILLING_AND_PRODUCTION_EQUIPMENTS = R.string.semester_8_subject_3;
        public static final int APPLIED_RESERVOIR_ENGINEERING = R.string.semester_8_subject_4;
        public static final int WELL_COMPLETION_ENGINEERING = R.string.semester_8_subject_5;
        public static final int ENVIRONMENTAL_RISK_ASSESSMENT_IN_PETROLEUM_INDUSTRY = R.string.semester_8_subject_6;


        // all subject in semester (9).
        public static final int PETROLEUM_PRODUCTION_ENGINEERING_II = R.string.semester_9_subject_1;
        public static final int DRILLING_ENGINEERING_II = R.string.semester_9_subject_2;
        public static final int WELL_TESTING = R.string.semester_9_subject_3;
        public static final int COMPUTER_APPLICATION_IN_PETROLEUM_ENGINEER = R.string.semester_9_subject_4;
        public static final int ENHANCED_OIL_RECOVERY = R.string.semester_9_subject_5;
        public static final int ENGINEERING_ESTHETES = R.string.semester_9_subject_6;
        public static final int SENIOR_PROJECT_I = R.string.semester_9_subject_7;


        // all subject in semester (10).
        public static final int WELL_DESIGN = R.string.semester_10_subject_1;
        public static final int FORMATION_EVALUATION = R.string.semester_10_subject_2;
        public static final int TRANSPORTING_AND_STORAGE_OF_OIL_AND_GAS = R.string.semester_10_subject_3;
        public static final int RESERVOIR_SIMULATION = R.string.semester_10_subject_4;
        public static final int FORMATION_STIMULATION = R.string.semester_10_subject_5;
        public static final int MANAGEMENT_SCIENCE_AND_DETERMINATION_DECISION_MODELS = R.string.semester_10_subject_6;
        public static final int SENIOR_PROJECT_II = R.string.semester_10_subject_7;


        // (others subject).
        public static final int CORROSION = R.string.semester_0_subject_1;


    }



}
