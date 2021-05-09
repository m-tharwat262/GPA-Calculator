package com.example.android.explorationgpa;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

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



}