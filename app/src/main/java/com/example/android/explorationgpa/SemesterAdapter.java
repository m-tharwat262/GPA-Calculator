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


    public static final String LOG_TAG = SemesterAdapter.class.getSimpleName(); // class name.
    private final Context mContext; // for the activity context that the Adapter work at.
    private final ArrayList<SubjectObject> mSubjectObject; // to store the ArrayList for the SubjectObjects.
    private final int mArraySize; // to store the ArrayList size.


    private static final int MODE_FIRST_OPEN = 0; // first time the user open the activity to add a new semester.
    private static final int MODE_DISPLAY_TOTAL_GPA = 1; // display the total gpa and prevent the user to edit anything.
    private static final int MODE_EDIT_DEGREES_AGAIN = 2; // make the user able to edit his degrees again.
    private static final int MODE_OPEN_WITH_URI = 3; // display the semester info when the user open the activity by intent contain uri.
    private int mMode = MODE_FIRST_OPEN; // the basic mode that use across the all activity functions.


    public SemesterAdapter(Context context, ArrayList<SubjectObject> subjectObject) {
        super(context, 0, subjectObject);

        mContext = context; // to determine the specific place that the adapter works in.
        mSubjectObject = subjectObject; // the ArrayList for the SubjectObjects.
        mArraySize = subjectObject.size(); // the ArrayList size.
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
        if (mMode == MODE_DISPLAY_TOTAL_GPA || mMode == MODE_OPEN_WITH_URI) {
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

        } else if ( mMode == MODE_EDIT_DEGREES_AGAIN) {

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
                    String degreeAsString = answerBody.getText().toString();

                    if (!TextUtils.isEmpty(degreeAsString)) {
                        // when there is degree inserted.
                        double degree = Double.parseDouble(degreeAsString);
                        // get the exact SubjectObject that in the item view we work on it.
                        SubjectObject subjectObject = getItem(i);
                        // save the degree in the SubjectObject.
                        subjectObject.setSubjectDegree(degree);
                        // when the degree more than 100, show an error message on the EditText.
                        if (degree > 100) {
                            // tell the user that "can't be more than 100".1
                            answerBody.setError(mContext.getString(R.string.error_message_on_editText_for_degree));
                        }
                    } else if (TextUtils.isEmpty(degreeAsString)) {
                        // when there is no degree inserted we make it equal 0.
                        double degree = 0.0;
                        // get the exact SubjectObject that in the item view we work on it.
                        SubjectObject subjectObject = getItem(i);
                        // save the degree in the SubjectObject.
                        subjectObject.setSubjectDegree(degree);
                    }
                    view.clearFocus();
                }

            }
        });

        return listItemView;
    }


    /**
     * Determine the mode that the user at (edit-calculate) to change the state of the items view.
     * Notify the Adapter that there is changes in the dada to reset the Adapter.
     *
     * @param mode the mode (edit-calculate).
     */
    public void setAdapterMode(int mode) {

        // set the layout mode.
        mMode = mode;

        // to notify the adapter that the mode changed after call this method.
        notifyDataSetChanged();
    }


    /**
     * get all degrees that the user insert inside the EditText views.
     *
     * @return Array with all subject degrees.
     */
    public double[] getSubjectDegrees() {

        // initialize Array that will store the degrees inside it.
        double[] degrees = new double[mArraySize];

        // to get each subject degree and put it inside the Array.
        for (int i = 0 ; i < mArraySize ; i++) {
            degrees[i] = mSubjectObject.get(i).getSubjectDegree();
        }

        return degrees;
    }


    /**
     * Check if there is a degree inserted more than hundred (100).
     *
     * @return true value means there is a degree more than 100.
     */
    public boolean checkDegreesError() {

        // get all the degree inserted to the EditTexts.
        double[] degrees = getSubjectDegrees();
        // array size.
        int arraySize = degrees.length;

        // check all the degrees.
        for (int i = 0 ; i < arraySize ; i++) {
            if (degrees[i] > 100) {
                return true;
            }

        }
        return false;
    }

}
