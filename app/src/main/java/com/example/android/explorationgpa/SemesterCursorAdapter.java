package com.example.android.explorationgpa;


import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.android.explorationgpa.data.ExplorationContract.SemesterGpaEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class SemesterCursorAdapter extends CursorAdapter {


    public static final String LOG_TAG = SemesterCursorAdapter.class.getSimpleName(); // class name.

    private Context mContext;

    private ArrayList<Boolean> mItemChecked; // array list for store state of each checkbox.

    private static final int DISPLAY_ITEMS = 0; // the layout display items without checkboxes.
    private static final int CALCULATE_TOTAL_GPA = 1; // the layout display items with checkboxes..
    private int mMode = DISPLAY_ITEMS; // the mode that the adapter uses from above.


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
        return LayoutInflater.from(context).inflate(R.layout.cursor_item, parent, false);

    }



    @Override
    public void bindView(View view, Context context, final Cursor cursor) {


        // determine the views inside the item.
        TextView studentNameTextView = (TextView) view.findViewById(R.id.cursor_item_student_name);
        TextView studentIdTextView = (TextView) view.findViewById(R.id.cursor_item_student_id);
        TextView semesterNumberTextView = (TextView) view.findViewById(R.id.cursor_item_semester_number);
        TextView dateTextView = (TextView) view.findViewById(R.id.cursor_item_date);
        TextView timeTextView = (TextView) view.findViewById(R.id.cursor_item_time);
        TextView gpaAsNumberTextView = (TextView) view.findViewById(R.id.cursor_item_total_gpa);
        TextView gpaAsLetterTextView = (TextView) view.findViewById(R.id.cursor_item_circle_gpa);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cursor_item_check_box);


        // get the column position inside the table in semester database.
        int studentNameColumnIndex = cursor.getColumnIndexOrThrow(SemesterGpaEntry.COLUMN_STUDENT_NAME);
        int studentIdColumnIndex = cursor.getColumnIndexOrThrow(SemesterGpaEntry.COLUMN_STUDENT_ID);
        int semesterNumberColumnIndex = cursor.getColumnIndexOrThrow(SemesterGpaEntry.COLUMN_SEMESTER_NUMBER);
        int unixNumberColumnIndex = cursor.getColumnIndexOrThrow(SemesterGpaEntry.COLUMN_UNIX);
        int subjectDegreesColumnIndex = cursor.getColumnIndexOrThrow(SemesterGpaEntry.COLUMN_SEMESTER_DEGREES);


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


        // get total gpa for the semester as letter.
        String totalGpaAsLetter = CalculatorForTotalGpa.getTotalGpaOfSemesterAsLetter(yearNumber, termNumber, degrees);

        // display the total gpa as letter in the screen.
        gpaAsLetterTextView.setText(totalGpaAsLetter);


        // get the background for the TextView that responsible to display the total gpa as letter.
        GradientDrawable circleBackground = (GradientDrawable) gpaAsLetterTextView.getBackground();

        // get the letter color.
        int colorResourceId = CalculatorForTotalGpa.getGpaLetterColor(totalGpaAsLetter);
        int color = ContextCompat.getColor(context, colorResourceId);

        // set the background color for the gpaAsLetterTextView to be the letter color above.
        circleBackground.setColor(color);


        // setup the checkBox.
        if (mMode == CALCULATE_TOTAL_GPA) {

            // show the checkBox in the item.
            checkBox.setVisibility(View.VISIBLE);

            // get position by cursor.
            final int position = cursor.getPosition();

            // to know when the user click on the checkBox.
            checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    // handle clicking on the checkBox and link it to the array (itemChecked).
                    if (mItemChecked.get(position)) {
                        // if current checkbox is checked (true) before, make it not checked (false).
                        mItemChecked.set(position, false);
                    } else {
                        // if current checkbox is not checked (false), make it checked (true).
                        mItemChecked.set(position, true);
                    }

                }
            });

            // (important) set the checkbox state for all item base on the arrayList (itemChecked).
            // without it scrolling in the ListView will change the selected items.
            checkBox.setChecked(mItemChecked.get(position));

        } else {
            // hide the checkBox from the item (in Displaying mode).
            checkBox.setVisibility(View.GONE);
        }


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

        // setup the format shape of the time that should display in the item.
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

        // transfer the unix to the format above.
        return timeFormat.format(dateObject);

    }


    /**
     * Determine the mode that the user at (display-calculate) to change the state of the items view.
     * Notify the Adapter that there is changes in the mode to reset the Adapter.
     *
     * @param mode the mode (display-calculate).
     */
    public void setAdapterMode(int mode) {

        // set the layout mode.
        mMode = mode;

        // the cursor size.
        int arraySize = getCount();

        // initialize the boolean arrayList that will control selecting the checkBox in the items.
        // true : checked.
        // false : not checked.
        mItemChecked = new ArrayList<Boolean>();
        for (int i = 0 ; i < arraySize ; i++) {
            // initializes all items value with false.
            mItemChecked.add(i, false);
        }

        // to notify the adapter that the mode changed after call this method.
        notifyDataSetChanged();

    }


    /**
     * Get the Uris for the semester location inside database that selected by the user.
     *
     * @return array contain semester uris.
     */
    public ArrayList<Uri> getSemesterSelectedUris() {

        ArrayList<Uri> SemesterUris = new ArrayList<>();

        // the cursor size.
        int arraySize = getCount();

        // get the semester uri for any item selected.
        for (int i = 0 ; i < arraySize ; i++) {

            // know if the item selected or not.
            boolean isChecked = mItemChecked.get(i);

            if (isChecked) {
                // create the uri for the semester and add it in the arrayList.
                Uri currentUri = new ContentUris().withAppendedId(SemesterGpaEntry.CONTENT_URI, getItemId(i));
                SemesterUris.add(currentUri);

            }

        }

        return SemesterUris;

    }



}