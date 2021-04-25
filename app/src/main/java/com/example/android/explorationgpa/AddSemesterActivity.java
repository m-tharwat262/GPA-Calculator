package com.example.android.explorationgpa;


import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.explorationgpa.data.ExplorationContract.SemesterGpaEntry;
import com.google.gson.Gson;

import java.util.ArrayList;


public class AddSemesterActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final String LOG_TAG = AddSemesterActivity.class.getSimpleName(); // activity name.

    private TextView nameTextView; // for the basic info part in the layout.
    private TextView idTextView; // for the basic info part in the layout.
    private TextView semesterTextView; // for the basic info part in the layout.

    private TextView totalGpaAsNumberTextView; // for display the gpa as a number.
    private TextView totalGpaAsLetterTextView; // for display the gpa as a letter.
    private TextView totalGpaAsPercentageTextView; // for display the gpa as a percentage.

    private LinearLayout linearForTotalGpa; // the linear that display the (the total gap) statement.
    private Button doneButton; // fot the button which calculate gpa.

    private ListView listView; // that display the semester subjects info as items in the layout.

    int yearNumber; // (0-1-2-3-4-5)
    int termNumber; // (1-2)

    private SemesterAdapter semesterAdapter; // the adapter which display the semester subjects in the listView.

    private static final int MODE_FIRST_OPEN = 0; // first time the user open the activity to add a new semester.
    private static final int MODE_DISPLAY_TOTAL_GPA = 1; // display the total gpa and prevent the user to edit anything.
    private static final int MODE_EDIT_DEGREES_AGAIN = 2; // make the user able to edit his degrees again.
    private int mMode; // the basic mode that use across the all activity functions.

    private Uri semesterUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_semester);

        // get the specific view from the xml layout.
        nameTextView = (TextView) findViewById(R.id.activity_add_semester_name);
        idTextView = (TextView) findViewById(R.id.activity_add_semester_id);
        semesterTextView = (TextView) findViewById(R.id.activity_add_semester_number_of_semester);

        linearForTotalGpa = (LinearLayout) findViewById(R.id.activity_add_semester_linear_for_total_gpa);
        doneButton = (Button) findViewById(R.id.activity_add_semester_button_done);

        totalGpaAsNumberTextView = (TextView) findViewById(R.id.activity_add_semester_total_gpa_as_number);
        totalGpaAsLetterTextView = (TextView) findViewById(R.id.activity_add_semester_total_gpa_as_letter);
        totalGpaAsPercentageTextView = (TextView) findViewById(R.id.activity_add_semester_total_gpa_as_percentage);

        listView = (ListView) findViewById(R.id.activity_add_semester_list_view);


        // get the information which the intent (from infoActivity) that open that activity.
        Intent intent = getIntent();
        String studentName = intent.getStringExtra("student_name"); // for student name.
        int studentId = intent.getIntExtra("student_id",0); // for student id.
        yearNumber = intent.getIntExtra("year_number", 0); // for number of the year.
        termNumber = intent.getIntExtra("term_number", 0); // for number of the term.
        Log.i(LOG_TAG, "intent come from InfoActivity contain : " + studentName + "  " + studentId + " " + yearNumber + " " + termNumber);



        // show the user basic data on the top of the layout.
        setupTheBasicInfo(studentName, studentId, yearNumber, termNumber);

        // show the year and term name in the layout.
        setupTheYearAndTerm(yearNumber, termNumber);

        // fix the overlap between the views in the layout.
        setupTheLayoutShadows();

        // control the clicks on the done buttons.
        setupDoneButtonFunctions();


        // check if there is uri inside the intent that open this activity.
        // handle both cases(with Uri - without Uri).
        semesterUri = intent.getData();
        if (semesterUri == null) {
            // start the first mode for the activity to add a new semester.
            startMode0();
        } else {
            // TODO: setup the activity to handle when there is a valid Uri.
        }


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

                // (important) to clear the listener from the view.
                // without it the listener will work without stopping when the activity start.
                // we can note that by using logs.
                basicInfoLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

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

                // (important) to clear the listener from the view.
                // without it the listener will work without stopping when the activity start.
                // we can note that by using logs.
                doneButton.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // setup the height of the shadow to be equal the height of the done button.
                int heightOfDoneButtonHeight = doneButton.getHeight();
                View shadowViewOfDoneButton = findViewById(R.id.activity_add_semester_shadow_for_done_button);
                ViewGroup.LayoutParams paramsForDoneButton =  shadowViewOfDoneButton.getLayoutParams();
                paramsForDoneButton.height = heightOfDoneButtonHeight;
                shadowViewOfDoneButton.setLayoutParams(paramsForDoneButton);

            }
        });


    }


    /**
     * Control all functions that the done button must execute and calculate the
     * Switch between modes like (add - edit)
     */
    private void setupDoneButtonFunctions() {

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // switch between modes.
                if (mMode == MODE_FIRST_OPEN || mMode == MODE_EDIT_DEGREES_AGAIN) {

                    // (important) to clear the focus from the EditText inside the item views.
                    // that make the last EditText save the data inserted inside it.
                    listView.clearFocus();

                    // start and execute the functions at the mode (1).
                    startMode1();

                } else if (mMode == MODE_DISPLAY_TOTAL_GPA) {

                    // start and execute the functions at the mode (2).
                    startMode2();

                }

            }
        });

    }


    /**
     * (user can insert degrees).
     * for the first time the user open the activity to add a new semester.
     */
    private void startMode0() {

        // make the mode equal 0.
        mMode = MODE_FIRST_OPEN;
        Log.i(LOG_TAG, "the AddSemesterActivity mode is   :   " + mMode);

        // get ArrayList of the SubjectObjects that contain the info about the semester subject.
        ArrayList<SubjectObject> subjectObjects = getArrayListOfSubjectsObjects(yearNumber, termNumber);

        // setup the ListView that display the semester subject info.
        semesterAdapter = new SemesterAdapter(this, subjectObjects);
        listView.setAdapter(semesterAdapter);

        // no need to show the total gpa statement at that mode.
        linearForTotalGpa.setVisibility(View.GONE);

        // to display word DONE on the done button.
        doneButton.setText(R.string.button_done);

    }


    /**
     * (user can not insert or edit).
     * show the total gpa to the user by (4 Scale - % Scale - Letter) without ability to edit his
     * degrees.
     */
    private void startMode1() {

        // change the mode to be equal (1).
        mMode = MODE_DISPLAY_TOTAL_GPA;
        Log.i(LOG_TAG, "the AddSemesterActivity mode is   :   " + mMode);

        // notify the adapter that we are start the mode (1) to change its content.
        semesterAdapter.setAdapterMode(MODE_DISPLAY_TOTAL_GPA);

        // to show the total gpa statement at that mode.
        linearForTotalGpa.setVisibility(View.VISIBLE);

        // to display word EDIT on the done button.
        doneButton.setText(R.string.button_edit);

        // get all degrees that the user insert in the mode (0).
        double[] degrees = semesterAdapter.getSubjectDegrees();


        // calculate the total gpa number (4 Scale type).
        double totalGpaForFourScale = CalculatorForTotalGpa.getTotalGpaOfSemesterForFourScale(yearNumber, termNumber, degrees);
        // to make there is just two number after the dot like that (4.75).
        String totalGpaAfterFormat = String.format("%.2f", totalGpaForFourScale);
        // display the total gpa by four scale in the layout.
        totalGpaAsNumberTextView.setText(totalGpaAfterFormat);


        // calculate the total gpa letter.
        String totalGpaAsLetter = CalculatorForTotalGpa.getTotalGpaOfSemesterAsLetter(yearNumber, termNumber, degrees);
        // make the total gpa letter between brackets.
        String totalGpaAsLetterBetweenBrackets = String.format(getResources().getString(R.string.text_between_brackets), totalGpaAsLetter);
        // display the total gpa by four scale in the layout.
        totalGpaAsLetterTextView.setText(totalGpaAsLetterBetweenBrackets);


        // calculate the total gpa number (% Scale type).
        double totalGpaPercentageScale = CalculatorForTotalGpa.getTotalGpaOfSemesterForPercentageScale(yearNumber, termNumber, degrees);
        // make there is just two number after the dot like that (88.75) .
        String totalGpaPercentageAfterFormat = String.format("%.2f", totalGpaPercentageScale);
        // add the % sign after the total gpa.
        String totalGpaWithPercentageSign = String.format(getResources().getString(R.string.number_with_hundred_percentage_sign), totalGpaPercentageAfterFormat);
        // display the total gpa by four scale in the layout.
        totalGpaAsPercentageTextView.setText(totalGpaWithPercentageSign);

    }


    /**
     * (user can insert or edit his degrees).
     * enable the EditText again to make it able to write inside it and receive degrees that the
     * user inserted or edited.
     */
    private void startMode2() {

        // change the mode to be equal (2).
        mMode = MODE_EDIT_DEGREES_AGAIN;
        Log.i(LOG_TAG, "the AddSemesterActivity mode is   :   " + mMode);

        // notify the adapter that we are start the mode (2) to change its content.
        semesterAdapter.setAdapterMode(2);

        // to display word DONE on the done button.
        doneButton.setText(R.string.button_done);

    }


    /**
     * get an ArrayList of SubjectObjects that include subject info of any semester by know the
     * year and term number.
     *
     * @param year number of the year that the user choose.
     * @param term number of the term that the user choose.
     *
     * @return ArrayList of SubjectObjects for the semester.
     */
    private ArrayList<SubjectObject> getArrayListOfSubjectsObjects(int year, int term) {

        // to get the resources ids for the subjects name.
        int[] ids = SemesterInfo.getSubjectsOfSemester(year, term);

        // initialise the ArrayList of the SubjectObjects.
        ArrayList<SubjectObject> subjectObjects = new ArrayList<>();

        // add the resources ids to the subjects name to the ArrayList.
        for (int i = 0 ; i < ids.length ; i++) {
            subjectObjects.add(new SubjectObject(ids[i]));
        }

        return subjectObjects;
    }


    /**
     * Override method to inflate our custom menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // inflate our custom menu we made for AddSemesterActivity.
        getMenuInflater().inflate(R.menu.menu_add_semester, menu);

        return true;
    }


    /**
     * Override method to handle the clicks on the menu icons.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // handle clicks on any menu icons.
        switch (item.getItemId()) {

            // for save icon.
            case R.id.menu_add_semester_action_save:

                saveSemester(); // save the semester inside database.

                finish(); // close the activity and return the user to GpaActivity.

                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * save the total gpa for the semester.
     */
    private void saveSemester() {

        // get current date and time.
        long time = System.currentTimeMillis();

        // get student name from the TextView.
        // trim the text inside the TextView (the statement without white spaces right or left it).
        String studentName = nameTextView.getText().toString().trim();

        // get student ID from the TextView.
        // trim the text inside the TextView (the statement without white spaces right or left it).
        String studentIdAsString = idTextView.getText().toString().trim();
        int studentId = Integer.valueOf(studentIdAsString);

        // get semester number.
        String semesterNumberAsString = semesterTextView.getText().toString();
        int semesterNumber = Integer.valueOf(semesterNumberAsString);

        // get semester subject degrees.
        double[] degrees = semesterAdapter.getSubjectDegrees();

        // convert degrees array to a byte array.
        Gson gson = new Gson();
        byte[] degreesAsByte = gson.toJson(degrees).getBytes();


        // initialize and setup the ContentValues to contain the data that will be insert inside the database.
        ContentValues values = new ContentValues();
        values.put(SemesterGpaEntry.COLUMN_STUDENT_NAME, studentName);
        values.put(SemesterGpaEntry.COLUMN_STUDENT_ID, studentId);
        values.put(SemesterGpaEntry.COLUMN_SEMESTER_NUMBER, semesterNumber);
        values.put(SemesterGpaEntry.COLUMN_OBJECT_SEMESTER, degreesAsByte);
        values.put(SemesterGpaEntry.COLUMN_UNIX, time);


        // insert the new semester to the database.
        Uri uri = getContentResolver().insert(SemesterGpaEntry.CONTENT_URI, values);

        // check if the semester inserted successfully or failed.
        if (uri == null) {

            // show a Toast message say that the semester saving failed.
            Toast.makeText(this, R.string.insert_semester_inside_database_failed, Toast.LENGTH_SHORT).show();
        } else {

            // show a Toast message say that the semester saved.
            Toast.makeText(this, R.string.insert_semester_inside_database_successful, Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    public Loader<Cursor> onCreateLoader(int id,Bundle args) {
        return null;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }



}
