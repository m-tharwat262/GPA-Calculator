package com.example.android.explorationgpa;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.explorationgpa.data.ExplorationContract.SemesterGpaEntry;


public class SemesterCursorAdapter extends CursorAdapter {


    public static final String LOG_TAG = SemesterCursorAdapter.class.getSimpleName(); // class name.

    public SemesterCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);

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


        // get the column position inside the table in semester database.
        int studentNameColumnIndex = cursor.getColumnIndexOrThrow(SemesterGpaEntry.COLUMN_STUDENT_NAME);
        int studentIdColumnIndex = cursor.getColumnIndexOrThrow(SemesterGpaEntry.COLUMN_STUDENT_ID);


        // get the student name from the database and display it in the screen.
        String studentName = cursor.getString(studentNameColumnIndex);
        studentNameTextView.setText(studentName);


        // get the student name from the database and display it in the screen.
        int studentId = cursor.getInt(studentIdColumnIndex);
        String studentIdAsString = String.valueOf(studentId); // convert student ID from Integer to String.
        studentIdTextView.setText(studentIdAsString);

    }


}