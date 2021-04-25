package com.example.android.explorationgpa;


import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.explorationgpa.data.ExplorationContract.SemesterGpaEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.Date;


public class SemesterCursorAdapter extends CursorAdapter {


    public static final String LOG_TAG = SemesterCursorAdapter.class.getSimpleName(); // class name.

    private Context mContext;


    public SemesterCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);

        mContext = context;

    }



    /**
     * Inflate our custom item to use it inside the ListView.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        // use our custom list item inside the adapter.
        return LayoutInflater.from(context).inflate(R.layout.semester_item, parent, false);

    }



    @Override
    public void bindView(View view, Context context, final Cursor cursor) {


        // determine the views inside the item.
        TextView studentNameTextView = (TextView) view.findViewById(R.id.semester_item_student_name);
        TextView studentIdTextView = (TextView) view.findViewById(R.id.semester_item_student_id);
        TextView semesterNumberTextView = (TextView) view.findViewById(R.id.semester_item_semester_number);
        TextView dateTextView = (TextView) view.findViewById(R.id.semester_item_date);
        TextView timeTextView = (TextView) view.findViewById(R.id.semester_item_time);
        TextView gpaAsNumberTextView = (TextView) view.findViewById(R.id.semester_item_total_gpa);


        // get the column position inside the table in semester database.
        int studentNameColumnIndex = cursor.getColumnIndexOrThrow(SemesterGpaEntry.COLUMN_STUDENT_NAME);
        int studentIdColumnIndex = cursor.getColumnIndexOrThrow(SemesterGpaEntry.COLUMN_STUDENT_ID);
        int semesterNumberColumnIndex = cursor.getColumnIndexOrThrow(SemesterGpaEntry.COLUMN_SEMESTER_NUMBER);
        int unixNumberColumnIndex = cursor.getColumnIndexOrThrow(SemesterGpaEntry.COLUMN_UNIX);
        int subjectDegreesColumnIndex = cursor.getColumnIndexOrThrow(SemesterGpaEntry.COLUMN_OBJECT_SEMESTER);


        // get the student name from the database and display it in the screen.
        String studentName = cursor.getString(studentNameColumnIndex);
        studentNameTextView.setText(studentName);


        // get the student name from the database and display it in the screen.
        int studentId = cursor.getInt(studentIdColumnIndex);
        String studentIdAsString = String.valueOf(studentId); // convert student ID from Integer to String.
        studentIdTextView.setText(studentIdAsString);


        // get the semester number from the database and display it in the screen.
        int semesterNumber = cursor.getInt(semesterNumberColumnIndex);
        Resources resources = mContext.getResources();
        String semester = String.format(resources.getString(R.string.semester_with_placeholder), semesterNumber);
        semesterNumberTextView.setText(semester);


        // get the unix number from the database and store it inside Date object to format it.
        long unixNumber = cursor.getLong(unixNumberColumnIndex);
        Date dateObject = new Date(unixNumber);

        // display the date in the screen.
        String dateFormat = formatDate(dateObject);
        dateTextView.setText(dateFormat);

        // display the time in the screen.
        String timeFormat = formatTime(dateObject);
        timeTextView.setText(timeFormat);


        // get degrees from database.
        byte[] blob = cursor.getBlob(subjectDegreesColumnIndex); // degrees as BLOB.
        String json = new String(blob); // convert BLOB above to json object.
        Gson gson = new Gson(); // initialize the Gson Object.
        double[] degrees = gson.fromJson(json, new TypeToken<double[]>(){}.getType()); // convert the json String to double array.

        // get the year & term number for the semester.
        int yearNumber = SemesterInfo.getNumberOfYear(semesterNumber);
        int termNumber = SemesterInfo.getNumberOfTerm(semesterNumber);

        // get total gpa for the semester as number (4 Scale type).
        double totalGpaAsNumber = CalculatorForTotalGpa.getTotalGpaOfSemesterForFourScale(yearNumber, termNumber, degrees);

        // display the total gpa as number in the screen.
        String numberAfterFormat = String.format("%.2f", totalGpaAsNumber);
        String gpaStatement = String.format(resources.getString(R.string.gpa_equal_with_placeholder), numberAfterFormat);
        gpaAsNumberTextView.setText(gpaStatement);


    }



    /**
     * Transfer the unix number to a date look like this format (Apr 21, 2015).
     *
     * @param dateObject contain the unix number.
     *
     * @return the date.
     */
    private String formatDate(Date dateObject) {

        // setup the format shape of the date that should display in the item.
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");

        // transfer the unix to the format above.
        return dateFormat.format(dateObject);
    }


    /**
     * Transfer the unix number to a time look like this format (9:48 PM).
     *
     * @param dateObject contain the unix number.
     *
     * @return the time.
     */
    private String formatTime(Date dateObject) {

        // setup the format shape of the tiem that should display in the item.
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

        // transfer the unix to the format above.
        return timeFormat.format(dateObject);

    }


}