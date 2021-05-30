package com.example.android.explorationgpa;


import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.android.explorationgpa.data.ExplorationContract.CumulativeGpaEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CumulativeCursorAdapter extends CursorAdapter {


    private static final String LOG_TAG = CumulativeCursorAdapter.class.getSimpleName(); // class name.


    public CumulativeCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }



    /**
     * Inflate our custom item to use it inside the ListView.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        // use our custom list item inside the adapter.
        return LayoutInflater.from(context).inflate(R.layout.cursor_item, parent, false);

    }




    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        // determine the views inside the item.
        LinearLayout itemLinearLayout = view.findViewById(R.id.cursor_item_background_layout_item);
        TextView circleTextView = view.findViewById(R.id.cursor_item_circle_gpa);
        TextView studentNameTextView = view.findViewById(R.id.cursor_item_student_name);
        TextView studentIdTextView = view.findViewById(R.id.cursor_item_student_id);
        TextView gpaStatementTextView = view.findViewById(R.id.cursor_item_gpa_statement);
        TextView dateTextView = view.findViewById(R.id.cursor_item_date);
        TextView timeTextView = view.findViewById(R.id.cursor_item_time);


        // get the column position inside the table in cumulative database.
        int studentNameIndex = cursor.getColumnIndexOrThrow(CumulativeGpaEntry.COLUMN_STUDENT_NAME);
        int studentIdIndex = cursor.getColumnIndexOrThrow(CumulativeGpaEntry.COLUMN_STUDENT_ID);
        int semesterNumbersIndex = cursor.getColumnIndexOrThrow(CumulativeGpaEntry.COLUMN_SEMESTER_NUMBERS);
        int semesterDegreesIndex = cursor.getColumnIndexOrThrow(CumulativeGpaEntry.COLUMN_SEMESTER_DEGREES);
        int unixNumberIndex = cursor.getColumnIndexOrThrow(CumulativeGpaEntry.COLUMN_UNIX);


        // get the student name from the database and display it on the screen.
        String studentName = cursor.getString(studentNameIndex);
        studentNameTextView.setText(studentName);


        // get the student name from the database and display it on the screen.
        int studentId = cursor.getInt(studentIdIndex);
        String studentIdAsString = String.valueOf(studentId);
        studentIdTextView.setText(studentIdAsString);



        // create a Gson object.
        Gson gson = new Gson();

        // get the semester numbers from the cursor.
        byte[] semesterNumbersBlob = cursor.getBlob(semesterNumbersIndex); // get the BLOB from the cursor.
        String semesterNumbersJson = new String(semesterNumbersBlob); // convert the BLOB to a String.
        Type semesterNumbersType = new TypeToken<int[]>(){}.getType(); // create a Type object by type int[].
        int[] semesterNumbers = gson.fromJson(semesterNumbersJson, semesterNumbersType); // get the semester numbers as int[] Array.


        // get the semester degrees from the cursor.
        byte[] semesterDegreesBlob = cursor.getBlob(semesterDegreesIndex); // get the BLOB from the cursor.
        String semesterDegreesJson = new String(semesterDegreesBlob); // convert the BLOB to a String.
        Type semesterDegreesType = new TypeToken<ArrayList<double[]>>(){}.getType(); // create a Type object by type int[].
        ArrayList<double[]> semesterDegrees = gson.fromJson(semesterDegreesJson, semesterDegreesType); // get the semester degrees as int[] Array.


        // get all subject (hours & degrees) from semesters data stored in the cursor.
        double[] allHours = getHours(semesterNumbers);
        double[] allDegrees = getDegrees(semesterDegrees);


        // get the cumulative gpa number from the database and display it on the screen.
        double cumulativeGpaNumber = CalculatorForTotalGpa.getCumulativeGpaForFourScale(allHours, allDegrees);
        String cumGpaNumberAfterFormat = String.format("%.2f", cumulativeGpaNumber);
        Resources resources = context.getResources();
        String gpaStatement = String.format(resources.getString(R.string.gpa_statement_for_cumulative_item), cumGpaNumberAfterFormat);
        gpaStatementTextView.setText(gpaStatement);


        // get the cumulative gpa letter from the database and display it on the screen.
        String cumulativeGpaLetter = CalculatorForTotalGpa.getCumulativeGpaAsLetter(allHours,allDegrees);
        circleTextView.setText(cumulativeGpaLetter);


        // get the unix number from the database and store it inside Date object to format it.
        long unixNumber = cursor.getLong(unixNumberIndex);
        Date dateObject = new Date(unixNumber);

        // display the date on the screen.
        String dateFormat = formatDate(dateObject);
        dateTextView.setText(dateFormat);

        // display the time on the screen.
        String timeFormat = formatTime(dateObject);
        timeTextView.setText(timeFormat);


        // get the background for the TextView that responsible to display the total gpa as letter.
        GradientDrawable circleBackground = (GradientDrawable) circleTextView.getBackground();
        // get the letter color.
        int colorResourceId = CalculatorForTotalGpa.getGpaLetterColor(cumulativeGpaLetter);
        int color = ContextCompat.getColor(context, colorResourceId);
        // set the background color for the gpaAsLetterTextView to be the letter color above.
        circleBackground.setColor(color);


        // change the item background color to show some difference between
        // cumulative items and semester items.
        int background = ContextCompat.getColor(context, R.color.background_cumulative_item);
        itemLinearLayout.setBackgroundColor(background);


    }



    /**
     * Get all subject hours in all semesters that inside the integer Array inserted.
     *
     * @return Array contain all subject hours.
     */
    private double[] getHours(int[] semesterNumbers) {

        // create ArrayList to contain all subject hours.
        ArrayList<Double> allSemesterHours = new ArrayList<>();

        // size of the semesterNumbers ArrayList.
        int arraySize = semesterNumbers.length;


        // put each semester hours in the allSemesterHours ArrayList.
        for (int i = 0 ; i < arraySize ; i++) {

            int semesterNumber = semesterNumbers[i];
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
     * Get all subject degrees in all semesters that inside the integer Array inserted.
     *
     * @return Array contain all subject degrees.
     */
    private double[] getDegrees(ArrayList<double[]> semesterDegrees) {

        // create ArrayList to contain all subject degrees.
        ArrayList<Double> allSemesterDegrees = new ArrayList<>();

        // size of the semesterDegrees ArrayList.
        int arraySize = semesterDegrees.size();


        // put each semester degrees in the allSemesterDegrees ArrayList.
        for (int i = 0 ; i < arraySize ; i++) {

            // subject degrees in one semester from the mSemesterDegrees ArrayList
            double[] degrees = semesterDegrees.get(i);


            // put the array above in the ArrayList.
            for (int j = 0 ; j < degrees.length ; j++) {
                allSemesterDegrees.add(degrees[j]);
            }

        }



        // convert the ArrayList that contain all subject degrees to a constant Array.
        double[] finalArrayForDegrees = new double[allSemesterDegrees.size()];
        for (int j = 0 ; j < allSemesterDegrees.size() ; j++) {
            finalArrayForDegrees[j] = allSemesterDegrees.get(j);
        }


        // return all the subject degrees in all semesters.
        return finalArrayForDegrees;

    }


    /**
     * Transfer the unix number to a date look like this format (Apr 21, 2015).
     *
     * @param dateObject contain the unix number.
     *
     * @return the date.
     */
    private String formatDate(Date dateObject) {

        SimpleDateFormat timeFormat = new SimpleDateFormat("LLL dd, yyyy");

        return timeFormat.format(dateObject);

    }


    /**
     * Transfer the unix number to a time look like this format (9:48 PM).
     *
     * @param dateObject contain the unix number.
     *
     * @return the time.
     */
    private String formatTime(Date dateObject) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");

        return dateFormat.format(dateObject);

    }



}
