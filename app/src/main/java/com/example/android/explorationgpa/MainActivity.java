package com.example.android.explorationgpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.activity_main_full_layout);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.activity_main_progress_bar);

        TextView mNextButton = (TextView) findViewById(R.id.activity_main_next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // TODO(1): Setup the preference to contain the student name and ID.

                // make only the progress bar visible
                linearLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                // destroy the current Activity and start the GpaActivity
                Intent intent = new Intent(MainActivity.this , GpaActivity.class);
                finish();
                startActivity(intent);

            }
        });

    }

}
