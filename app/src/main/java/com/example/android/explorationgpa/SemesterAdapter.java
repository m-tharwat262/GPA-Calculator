package com.example.android.explorationgpa;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class SemesterAdapter extends ArrayAdapter<SubjectObject> {


    public SemesterAdapter(Context context, ArrayList<SubjectObject> subjectObject) {
        super(context, 0, subjectObject);

    }


}
