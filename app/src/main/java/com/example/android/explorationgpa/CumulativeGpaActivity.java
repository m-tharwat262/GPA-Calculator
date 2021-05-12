package com.example.android.explorationgpa;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.explorationgpa.data.ExplorationContract.SemesterGpaEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;



public class CumulativeGpaActivity extends AppCompatActivity {


    private static final String LOG_TAG = CumulativeGpaActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cumulative_gpa);


        // get data (semester uris) from the last activity (GpaActivity).
        Intent intent = getIntent();
        ArrayList<Uri> semesterUris = intent.getParcelableArrayListExtra("semester_uris");
        Log.i(LOG_TAG, "the semester uris that comes from the GpaActivity : " + semesterUris);



        // fix the overlap between the views in the layout.
        setupTheLayoutShadows();


        // display the cumulative gpa in the top part in the activity layout;
        displayCumulativeGpa(semesterUris);


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
     * cumulative gpa
     *
     * @param semesterUris semesters uris for its location inside database.
     *
     * @return cursor contain semesters data that will use in the activity to calculate cumulative gpa.
     */
    private Cursor getCursorForSemesterUris(ArrayList<Uri> semesterUris) {

        // setup the projection parameter for the query method.
        String[] projection = {
                SemesterGpaEntry.COLUMN_SEMESTER_NUMBER,
                SemesterGpaEntry.COLUMN_SEMESTER_DEGREES
        };



        // create a StringBuilder to use to setup the selection parameter for the query method.
        StringBuilder selectionStringBuilder = new StringBuilder();

        // get the unique ids for the semester uris.
        long[] semesterIds = getIdsFromUris(semesterUris);

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
     * @param uris refer to the specific location inside the database
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
     * Get all semester numbers from the cursor inserted.
     *
     * @param cursor contain the semesters data.
     *
     * @return ArrayList contain the semester numbers for the all semesters in the cursor.
     */
    private ArrayList<Integer> getSemesterNumbers (Cursor cursor) {

        // create ArrayList to make it to contain the semester numbers.
        ArrayList<Integer> allSemesterNumbers = new ArrayList<>();

        // get the index numbers for semester number column in the table inside semester database.
        int semesterNumberIndex = cursor.getColumnIndexOrThrow(SemesterGpaEntry.COLUMN_SEMESTER_NUMBER);


        // to make sure that the cursor in the zero position before start the loop bottom.
        cursor.moveToPosition(-1);

        // move across all the cursor position to get the semester number for each semester inside it.
        while (cursor.moveToNext()) {

            // get the semester number for the cursor.
            int semesterNumberFromDatabase = cursor.getInt(semesterNumberIndex);

            // add the semester number to the ArrayList.
            allSemesterNumbers.add(semesterNumberFromDatabase);

        }

        // ArrayList contain the semester numbers inside it.
        return allSemesterNumbers;

    }


    /**
     * Get all subject hours in all semesters which numbers insert to the method.
     *
     * @param allSemesterNumbers ArrayList contain all semester numbers we want get hours for it.
     *
     * @return array contain all subject hours.
     */
    private double[] getSemesterHours (ArrayList<Integer> allSemesterNumbers) {

        // create ArrayList to contain the all subject hours.
        ArrayList<Double> allSemesterHours = new ArrayList<>();

        // size of the sllSemesterNumbers ArrayList.
        int arraySize = allSemesterNumbers.size();


        // put each semester hours in the allSemesterHours ArrayList.
        for (int i = 0 ; i < arraySize ; i++) {

            int semesterNumber = allSemesterNumbers.get(i);
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
     * @param cursor contain the semesters data.
     *
     * @return array contain all subject degrees.
     */
    private double[] getAllSemesterDegrees (Cursor cursor) {

        // create ArrayList will contain all subject degrees fo all semesters.
        ArrayList<Double> allSemesterDegrees = new ArrayList<>();

        // get the index numbers for each column in the table inside semester database.
        int semesterDegreesIndex = cursor.getColumnIndexOrThrow(SemesterGpaEntry.COLUMN_SEMESTER_DEGREES);


        // to make sure that the cursor in the zero position before start the loop bottom.
        cursor.moveToPosition(-1);

        // move across all the cursor position to get the subject degrees for each semester inside the cursor.
        while (cursor.moveToNext()) {

            // get all subject degrees for the semester from database.
            byte[] blob = cursor.getBlob(semesterDegreesIndex); // degrees as BLOB.
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
     *
     * @param semesterUris refer to the semester uris that comes from the GpaActivity.
     */
    private void displayCumulativeGpa(ArrayList<Uri> semesterUris) {

        // get the specific part in the activity layout that will display the cumulative gpa.
        TextView cumGpaTextView = findViewById(R.id.activity_cumulative_gpa_circle_shape);


        // get the cursor contained required data about semesters to calculate the cumulative gpa.
        Cursor cursor = getCursorForSemesterUris(semesterUris);

        // get all semester numbers stored in the cursor above.
        ArrayList<Integer> semesterNumbers = getSemesterNumbers(cursor);

        // get all subject hours that will be used in the calculation for the cumulative gpa.
        double[] hours = getSemesterHours(semesterNumbers);

        // get all subject degrees that will be used in the calculation for the cumulative gpa.
        double[] degrees = getAllSemesterDegrees(cursor);


        // close the cursor to clean used resources after no need for it.
        cursor.close();



        // get the cumulative gpa as a number and format it to make just two numbers after dot (0.00).
        double cumulativeGpaNumber = CalculatorForTotalGpa.getCumulativeGpaForFourScale(hours, degrees);
        String cumGpaNumberAfterFormat = String.format("%.2f", cumulativeGpaNumber);

        // get the cumulative gpa as a letter.
        String cumulativeGpaLetter = CalculatorForTotalGpa.getCumulativeGpaOfSemesterAsLetter(hours,degrees);



        // set the TextView background color depend on the gpa letter.
        GradientDrawable circleBackground = (GradientDrawable) cumGpaTextView.getBackground();
        int colorResourceId = CalculatorForTotalGpa.getGpaLetterColor(cumulativeGpaLetter);
        int color = ContextCompat.getColor(this, colorResourceId);
        circleBackground.setColor(color);



        // setup the string for the cumulative gpa to make it ready to display on the screen.
        String cumGpaForDisplaying = cumGpaNumberAfterFormat + "\n" + cumulativeGpaLetter;

        // display the cumulative gpa on the screen.
        cumGpaTextView.setText(cumGpaForDisplaying);

    }


}
