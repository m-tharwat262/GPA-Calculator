package com.example.android.explorationgpa;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;


public class SemesterAdapter extends ArrayAdapter<SubjectObject> {


    public static final String LOG_TAG = SemesterAdapter.class.getSimpleName();
    private Context mContext;
    private int mMode = 0;
    private int mSize;


    public SemesterAdapter(Context context, ArrayList<SubjectObject> subjectObject) {
        super(context, 0, subjectObject);

        mContext = context; // to determine the specific place that the adapter works in.
        mSize = subjectObject.size();

    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;

        if (listItemView == null) {

            // inflate the layout that contain the item view which we made to display the subject info.
            listItemView = LayoutInflater.from(mContext).inflate(R.layout.subject_item, parent, false);
        }


        // get the subjectObject by his position in the ArrayList.
        SubjectObject subjectObject = getItem(position);

        // determine the views from the inflated layout.
        EditText changeDegreeEditText = (EditText) listItemView.findViewById(R.id.subject_item_change_degree);
        TextView gpaLetterTextView = (TextView) listItemView.findViewById(R.id.subject_item_gpa_letter);
        TextView subjectNameTextView = (TextView) listItemView.findViewById(R.id.subject_item_subject_name);

        // set the subject names.
        int resourceId = subjectObject.getResourceIdForSubjectName();
        subjectNameTextView.setText(mContext.getString(resourceId));


        // setup the item view by know the mode that the user at.
        if (mMode == 1) {
            // (important) make EditText lost focus to execute the the code that in the listener below.
            changeDegreeEditText.clearFocus();
            // display the gpa letter.
            gpaLetterTextView.setVisibility(View.VISIBLE);
            gpaLetterTextView.setText(subjectObject.getGpaLetterOfSubject());

            // to make the user can not change his degree.
            changeDegreeEditText.setEnabled(false);

            // change the color of the text to be hint.
            changeDegreeEditText.setTextColor(ContextCompat.getColor(mContext, R.color.hint_color));

            // remove the box shape and make it like TextView.
            changeDegreeEditText.setBackgroundResource(android.R.color.transparent);

            // display the degree inserted by the user.
            String string = String.valueOf(subjectObject.getSubjectDegree());
            changeDegreeEditText.setText(string);

        } else if ( mMode == 2) {

            // display the gpa letter.
            gpaLetterTextView.setVisibility(View.VISIBLE);
            gpaLetterTextView.setText(subjectObject.getGpaLetterOfSubject());

            // to make the user can change his degree.
            changeDegreeEditText.setEnabled(true);

            // change the color of the text to be like the app appearance.
            changeDegreeEditText.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));

            // add the box shape to the EditText.
            changeDegreeEditText.setBackgroundResource(android.R.drawable.edit_text);

            // display the degree inserted by the user.
            String string = String.valueOf(subjectObject.getSubjectDegree());
            changeDegreeEditText.setText(string);

        }


        // know the focus on the each EditText by listener to save the degree after the focus lost.
        // any EditText can execute the code below when the focus change on it.
        changeDegreeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                final int i = position; // determine which item view we work at.

                // when the view lost focus that code below will execute.
                if (!hasFocus) {

                    // the view that will work on it below.
                    EditText answerBody = (EditText) view;
                    // get the degree that inserted by the user (may be there is no degree).
                    String s = answerBody.getText().toString();

                    if (!TextUtils.isEmpty(s)) {
                        // when there is degree inserted.
                        int degree = Integer.parseInt(s);
                        // get the exact SubjectObject that in the item view we work on it.
                        SubjectObject subjectObject = getItem(i);
                        // save the degree in the SubjectObject.
                        subjectObject.setSubjectDegree(degree);
                    } else if (TextUtils.isEmpty(s)) {
                        //  when there is no degree inserted we make it equal 0.
                        int degree = 0;
                        // get the exact SubjectObject that in the item view we work on it.
                        SubjectObject subjectObject = getItem(i);
                        // save the degree in the SubjectObject.
                        subjectObject.setSubjectDegree(degree);
                    }

                }

            }
        });

        return listItemView;
    }


    /**
     * Determine the mode that the user at to change the state of the items view.
     *
     * @param mode the mode (edit-calculate).
     */
    public void setAdapterMode(int mode) {

        mMode = mode;

    }


    /**
     * collect all the semester subjects info (degrees specially) and put it inside ArrayList which will be
     * used outside the class in calculation like total gpa for the semester.
     *
     * @return Array with all semester subjects info (name & degrees)
     */
    public ArrayList<SubjectObject> getSemesterSubjects() {

        // initialize the ArrayList
        ArrayList<SubjectObject> semesterSubjects = new ArrayList<>();

        // collect all the final SubjectObjects in ArrayList after the user finish of insert his degrees.
        for (int i = 0 ; i < mSize ; i++) {
            SubjectObject subjectObject = getItem(i);
            semesterSubjects.add(subjectObject);
        }

        return semesterSubjects;
    }

}
