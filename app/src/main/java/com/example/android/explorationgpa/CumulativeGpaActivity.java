package com.example.android.explorationgpa;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.explorationgpa.data.ExplorationContract.CumulativeGpaEntry;
import com.example.android.explorationgpa.data.ExplorationContract.SemesterGpaEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;



public class CumulativeGpaActivity extends AppCompatActivity {


    private static final String LOG_TAG = CumulativeGpaActivity.class.getSimpleName(); // class name.

    private LinearLayout mYearItermContainer; // will contain the year items.

    private Cursor mCursor; // to contain all required data about semesters.

    private int SEMESTER_NUMBER_INDEX; // the semester number position in the cursor.
    private int SEMESTER_DEGREES_INDEX; // the semester degree position in the cursor.

    private ArrayList<Uri> mSemesterUris; // contain the uris that comes to the activity with the intent.
    private ArrayList<Integer> mSemesterNumbers; // contain the semester numbers that stored in the cursor.

    private static final int SEMESTER_ADAPTER_MODE = 4; // use to set the semester adapter to display the subject item in year items.

    private double mCumulativeGpaNumber; // the cumulative gpa as a double number.
    private String mCumulativeGpaLetter; // the cumulative gpa as a letter.

    public static final int MODE_CALCULATING = 0; // when the activity open to calculate the cumulative gpa for semesters selected in the GpaActivity.
    public static final int MODE_DISPLAYING = 1; // when the activity opens to display a cumulative item stored inside the database.
    private int mMode = MODE_CALCULATING; // the mode used across the activity.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cumulative_gpa);



        // determine the LinearLayout that contain the year items.
        mYearItermContainer = findViewById(R.id.activity_cumulative_gpa_year_items);


        // get data (semester uris) from the last activity (GpaActivity).
        Intent intent = getIntent();

        // get semester uris.
        mSemesterUris = intent.getParcelableArrayListExtra("semester_uris");

        // get the mode number.
        mMode = intent.getIntExtra("activity_mode", MODE_CALCULATING);


        // fix the overlap between the views in the layout.
        setupTheLayoutShadows();


        // create a Cursor contain all required data about semesters that will be used in the activity.
        mCursor = getMemberCursor();


        // determine the position for the semester number and degrees in the Cursor.
        SEMESTER_NUMBER_INDEX = mCursor.getColumnIndex(SemesterGpaEntry.COLUMN_SEMESTER_NUMBER);
        SEMESTER_DEGREES_INDEX = mCursor.getColumnIndex(SemesterGpaEntry.COLUMN_SEMESTER_DEGREES);


        // create ArrayList contain the semester numbers in order like its order in the cursor.
        mSemesterNumbers = getSemesterNumbers();


        // display the cumulative gpa in the top part on the screen.
        displayCumulativeGpa();


        // display year items on the screen.
        displayYearItems();


