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

import java.text.SimpleDateFormat;
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
        int gpaNumberIndex = cursor.getColumnIndexOrThrow(CumulativeGpaEntry.COLUMN_GPA_NUMBER);
        int gpaLetterIndex = cursor.getColumnIndexOrThrow(CumulativeGpaEntry.COLUMN_GPA_LETTER);
        int unixNumberIndex = cursor.getColumnIndexOrThrow(CumulativeGpaEntry.COLUMN_UNIX);


        // get the student name from the database and display it on the screen.
        String studentName = cursor.getString(studentNameIndex);
        studentNameTextView.setText(studentName);


        // get the student name from the database and display it on the screen.
        int studentId = cursor.getInt(studentIdIndex);
        String studentIdAsString = String.valueOf(studentId);
        studentIdTextView.setText(studentIdAsString);


        // get the cumulative gpa number from the database and display it on the screen.
        double cumulativeGpaNumber = cursor.getDouble(gpaNumberIndex);
        String gpaNumberAfterFormat = String.format("%.2f", cumulativeGpaNumber);
        Resources resources = context.getResources();
        String gpaStatement = String.format(resources.getString(R.string.gpa_statement_for_cumulative_item), gpaNumberAfterFormat);
        gpaStatementTextView.setText(gpaStatement);


        // get the cumulative gpa letter from the database and display it on the screen.
        String cumulativeGpaLetter = cursor.getString(gpaLetterIndex);
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
