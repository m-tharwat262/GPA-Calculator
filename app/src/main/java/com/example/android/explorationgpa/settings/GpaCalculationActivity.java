package com.example.android.explorationgpa.settings;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.explorationgpa.R;


public class GpaCalculationActivity extends AppCompatActivity {


    private static final String LOG_TAG = GpaCalculationActivity.class.getSimpleName(); // class name.

    private LinearLayout mLinearLayoutHint2; // layout contain the hint number (2).


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_gpa_calculation);


        // determine the LinearLayouts that responsible to display the hints inside them
        mLinearLayoutHint2 = findViewById(R.id.activity_setting_gpa_calculation_layout_for_hint_2);



        // add a table with its content values inside the layout responsible to display the hint(2).
        addTableForHint_2();


    }



    /**
     * Add a table with its content values (subject degree range - gpa letter) inside
     * the layout that responsible to display the hint(2).
     */
    private void addTableForHint_2() {

        // inflate the layout contain the table item.
        View tableHeadsItem = getLayoutInflater().inflate(R.layout.settings_table_item, null, false);


        // display the subject degree heads in the table.
        TextView subjectDegreeHead = tableHeadsItem.findViewById(R.id.settings_table_item_subject_degree_range);
        subjectDegreeHead.setText(R.string.table_head_subject_degree);

        // display the gpa letters heads in the table.
        TextView gpaLettersHead = tableHeadsItem.findViewById(R.id.settings_table_item_subject_gpa_details);
        gpaLettersHead.setText(R.string.table_head_gpa_letter);


        // add the view above to the layout that responsible to display the hint (3)
        // between the part.1 and the part.2 in the hint.
        mLinearLayoutHint2.addView(tableHeadsItem);


        // get the arrays that contain the degrees range values and the gpa points
        String[] degreesRangeArray = getResources().getStringArray(R.array.array_subject_degree_value);
        String[] gapLettersArray = getResources().getStringArray(R.array.array_gpa_letter_value);



        // add each row in the table with the values for (degree range - gap points)
        for(int i = 0 ; i < degreesRangeArray.length ; i++) {

            // inflate the layout contain the table item.
            View tableItem = getLayoutInflater().inflate(R.layout.settings_table_item, null, false);


            // display the subject degree value in the table.
            TextView subjectDegreeValue = tableItem.findViewById(R.id.settings_table_item_subject_degree_range);
            subjectDegreeValue.setText(degreesRangeArray[i]);

            // display the gpa letters value in the table.
            TextView gpaLettersValue = tableItem.findViewById(R.id.settings_table_item_subject_gpa_details);
            gpaLettersValue.setText(gapLettersArray[i]);


            // add the view above to under the table heads the created above the loop.
            mLinearLayoutHint2.addView(tableItem);

        }


    }


}
