package com.example.android.explorationgpa.settings;


import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import com.example.android.explorationgpa.R;
import com.example.android.explorationgpa.SemesterInfo;


public class SubjectOrderActivity extends AppCompatActivity {


    // all subject in semester (1).
    public int MATHEMATICS_I;
    public int MECHANICS_I;
    public int PHYSICS_I;
    public int GENERAL_CHEMISTRY;
    public int INTRODUCTION_TO_GEOLOGY;
    public int INTRODUCTION_TO_COMPUTER;
    public int ENGINEERING_DRAWING_AND_PROJECTION;

    // all subject in semester (2).
    public int MATHEMATICS_II;
    public int MECHANICS_II;
    public int PHYSICS_II;
    public int INTRODUCTION_TO_PRODUCTION_ENGINEERING;
    public int HISTORY_OF_ENGINEERING;
    public int TECHNICAL_ENGLISH_I;

    // all subject in semester (3).
    public int MATHEMATICS_III;
    public int PHYSICS_III;
    public int PHYSICAL_CHEMISTRY;
    public int STRENGTH_OF_MATERIALS;
    public int SEDIMENTOLOGY_AND_STRATIGRAPHY;
    public int COMPUTER_PROGRAMMING;
    public int TECHNICAL_ENGLISH_II;

    // all subject in semester (4).
    public int ORGANIC_CHEMISTRY;
    public int STRUCTURE_GEOLOGY;
    public int PLAN_SURVEYING_AND_TOPOGRAPHY;
    public int INTRODUCTION_TO_MATERIAL;
    public int INTRODUCTION_TO_PETROLEUM_ENGINEERING;
    public int ENGINEERING_DRAWING_AND_AUTO_CAD;
    public int HUMAN_RIGHTS;

    // all subject in semester (5).
    public int ANALYTICAL_CHEMISTRY;
    public int FLUID_MECHANICS;
    public int RESERVOIR_ROCK_PROPERTIES;
    public int FUNDAMENTAL_OF_ELECTRICAL_ENGINEERING;
    public int AUTOMATIC_CONTROL;
    public int GAS_TREATMENT;
    public int TECHNICAL_ENGLISH_III;

    // all subject in semester (6).
    public int DRILLING_ENGINEERING_I ;
    public int ROCK_MECHANICS;
    public int THERMODYNAMICS ;
    public int RESERVOIR_FLUIDS_PROPERTIES;
    public int CRUDE_OIL_EVALUATION;
    public int ECONOMICS_AND_MANAGEMENT;

    // all subject in semester (7).
    public int EXPLORATION_GEOPHYSICS_I;
    public int HORIZONTAL_DRILLING_TECHNOLOGY;
    public int PETROLEUM_GEOLOGY;
    public int WELL_LOGGING;
    public int NATURAL_GAS_ENGINEERING;
    public int SAFETY_AND_LABORS_LAW;

    // all subject in semester (8).
    public int EXPLORATION_GEOPHYSICS_II;
    public int PETROLEUM_PRODUCTION_ENGINEERING_I;
    public int DRILLING_AND_PRODUCTION_EQUIPMENTS;
    public int APPLIED_RESERVOIR_ENGINEERING;
    public int WELL_COMPLETION_ENGINEERING;
    public int ENVIRONMENTAL_RISK_ASSESSMENT_IN_PETROLEUM_INDUSTRY;

    // all subject in semester (9).
    public int PETROLEUM_PRODUCTION_ENGINEERING_II;
    public int DRILLING_ENGINEERING_II;
    public int WELL_TESTING ;
    public int COMPUTER_APPLICATION_IN_PETROLEUM_ENGINEER;
    public int ENHANCED_OIL_RECOVERY;
    public int ENGINEERING_ESTHETES;
    public int SENIOR_PROJECT_I;