        // close the cursor to clean the resources after no need to it.
        mCursor.close();


    }



    /**
     * Fix the overlap between the views (top part contain total gpa) and other views in the
     * layout by know the height of the views and make shadows behind it.
     */
    private void setupTheLayoutShadows() {

        // determine linear that contain the top part in the layout which display the total gpa.
        final LinearLayout topPart = findViewById(R.id.activity_cumulative_gpa_linear_top_part);

        // to get the height after the activity layout inflated.
        ViewTreeObserver observerForBasicInfoLayout = topPart.getViewTreeObserver();
        observerForBasicInfoLayout.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                // (important) to clear the listener from the view.
                // without it the listener will work without stopping when the activity start.
                // we can note that by using logs.
                topPart.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // setup the height of the shadow to be equal the height of the top part.
                int heightOfBasicInfoLayout = topPart.getHeight();
                View shadowView = findViewById(R.id.activity_cumulative_gpa_shadow_for_top_part);
                ViewGroup.LayoutParams paramsForBasicInfoLayout =  shadowView.getLayoutParams();
                paramsForBasicInfoLayout.height = heightOfBasicInfoLayout;
                shadowView.setLayoutParams(paramsForBasicInfoLayout);

            }
        });

    }


    /**
     * Get the required data about semesters from semester database to use it in calculate the
     * cumulative gpa.
     *
     * @return cursor contain semesters data that will use in the activity to calculate cumulative gpa.
     */
    private Cursor getMemberCursor() {

        // setup the projection parameter for the query method.
        String[] projection = {
                SemesterGpaEntry.COLUMN_SEMESTER_NUMBER,
                SemesterGpaEntry.COLUMN_SEMESTER_DEGREES
        };



        // create a StringBuilder to use to setup the selection parameter for the query method.
        StringBuilder selectionStringBuilder = new StringBuilder();

        // get the unique ids for the semester uris.
        long[] semesterIds = getIdsFromUris(mSemesterUris);

        // start build the selection statement to be (  "_id IN (?"  ).
        selectionStringBuilder.append(SemesterGpaEntry._ID + " IN (?");

        // add placeholder for all ids in the selection statement (   ex :   "_id IN (?,?,?,?"    ).
        for (int i = 1 ; i <  semesterIds.length ; i++) {
            selectionStringBuilder.append(",?");
        }
        // finish the selection statement by end it with ")" => (  "_id IN (?,?,?,?)"    )
        selectionStringBuilder.append(")");

        // convert the selection parameter from StringBuilder to String to can use it in query method.
        String selection = String.valueOf(selectionStringBuilder);



        // setup the projection parameter for the query method.
        String[] selectionArgsTest = new String[semesterIds.length];

        for (int j = 0 ; j < semesterIds.length ; j++) {
            selectionArgsTest[j] = String.valueOf(semesterIds[j]);
        }



        // execute the query method using the parameter above to get the cursor containing the
        // required data to calculate the cumulative gpa.
        Cursor cursor = getContentResolver().query(SemesterGpaEntry.CONTENT_URI, projection, selection, selectionArgsTest, null);

        return cursor;

    }


    /**
     * Get the unique ids from the uris inserted.
     *
     * @param uris refer to the specific location inside the database.
     *
     * @return array contain the unique ids for each uri inserted.
     */
    private long[] getIdsFromUris(ArrayList<Uri> uris){

        // the size of the ArrayList.
        int arrayListSize = uris.size();

        // create array to contain the unique ids after get it from the uris.
        long[] ids = new long[arrayListSize];

        // get each unique id for the uris and put it inside the ids array.
        for (int i = 0 ; i < arrayListSize ; i++) {
            Uri singleUriFromArrayList = uris.get(i);

            long id = new ContentUris().parseId(singleUriFromArrayList);

            ids[i] = id;
        }

        return ids;

    }


    /**
     * Get all semester numbers from the cursor member.
     *
     * @return ArrayList contain the semester numbers for the all semesters in the cursor.
     */
    private ArrayList<Integer> getSemesterNumbers() {

        // create ArrayList to make it to contain the semester numbers.
        ArrayList<Integer> semesterNumbers = new ArrayList<>();

        // to make sure that the cursor in the zero position before start the loop bottom.
        mCursor.moveToPosition(-1);

        // move across all the cursor position to get the semester number for each semester inside it.
        while (mCursor.moveToNext()) {

            // get the semester number for the cursor.
            int semesterNumberFromDatabase = mCursor.getInt(SEMESTER_NUMBER_INDEX);

            // add the semester number to the ArrayList.
            semesterNumbers.add(semesterNumberFromDatabase);

        }

        // ArrayList contain the semester numbers inside it.
        return semesterNumbers;

    }


    /**
     * Get all subject hours in all semesters in the member cursor.
     *
     * @return array contain all subject hours.
     */
    private double[] getSemesterHours() {

        // create ArrayList to contain the all subject hours.
        ArrayList<Double> allSemesterHours = new ArrayList<>();

        // size of the sllSemesterNumbers ArrayList.
        int arraySize = mSemesterNumbers.size();


        // put each semester hours in the allSemesterHours ArrayList.
        for (int i = 0 ; i < arraySize ; i++) {

            int semesterNumber = mSemesterNumbers.get(i);
            // get the semester hours.
            double[] hours = SemesterInfo.getHoursForSemester(semesterNumber);

            // put the array above in the ArrayList.
            for (int j = 0 ; j < hours.length ; j++) {
                allSemesterHours.add(hours[j]);
            }

        }



        // convert the ArrayList that contain all subject hours to a constant Array.
        double[] finalArrayForHours = new double[allSemesterHours.size()];
        for (int k = 0 ; k < allSemesterHours.size() ; k++) {
            finalArrayForHours[k] = allSemesterHours.get(k);
        }


        // return all the subject hours in all semesters inserted.
        return finalArrayForHours;

    }


    /**
     * Get all subject degrees from all semesters inside the cursor inserted.
     *
     * @return array contain all subject degrees.
     */
    private double[] getAllSemesterDegrees() {

        // create ArrayList will contain all subject degrees fo all semesters.
        ArrayList<Double> allSemesterDegrees = new ArrayList<>();


        // to make sure that the cursor in the zero position before start the loop bottom.
        mCursor.moveToPosition(-1);

        // move across all the cursor position to get the subject degrees for each semester inside the cursor.
        while (mCursor.moveToNext()) {

            // get all subject degrees for the semester from database.
            byte[] blob = mCursor.getBlob(SEMESTER_DEGREES_INDEX); // degrees as BLOB.
            String json = new String(blob); // convert BLOB above to json object.
            Gson gson = new Gson(); // initialize the Gson Object.
            double[] degrees = gson.fromJson(json, new TypeToken<double[]>(){}.getType()); // convert the json String to double array.

            // put all subject degrees in the semester inside allSemesterDegrees ArrayList.
            for (int i = 0 ; i < degrees.length ; i++) {
                allSemesterDegrees.add(degrees[i]);
            }

        }



        // convert the ArrayList that contain all subject hours to a constant Array.
        double[] finalArrayForHours = new double[allSemesterDegrees.size()];
        for (int i = 0 ; i < allSemesterDegrees.size() ; i++) {
            finalArrayForHours[i] = allSemesterDegrees.get(i);
        }


        // return all the subject degrees in all semesters inserted.
        return finalArrayForHours;

    }


    /**
     * Display the cumulative gpa on the screen by (4 Scale type - letter type).
     */
    private void displayCumulativeGpa() {

        // get the specific part in the activity layout that will display the cumulative gpa.
        TextView cumGpaTextView = findViewById(R.id.activity_cumulative_gpa_circle_shape);

        // get all subject hours that will be used in the calculation for the cumulative gpa.
        double[] hours = getSemesterHours();

        // get all subject degrees that will be used in the calculation for the cumulative gpa.
        double[] degrees = getAllSemesterDegrees();



        // get the cumulative gpa as a number and format it to make just two numbers after dot (0.00).
        mCumulativeGpaNumber = CalculatorForTotalGpa.getCumulativeGpaForFourScale(hours, degrees);
        String cumGpaNumberAfterFormat = String.format("%.2f", mCumulativeGpaNumber);

        // get the cumulative gpa as a letter.
        mCumulativeGpaLetter = CalculatorForTotalGpa.getCumulativeGpaAsLetter(hours,degrees);



        // set the TextView background color depend on the gpa letter.
        GradientDrawable circleBackground = (GradientDrawable) cumGpaTextView.getBackground();
        int colorResourceId = CalculatorForTotalGpa.getGpaLetterColor(mCumulativeGpaLetter);
        int color = ContextCompat.getColor(this, colorResourceId);
        circleBackground.setColor(color);



        // setup the string for the cumulative gpa to make it ready to display on the screen.
        String cumGpaForDisplaying = cumGpaNumberAfterFormat + "\n" + mCumulativeGpaLetter;

        // display the cumulative gpa on the screen.
        cumGpaTextView.setText(cumGpaForDisplaying);

    }


    /**
     * Display the years items on the screen.
     */
    private void displayYearItems() {

        for (int i = 1 ; i < 11 ; i += 2) {

            // get the position in the cursor.
            // if the semester not exist, the cursor will return (-1) as a position.
            int position1 = getPositionInCursor(i);
            int position2 = getPositionInCursor(i+1);


            // if the semester not exist in the cursor we will make it equal 0.
            int firstSemesterNumber = 0;
            int secondSemesterNumber = 0;

            if (position1 != -1) {
                firstSemesterNumber = i;
            }

            if (position2 != -1) {
                secondSemesterNumber = i + 1;
            }



            // start add the year item to the screen if there is one valid semester in the year
            // or both semester (term 1 & term 2) are valid.
            if ( (position1 != -1) || (position2 != -1) ) {
                AddYearIterm(firstSemesterNumber, position1, secondSemesterNumber, position2);
            }

        }

    }


    /**
     * Get the position for the semester in the cursor.
     *
     * @param semesterNumber number of the semester.
     *
     * @return the position in cursor or (-1) if the semester not exist in the cursor.
     */
    private int getPositionInCursor(int semesterNumber) {

        // the position in the ArrayList that contain the semesterNumbers is the same position for
        // that semester in the cursor.
        int positionInCursor = mSemesterNumbers.indexOf(semesterNumber);

        return positionInCursor;

    }


    /**
     * Get subject degrees for any semester in the member cursor by know the position for that semester
     * inside the cursor.
     *
     * @param cursorPosition the position in the member cursor.
     *
     * @return array contain the semester degrees.
     */
    private double[] getDegreesForOneSemester(int cursorPosition) {

        if (cursorPosition == -1) {
            return null;
        }

        // make the cursor in a specific position.
        mCursor.moveToPosition(cursorPosition);

        // get all subject degrees for the semester from database.
        byte[] blob = mCursor.getBlob(SEMESTER_DEGREES_INDEX); // degrees as BLOB.
        String json = new String(blob); // convert BLOB above to json object.
        Gson gson = new Gson(); // initialize the Gson Object.
        double[] degrees = gson.fromJson(json, new TypeToken<double[]>(){}.getType()); // convert the json String to double array.

        // return the semester degrees.
        return degrees;

    }


    /**
     * Add year item contain all the year data to the linearLayout that responsible to display
     * year items in the activity.
     *
     * @param semester1 semester number in the term(1).
     * @param position1 the semester(1) position in the member cursor.
     * @param semester2 semester number in the term(2).
     * @param position2 the semester(2) position in the member cursor.
     */
    private void AddYearIterm(int semester1, int position1, int semester2, int position2) {

        // inflate a new year item to put it in its place in the activity.
        View yearItemView = LayoutInflater.from(this).inflate(R.layout.year_item, null);

        // the short year part in the year item contain (year name - year gpa - short note message).
        LinearLayout shortYearItem = yearItemView.findViewById(R.id.year_item_layout_short_part);

        // the short note message TextView that hint the user there is more details
        // when he click on the short item.
        final TextView detailsButtonTextView = yearItemView.findViewById(R.id.year_item_details_button);

        // the part contain terms (one and two).
        final LinearLayout termsLayouts = yearItemView.findViewById(R.id.year_item_layout_contain_all_terms);



        // display the year name in the short part.
        TextView yearNameTextView = yearItemView.findViewById(R.id.year_item_year_name);
        int yearNameResourceId = getYearNameResourceId(semester1, semester2);
        String yearName = getString(yearNameResourceId);
        yearNameTextView.setText(yearName);



        // get subject degrees for the term(1) and term(2).
        double[] degrees1 = getDegreesForOneSemester(position1);
        double[] degrees2 = getDegreesForOneSemester(position2);

        // display the year gpa in the short part.
        TextView yearGpaTextView = yearItemView.findViewById(R.id.year_item_year_gpa);
        double yearGpa = getYearGpa(semester1, degrees1, semester2, degrees2);
        String yearGpaAfterFormat = String.format("%.2f", yearGpa);
        yearGpaTextView.setText(yearGpaAfterFormat);



        // add listener to the short part to control displaying or hiding the terms in the year item.
        shortYearItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // know the visibility state (visible - Gone).
                int visibilityState = termsLayouts.getVisibility();

                // switch between displaying and hiding the terms depend on the clicks on the short part.
                if (visibilityState == View.GONE) {
                    detailsButtonTextView.setText(R.string.display_less_details);
                    termsLayouts.setVisibility(View.VISIBLE);

                } else {
                    detailsButtonTextView.setText(R.string.display_more_details);
                    termsLayouts.setVisibility(View.GONE);
                }

            }
        });


        // add the terms data (subjects names & degrees - total gpa) in the term item.
        View viewAfterSetupTerms = setTermsData(yearItemView, position1, position2);


        // add the year item to the LinearLayout that responsible to display year items in the activity.
        mYearItermContainer.addView(viewAfterSetupTerms);

    }


    /**
     * Set the terms data (term number - subjects - total gpa) inside the year item view that insert
     * to the function and return it back again.
     *
     * @param yearItemView the view that responsible to display the year info.
     * @param position1 refer to the position in the cursor for term(1).
     * @param position2 refer to the position in the cursor for term(2).
     *
     * @return the year item after filled the views inside it with the terms data.
     */
    private View setTermsData(View yearItemView, int position1, int position2) {

        // names the views that will used in each term (1-2) item.
        TextView TermTitleTextView;
        ListView semesterListView;
        TextView totalGpaTextView;

        // cursor position.
        int position;


        // the loop do the same function to fill data in both term(1) & term(2).
        for (int i = 1 ; i < 3 ; i++) {


            // check first if the term has information to put it in the views first or not by know
            // if the semester in that term is valid (has a position in cursor, means not equal -1).
            if ( (i == 1) && (position1 == -1) ) {

                // hide the term one layout form the year item.
                LinearLayout termOneLinearLayout = yearItemView.findViewById(R.id.year_item_layout_for_term_one);
                termOneLinearLayout.setVisibility(View.GONE);

                // no need to complete for the next steps in the loop.
                continue;

            }

            if (i == 2 && position2 == -1) {

                // hide the term two layout form the year item.
                LinearLayout termTwoLinearLayout = yearItemView.findViewById(R.id.year_item_layout_for_term_two);
                termTwoLinearLayout.setVisibility(View.GONE);

                // no need to complete for the next steps in the loop.
                continue;

            }



            // initialize the views that will be used in the loop and the position that will be used
            // to get the semester data.
            if (i == 1) {
                TermTitleTextView = yearItemView.findViewById(R.id.year_item_title_term_one);
                semesterListView = yearItemView.findViewById(R.id.year_item_list_view_for_term_one);
                totalGpaTextView = yearItemView.findViewById(R.id.year_item_total_gpa_for_term_one);
                position = position1;
            } else {
                TermTitleTextView = yearItemView.findViewById(R.id.year_item_title_term_two);
                semesterListView = yearItemView.findViewById(R.id.year_item_list_view_for_term_two);
                totalGpaTextView = yearItemView.findViewById(R.id.year_item_total_gpa_for_term_two);
                position = position2;
            }



            // move the cursor to the position that the semester at.
            mCursor.moveToPosition(position);


            // display the term and the semester title on the screen.
            int semesterNumber = mCursor.getInt(SEMESTER_NUMBER_INDEX);
            String termTitle = String.format(getResources().getString(R.string.term_word_in_year_item), i,semesterNumber);
            TermTitleTextView.setText(termTitle);


            // get all subject degrees for the semester from database.
            byte[] blob = mCursor.getBlob(SEMESTER_DEGREES_INDEX); // degrees as BLOB.
            String json = new String(blob); // convert BLOB above to json object.
            Gson gson = new Gson(); // initialize the Gson Object.
            double[] degrees = gson.fromJson(json, new TypeToken<double[]>(){}.getType()); // convert the json String to double array.


            // get only the subjects that has degree more than zero as a SubjectObjects.
            ArrayList<SubjectObject> subjectObjects = getValidSemesterSubjects(semesterNumber, degrees);

            // initialize and reaper the SemesterAdapter to display the subject info
            // like (name - degree - gpa letter).
            SemesterAdapter semesterAdapter = new SemesterAdapter(this, subjectObjects);
            semesterAdapter.setAdapterMode(SEMESTER_ADAPTER_MODE);


            // display the subjects info inside a listView.
            semesterListView.setAdapter(semesterAdapter);


            // display the total gpa for the term.
            double totalGpaFourScale = CalculatorForTotalGpa.getTotalGpaOfSemesterForFourScale(semesterNumber, degrees);
            String totalGpaAfterFormat = String.format("%.2f", totalGpaFourScale);
            String termGpaStatement = getResources().getString(R.string.term_gpa, totalGpaAfterFormat);
            totalGpaTextView.setText(termGpaStatement);


        } // end the for loop.


        // show a vertical line between the term one and two in the year item when both of them
        // have data displayed on the screen and no need for that line if one term displayed.
        if (position1 != -1 && position2 != -1) {
            View verticalLine = yearItemView.findViewById(R.id.year_item_vertical_line_between_terms);
            verticalLine.setVisibility(View.VISIBLE);

        }


        // the year item view after fill it with the terms data.
        return yearItemView;

    }


    /**
     * Create ArrayList of SubjectObjects by insert the subject name and its degree to
     * the SubjectObject (only if the degree more than zero).
     *
     * @param semesterNumber number of the semester.
     * @param degrees all subject degrees for the semester that the method will work on.
     *
     * @return ArrayList contain the SubjectObject for the subjects that has degree more than zero.
     */
    private ArrayList<SubjectObject> getValidSemesterSubjects(int semesterNumber, double[] degrees) {

        // get resources ids for the subject names.
        int [] subjectResourcesIds = SemesterInfo.getSubjectsOfSemester(semesterNumber);

        // initialize arrayList of SubjectObject which will contain only subject names that has degree more than zero.
        ArrayList<SubjectObject> subjectObjects = new ArrayList<>();

        // get the size of the array inserted (degrees array).
        int arraySize = degrees.length;

        for(int i = 0 ; i < arraySize ; i++) {

            // get the degree by position.
            double degree = degrees[i];

            // if the degree for the subject more than zero we will make SubjectObject for it.
            // tht SubjectObject store the resource id for the subject name and the degree for it.
            if (degree > 0) {
                int resourceId = subjectResourcesIds[i];
                SubjectObject subjectObject = new SubjectObject(resourceId, degree);
                subjectObjects.add(subjectObject);
            }
        }

        return subjectObjects;

    }


    /**
     * Get the year name by giving any of the semester numbers that in that year.
     *
     * @param semester1 semester number for the term (1).
     * @param semester2 semester number for the term (2).
     *
     * @return resource id for the string name.
     */
    private int getYearNameResourceId(int semester1, int semester2) {

        if (semester1 == 1 || semester2 == 2) {
            return R.string.year_zero;
        } else if (semester1 == 3 || semester2 == 4) {
            return R.string.year_one;
        } else if (semester1 == 5 || semester2 == 6) {
            return R.string.year_two;
        } else if (semester1 == 7 || semester2 == 8) {
            return R.string.year_three;
        } else {
            return R.string.year_four;
        }

    }


    /**
     * Get the year gpa (gpa for the two terms 1-2) by (4 Scale Type).
     *
     * @param semester1 semester number refer to the term(1) - if the value equal 0 means there is
     *                  no data for that semester in the member cursor and the method ignore it in
     *                  the gpa calculation.
     * @param degrees1 subject degrees in the semester(1).
     *
     * @param semester2 semester number refer to the term(2) - if the value equal 0 means there is
     *                  no data for that semester in the member cursor and the method ignore it in
     *                  the gpa calculation.
     * @param degrees2 subject degrees in the semester(2).
     *
     * @return year gpa.
     */
    private double getYearGpa(int semester1, double[] degrees1, int semester2, double[] degrees2) {

        // if the semester number equal to 0 means that semester must be ignored in calculation,
        // so no need to get the subject hours for that semester.
        double[] hours1 = null;
        double[] hours2 = null;
        if (semester1 != 0) {
            hours1 = SemesterInfo.getHoursForSemester(semester1);
        }

        if (semester2 != 0) {
            hours2 = SemesterInfo.getHoursForSemester(semester2);
        }


        // make the two arrays contains the semester hours in one array.
        double[] allHours = mergeTwoArrays(hours1, hours2);
        // make the two arrays contains the semester degrees in one array.
        double[] allDegrees = mergeTwoArrays(degrees1, degrees2);


        // calculate the year gpa.
        double yearGpa = CalculatorForTotalGpa.getCumulativeGpaForFourScale(allHours,allDegrees);

        return yearGpa;

    }


    /**
     * Merge two constant array elements to get one array contains both elements inside them.
     * The merge process put the elements in the (array1) at first in the merging array and
     * the (array2) after that.
     *
     * @param array1 the first array.
     *
     * @param array2 the second array.
     *
     * @return array contain all elements in the array1 & array2.
     */
    private double[] mergeTwoArrays(double[] array1, double[] array2) {

        // check if there is an array equal (null) and if that tru no need to merge the two array
        // together and the method can be finished early and just return the other array.
        if (array1 == null) {
            return array2;
        } else if (array2 == null) {
            return array1;
        }


        // create an ArrayList to put the elements in the (array1 & array2) inside it.
        ArrayList<Double> allElements = new ArrayList<>();

        // add the first array element to the ArrayList.
        for (int i = 0 ; i < array1.length ; i++) {
            allElements.add(array1[i]);
        }


        // add the second array element to the ArrayList.
        for (int i = 0 ; i < array2.length ; i++) {
                allElements.add(array2[i]);
        }


        // create a constant Array with size equal the ArrayList.
        double [] allElementsInConstantArray = new double[allElements.size()];

        // add all elements from the ArrayList to the constant Array.
        for (int i = 0 ; i < allElements.size() ; i ++) {
            allElementsInConstantArray[i] = allElements.get(i);
        }


        // return the constant Array.
        return allElementsInConstantArray;

    }





    /**
     * Save the cumulative item data inside the cumulative database.
     */
    private void saveInDatabase() {

        // get current date and time as a unix number.
        long time = System.currentTimeMillis();


        // get cursor contain the student info (name & ID) from the first semester in the semesters
        // that comes from the previous activity (GpaActivity).
        Cursor cursor = getCursorForStudentInfo();

        // make the cursor in the valid position to start extract data from it.
        cursor.moveToNext();


        // get the student info (name & ID).
        String studentName = getStudentName(cursor);
        int studentId = getStudentId(cursor);


        // close the cursor after no need for it to clean resources.
        cursor.close();


        // (important) to convert the uris to strings to can store it inside the database.
        ArrayList<String> stringUris = convertUrisToStrings();



        // convert stringUris ArrayList to a byte array.
        Gson gson = new Gson();
        byte[] semesterUrisAsByte = gson.toJson(stringUris).getBytes();


        // initialize and setup the ContentValues to contain the data that will be insert inside the database.
        ContentValues values = new ContentValues();
        values.put(CumulativeGpaEntry.COLUMN_STUDENT_NAME, studentName);
        values.put(CumulativeGpaEntry.COLUMN_STUDENT_ID, studentId);
        values.put(CumulativeGpaEntry.COLUMN_GPA_NUMBER, mCumulativeGpaNumber);
        values.put(CumulativeGpaEntry.COLUMN_GPA_LETTER, mCumulativeGpaLetter);
        values.put(CumulativeGpaEntry.COLUMN_SEMESTER_URIS, semesterUrisAsByte);
        values.put(CumulativeGpaEntry.COLUMN_UNIX, time);



        // insert the cumulative item data inside the cumulative database.
        insertData(values);

    }


    /**
     * insert a cumulative item data inside the cumulative database.
     */
    private void insertData(ContentValues values) {

        // insert the new cumulative item data to the database and get the uri refer to the location
        // for that item in the database.
        Uri uri = getContentResolver().insert(CumulativeGpaEntry.CONTENT_URI, values);

        // check if the cumulative item data inserted successfully or failed.
        if (uri == null) {
            // show a toast message to the user says that "Error with saving the cumulative gpa".
            Toast.makeText(this, R.string.insert_cumulative_inside_database_failed, Toast.LENGTH_SHORT).show();
        } else {
            // show a toast message to the user says that "Cumulative gpa saved".
            Toast.makeText(this, R.string.insert_cumulative_inside_database_successful, Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * Create cursor contain the student data that stored in the lowest semester (by number)
     * from all the semesters that comes from the previous activity (GpaActivity).
     *
     * @return Cursor contain the student info (name & ID) that stored with the semester in the database
     */
    private Cursor getCursorForStudentInfo() {

        // refer to the uri for the lowest semester in number.
        Uri lowestSemesterUri = null;

        // get the lowest semester number in all semesters in the mSemesterUris ArrayList
        // and get the uri for that semester.
        for (int i = 1 ; i < 11 ; i++) {

            // the semester uri position in mSemesterUris has the same position in mSemesterNumbers.
            int position = mSemesterNumbers.indexOf(i);

            // if the position is (-1) means there is no semester with number (i) in mSemesterNumbers.
            if (position != -1) {
                lowestSemesterUri = mSemesterUris.get(position);
                // end the loop.
                break;
            }

        }



        // set the projection to the query method to get a specific data from database.
        String[] projection = {
                CumulativeGpaEntry.COLUMN_STUDENT_NAME,
                CumulativeGpaEntry.COLUMN_STUDENT_ID
        };


        // get the semester data from the semester database.
        Cursor cursor = getContentResolver().query(lowestSemesterUri, projection, null, null, null);

        return cursor;

    }


    /**
     * Get the student name from the data stored in the cursor.
     *
     * @param cursor contain the student info stored with a semester item in the database.
     *
     * @return the student name.
     */
    private String getStudentName(Cursor cursor) {

        // get the column position for the student name in the cursor.
        int studentNameIndex = cursor.getColumnIndex(SemesterGpaEntry.COLUMN_STUDENT_NAME);

        // get the student name from the cursor.
        String studentName = cursor.getString(studentNameIndex);

        // return the student name.
        return studentName;

    }


    /**
     * Get the student ID from the data stored in the cursor.
     *
     * @param cursor contain the student info stored with a semester item in the database.
     *
     * @return the student ID.
     */
    private int getStudentId(Cursor cursor) {

        // get the column position for the student ID in the cursor.
        int studentIdIndex = cursor.getColumnIndex(SemesterGpaEntry.COLUMN_STUDENT_ID);

        // get the student ID from the cursor.
        int studentId = cursor.getInt(studentIdIndex);

        // return the student ID.
        return studentId;

    }


    /**
     * Convert all semester uris that comes from the previous activity (GpaActivity) from
     * ArrayList<Uris> to ArrayList<String>.
     *
     * @return ArrayList contain the semester uris as Strings.
     */
    private ArrayList<String> convertUrisToStrings() {

        // create the ArrayList that will store the uris inside it.
        ArrayList<String> urisAsString = new ArrayList<>();

        // convert each Uri in mSemesterUris and put it inside urisAsString ArrayList.
        for (int i = 0 ; i < mSemesterUris.size() ; i++) {

            String stringUri = mSemesterUris.get(i).toString();
            urisAsString.add(stringUri);

        }

        // return ArrayList contain the uris as Strings.
        return urisAsString;

    }






    /**
     * Override method to use our custom menu (menu_database_actions) in the activity.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_database_actions, menu);

        return true;

    }


    /**
     * Override method to control what happen when the user click on any icon in the menu bar.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            // for save icon.
            case R.id.menu_database_action_save:

                // save the cumulative item data inside the database.
                saveInDatabase();

                // close the activity and return the user to GpaActivity.
                finish();

                return true;

        }

        return super.onOptionsItemSelected(item);

    }


    /**
     * Override method to control displaying or hiding icons in the menu bar.
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // initialize the save icon in the menu.
        MenuItem saveIcon = menu.findItem(R.id.menu_database_action_save);

        // show the save icon.
        saveIcon.setVisible(true);

        return true;

    }



}
