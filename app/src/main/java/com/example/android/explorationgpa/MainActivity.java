package com.example.android.explorationgpa;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView mNextButton = (TextView) findViewById(R.id.main_activity_next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // TODO(1): Setup the preference to contain the student name and ID.
                // TODO(2): Setup an intent to open the next activity.

            }
        });

    }

}