    // all subject in semester (10).
    public int WELL_DESIGN;
    public int FORMATION_EVALUATION;
    public int TRANSPORTING_AND_STORAGE_OF_OIL_AND_GAS;
    public int RESERVOIR_SIMULATION;
    public int FORMATION_STIMULATION;
    public int MANAGEMENT_SCIENCE_AND_DETERMINATION_DECISION_MODELS;
    public int SENIOR_PROJECT_II;

    // (others subject).
    public int CORROSION = R.string.semester_0_subject_1_english;


    private static final String LOG_TAG = SubjectOrderActivity.class.getSimpleName(); // class name.

    private LinearLayout mMainLinearLayout; // the main layout of the activity.

    private boolean isEnglishLanguage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_subject_order);


        setSubjectNames();

        isEnglishLanguage = setSubjectLanguagePreference();

        Log.i(LOG_TAG, "the variable isEnglish has value  :  " + isEnglishLanguage);


        displayHintView();


        // initialize the main linear layout that we will put inside it
        // the rest of the views in the activity.
        mMainLinearLayout = findViewById(R.id.activity_setting_subject_order_main_linear_layout);


        // display all semesters details (the subject order on the semesters).
        displayAllSemestersDetails();


    }



    private void displayHintView() {


        TextView hintTextView = findViewById(R.id.activity_setting_subject_order_hint_text_view);

        String left = getString(R.string.word_left);
        String right = getString(R.string.word_right);

        String hintText;
        if (isEnglishLanguage) {
            hintText = getString(R.string.explain_the_subject_order, left, right);
        } else {
            hintText = getString(R.string.explain_the_subject_order, right, left);
        }

        hintTextView.setText(hintText);

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

        if (!isEnglishLanguage) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                finalLinearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }


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


    private boolean setSubjectLanguagePreference() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String subjectLanguage = preferences.getString(getString(R.string.settings_subject_language_key),
                getString(R.string.settings_subject_language_default));


        if ( (subjectLanguage.equals(getString(R.string.settings_subject_language_default))) ) {

            return true;

        }

        return false;

    }






    private void setSubjectNames() {


        //
        int[] SEMESTER_1 = SemesterInfo.getSubjectsOfSemester(this, 1);
        int[] SEMESTER_2 = SemesterInfo.getSubjectsOfSemester(this, 2);
        int[] SEMESTER_3 = SemesterInfo.getSubjectsOfSemester(this, 3);
        int[] SEMESTER_4 = SemesterInfo.getSubjectsOfSemester(this, 4);
        int[] SEMESTER_5 = SemesterInfo.getSubjectsOfSemester(this, 5);
        int[] SEMESTER_6 = SemesterInfo.getSubjectsOfSemester(this, 6);
        int[] SEMESTER_7 = SemesterInfo.getSubjectsOfSemester(this, 7);
        int[] SEMESTER_8 = SemesterInfo.getSubjectsOfSemester(this, 8);
        int[] SEMESTER_9 = SemesterInfo.getSubjectsOfSemester(this, 9);
        int[] SEMESTER_10 = SemesterInfo.getSubjectsOfSemester(this, 10);



        // all subject in semester (1).
        MATHEMATICS_I = SEMESTER_1[0];
        MECHANICS_I = SEMESTER_1[1];
        PHYSICS_I = SEMESTER_1[2];
        GENERAL_CHEMISTRY = SEMESTER_1[3];
        INTRODUCTION_TO_GEOLOGY = SEMESTER_1[4];
        INTRODUCTION_TO_COMPUTER = SEMESTER_1[5];
        ENGINEERING_DRAWING_AND_PROJECTION = SEMESTER_1[6];

        // all subject in semester (2).
        MATHEMATICS_II = SEMESTER_2[0];
        MECHANICS_II = SEMESTER_2[1];
        PHYSICS_II = SEMESTER_2[2];
        INTRODUCTION_TO_PRODUCTION_ENGINEERING = SEMESTER_2[3];
        HISTORY_OF_ENGINEERING = SEMESTER_2[4];
        TECHNICAL_ENGLISH_I = SEMESTER_2[5];

        // all subject in semester (3).
        MATHEMATICS_III = SEMESTER_3[0];
        PHYSICS_III = SEMESTER_3[1];
        PHYSICAL_CHEMISTRY = SEMESTER_3[2];
        STRENGTH_OF_MATERIALS = SEMESTER_3[3];
        SEDIMENTOLOGY_AND_STRATIGRAPHY = SEMESTER_3[4];
        COMPUTER_PROGRAMMING = SEMESTER_3[5];
        TECHNICAL_ENGLISH_II = SEMESTER_3[6];

        // all subject in semester (4).
        ORGANIC_CHEMISTRY = SEMESTER_4[0];
        STRUCTURE_GEOLOGY = SEMESTER_4[1];
        PLAN_SURVEYING_AND_TOPOGRAPHY = SEMESTER_4[2];
        INTRODUCTION_TO_MATERIAL = SEMESTER_4[3];
        INTRODUCTION_TO_PETROLEUM_ENGINEERING = SEMESTER_4[4];
        ENGINEERING_DRAWING_AND_AUTO_CAD = SEMESTER_4[5];
        HUMAN_RIGHTS = SEMESTER_4[6];

        // all subject in semester (5).
        ANALYTICAL_CHEMISTRY = SEMESTER_5[0];
        FLUID_MECHANICS = SEMESTER_5[1];
        RESERVOIR_ROCK_PROPERTIES = SEMESTER_5[2];
        FUNDAMENTAL_OF_ELECTRICAL_ENGINEERING = SEMESTER_5[3];
        AUTOMATIC_CONTROL = SEMESTER_5[4];
        GAS_TREATMENT = SEMESTER_5[5];
        TECHNICAL_ENGLISH_III = SEMESTER_5[6];

        // all subject in semester (6).
        DRILLING_ENGINEERING_I = SEMESTER_6[0];
        ROCK_MECHANICS = SEMESTER_6[1];
        THERMODYNAMICS = SEMESTER_6[2];
        RESERVOIR_FLUIDS_PROPERTIES = SEMESTER_6[3];
        CRUDE_OIL_EVALUATION = SEMESTER_6[4];
        ECONOMICS_AND_MANAGEMENT = SEMESTER_6[5];

        // all subject in semester (7).
        EXPLORATION_GEOPHYSICS_I = SEMESTER_7[0];
        HORIZONTAL_DRILLING_TECHNOLOGY = SEMESTER_7[1];
        PETROLEUM_GEOLOGY = SEMESTER_7[2];
        WELL_LOGGING = SEMESTER_7[3];
        NATURAL_GAS_ENGINEERING = SEMESTER_7[4];
        SAFETY_AND_LABORS_LAW = SEMESTER_7[5];

        // all subject in semester (8).
        EXPLORATION_GEOPHYSICS_II = SEMESTER_8[0];
        PETROLEUM_PRODUCTION_ENGINEERING_I = SEMESTER_8[1];
        DRILLING_AND_PRODUCTION_EQUIPMENTS = SEMESTER_8[2];
        APPLIED_RESERVOIR_ENGINEERING = SEMESTER_8[3];
        WELL_COMPLETION_ENGINEERING = SEMESTER_8[4];
        ENVIRONMENTAL_RISK_ASSESSMENT_IN_PETROLEUM_INDUSTRY = SEMESTER_8[5];

        // all subject in semester (9).
        PETROLEUM_PRODUCTION_ENGINEERING_II = SEMESTER_9[0];
        DRILLING_ENGINEERING_II = SEMESTER_9[1];
        WELL_TESTING = SEMESTER_9[2];
        COMPUTER_APPLICATION_IN_PETROLEUM_ENGINEER = SEMESTER_9[3];
        ENHANCED_OIL_RECOVERY = SEMESTER_9[4];
        ENGINEERING_ESTHETES = SEMESTER_9[5];
        SENIOR_PROJECT_I = SEMESTER_9[6];

        // all subject in semester (10).
        WELL_DESIGN = SEMESTER_10[0];
        FORMATION_EVALUATION = SEMESTER_10[1];
        TRANSPORTING_AND_STORAGE_OF_OIL_AND_GAS = SEMESTER_10[2];
        RESERVOIR_SIMULATION = SEMESTER_10[3];
        FORMATION_STIMULATION = SEMESTER_10[4];
        MANAGEMENT_SCIENCE_AND_DETERMINATION_DECISION_MODELS = SEMESTER_10[5];
        SENIOR_PROJECT_II = SEMESTER_10[6];

        // (others subject).
        if (isEnglishLanguage) {
            CORROSION = R.string.semester_0_subject_1_english;
        } else {
            CORROSION = R.string.semester_0_subject_1_arabic;
        }


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
        setEnglishIILayout();
        setSedimentologyLayout();
        setComputerProgrammingLayout();

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
        setOrganicChemistryLayout();
        setStructureGeologyLayout();
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
        setEnglishIIILayout();
        setRockPropertiesLayout();
        setCorrosionLayout();

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
        setFluidPropertiesLayout();
        setCrudeOilLayout();
        setDrillingILayout();

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
        setNaturalGasILayout();
        setComputerApplicationLayout();
        setPetroleumGeologyLayout();
        setGeophysicsILayout();
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
        setProductionILayout();
        setEquipmentsLayout();
        setGeophysicsIILayout();
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
        setDrillingIILayout();
        setWellLoggingLayout();
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
        setReservoirSimulationLayout();
        setWellDesignLayout();
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
                MECHANICS_I,
                MECHANICS_II);

        if (!isEnglishLanguage) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }

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
                MATHEMATICS_I,
                MATHEMATICS_II);


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
                PHYSICS_I,
                PHYSICS_II);


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
                MATHEMATICS_I,
                MATHEMATICS_II,
                MATHEMATICS_III);


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
                PHYSICS_I,
                PHYSICS_II,
                PHYSICS_III);


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
                GENERAL_CHEMISTRY,
                PHYSICAL_CHEMISTRY);


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
                INTRODUCTION_TO_GEOLOGY,
                SEDIMENTOLOGY_AND_STRATIGRAPHY);


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
                INTRODUCTION_TO_COMPUTER,
                COMPUTER_PROGRAMMING);


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
                TECHNICAL_ENGLISH_I,
                TECHNICAL_ENGLISH_II);


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
                INTRODUCTION_TO_GEOLOGY,
                STRUCTURE_GEOLOGY);


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
                GENERAL_CHEMISTRY,
                ORGANIC_CHEMISTRY);


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
                ENGINEERING_DRAWING_AND_PROJECTION,
                ENGINEERING_DRAWING_AND_AUTO_CAD);


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
                INTRODUCTION_TO_GEOLOGY,
                SEDIMENTOLOGY_AND_STRATIGRAPHY,
                STRUCTURE_GEOLOGY,
                INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                RESERVOIR_ROCK_PROPERTIES);


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
                INTRODUCTION_TO_MATERIAL,
                CORROSION);


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
                TECHNICAL_ENGLISH_I,
                TECHNICAL_ENGLISH_II,
                TECHNICAL_ENGLISH_III);


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
                INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                RESERVOIR_FLUIDS_PROPERTIES);


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
                INTRODUCTION_TO_GEOLOGY,
                SEDIMENTOLOGY_AND_STRATIGRAPHY,
                STRUCTURE_GEOLOGY,
                DRILLING_ENGINEERING_I);


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
                INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                CRUDE_OIL_EVALUATION);


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
                INTRODUCTION_TO_COMPUTER,
                COMPUTER_PROGRAMMING,
                COMPUTER_APPLICATION_IN_PETROLEUM_ENGINEER);


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
                INTRODUCTION_TO_GEOLOGY,
                SEDIMENTOLOGY_AND_STRATIGRAPHY,
                STRUCTURE_GEOLOGY,
                PETROLEUM_GEOLOGY);


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
                INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                RESERVOIR_FLUIDS_PROPERTIES,
                NATURAL_GAS_ENGINEERING);


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
                INTRODUCTION_TO_GEOLOGY,
                SEDIMENTOLOGY_AND_STRATIGRAPHY,
                STRUCTURE_GEOLOGY,
                EXPLORATION_GEOPHYSICS_I);


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
                INTRODUCTION_TO_GEOLOGY,
                SEDIMENTOLOGY_AND_STRATIGRAPHY,
                STRUCTURE_GEOLOGY,
                DRILLING_ENGINEERING_I,
                HORIZONTAL_DRILLING_TECHNOLOGY);


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
                INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                DRILLING_AND_PRODUCTION_EQUIPMENTS);


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
                INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                PETROLEUM_PRODUCTION_ENGINEERING_I);


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
                INTRODUCTION_TO_GEOLOGY,
                SEDIMENTOLOGY_AND_STRATIGRAPHY,
                STRUCTURE_GEOLOGY,
                EXPLORATION_GEOPHYSICS_I,
                EXPLORATION_GEOPHYSICS_II);


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
                INTRODUCTION_TO_GEOLOGY,
                SEDIMENTOLOGY_AND_STRATIGRAPHY,
                STRUCTURE_GEOLOGY,
                INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                RESERVOIR_ROCK_PROPERTIES,
                DRILLING_ENGINEERING_I,
                WELL_COMPLETION_ENGINEERING);


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
                INTRODUCTION_TO_GEOLOGY,
                SEDIMENTOLOGY_AND_STRATIGRAPHY,
                STRUCTURE_GEOLOGY,
                INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                RESERVOIR_ROCK_PROPERTIES,
                RESERVOIR_FLUIDS_PROPERTIES,
                APPLIED_RESERVOIR_ENGINEERING);


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
                INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                PETROLEUM_PRODUCTION_ENGINEERING_I,
                PETROLEUM_PRODUCTION_ENGINEERING_II);


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
                INTRODUCTION_TO_GEOLOGY,
                SEDIMENTOLOGY_AND_STRATIGRAPHY,
                STRUCTURE_GEOLOGY,
                INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                RESERVOIR_ROCK_PROPERTIES,
                DRILLING_ENGINEERING_I,
                WELL_LOGGING);


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
                INTRODUCTION_TO_GEOLOGY,
                SEDIMENTOLOGY_AND_STRATIGRAPHY,
                STRUCTURE_GEOLOGY,
                DRILLING_ENGINEERING_I,
                DRILLING_ENGINEERING_II);


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
                INTRODUCTION_TO_GEOLOGY,
                SEDIMENTOLOGY_AND_STRATIGRAPHY,
                STRUCTURE_GEOLOGY,
                INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                RESERVOIR_ROCK_PROPERTIES,
                RESERVOIR_FLUIDS_PROPERTIES,
                APPLIED_RESERVOIR_ENGINEERING,
                FORMATION_STIMULATION);


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
                INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                DRILLING_AND_PRODUCTION_EQUIPMENTS,
                WELL_DESIGN);


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
                INTRODUCTION_TO_COMPUTER,
                COMPUTER_PROGRAMMING,
                RESERVOIR_SIMULATION);


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
                INTRODUCTION_TO_GEOLOGY,
                SEDIMENTOLOGY_AND_STRATIGRAPHY,
                STRUCTURE_GEOLOGY,
                INTRODUCTION_TO_PETROLEUM_ENGINEERING,
                RESERVOIR_ROCK_PROPERTIES,
                CRUDE_OIL_EVALUATION,
                WELL_LOGGING,
                FORMATION_EVALUATION);


        // add the display linear layout above to the main linear layout in the activity to appear
        // on the screen.
        mMainLinearLayout.addView(linearLayout);

    }


}
