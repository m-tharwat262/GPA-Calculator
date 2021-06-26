package com.example.android.explorationgpa.settings;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.explorationgpa.R;

import java.util.ArrayList;


public class SettingSemesterAdapter extends ArrayAdapter<SubjectInfoObject> {


    private static final String LOG_TAG = SettingSemesterAdapter.class.getSimpleName(); // the class name.

    private final Context mContext; // for the activity context that the Adapter work at.


    public SettingSemesterAdapter(Context context, ArrayList<SubjectInfoObject> SubjectInfoObject) {
        super(context, 0, SubjectInfoObject);

        mContext = context; // to determine the specific place that the adapter works in.

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {

            // inflate the layout that contain the item view which we made to display the subject info.
            listItemView = LayoutInflater.from(mContext).inflate(R.layout.settings_subject_item, parent, false);

        }


        // determine the views from the inflated layout.
        TextView subjectNameTextView = listItemView.findViewById(R.id.settings_subject_item_subject_name);
        TextView subjectHoursTextView = listItemView.findViewById(R.id.settings_subject_item_subject_hours);
        TextView subjectSuccessDegreeTextView = listItemView.findViewById(R.id.settings_subject_item_subject_success_degree);


        // get the subjectObject by his position in the ArrayList.
        SubjectInfoObject subjectInfoObject = getItem(position);


        // set the subject names.
        String subjectName = mContext.getString(subjectInfoObject.getSubjectNameResourceIds());
        subjectNameTextView.setText(subjectName);

        // set the subject hours.
        String subjectHours = String.valueOf(subjectInfoObject.getSubjectHours());
        subjectHoursTextView.setText(subjectHours);

        // set the subject success degree required degree to pass in it.
        String subjectSuccessDegree = String.valueOf(subjectInfoObject.getSubjectSuccessDegrees());
        subjectSuccessDegreeTextView.setText(subjectSuccessDegree);


        return listItemView;

    }


}
