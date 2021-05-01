package com.example.android.explorationgpa;


import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.android.explorationgpa.data.ExplorationContract.SemesterGpaEntry;
import com.example.android.explorationgpa.settings.SettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class GpaActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final String LOG_TAG = GpaActivity.class.getSimpleName(); // class name.

    private SharedPreferences mSharedPrefs; // to access to the preference settings.

    private LinearLayout mCalculateButtonsLayout; // layout contain the calculate & cancel buttons.

    private FloatingActionButton mFloatingActionButton; // icon that start to add a new semester.

    private ListView mSemesterListView; // a list display the semester items.

    private SemesterCursorAdapter mSemesterCursorAdapter; // adapter for the semester items.

    private static final int SEMESTER_LOADER = 0; // number of the semester loader.

    private static final int DISPLAY_ITEMS = 0; // the layout display items in the listViews.
    private static final int CALCULATE_TOTAL_GPA = 1; // the layout display two buttons responsible to calculate total gpa.
    private int mMode = DISPLAY_ITEMS; // the mode that the activity uses from above.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);



        // to access to the preference settings.
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        // the circle button that use to add a new semester.
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.activity_gpa_floating_action_button);
        // list that display the semester items.
        mSemesterListView = (ListView) findViewById(R.id.activity_gpa_listView_for_semesters);
        // layout contain buttons (calculate - cancel).
        mCalculateButtonsLayout = (LinearLayout) findViewById(R.id.activity_gpa_Layout_for_buttons);


        // check if the user login before or not.
        checkPreference();

        // handle clicking on the floating action button.
        setupFloatingActionButton();


        // handle clicking on the cancel button.
        setupCancelButton();


        // handle clicking on the calculate button.
        setupCalculateButton();


        // display the semesters as items inside the listView.
        mSemesterCursorAdapter = new SemesterCursorAdapter(this, null);


        // handle everything related to the semester ListView(adapting, empty views, item clicks).
        setupSemesterListView();


        // start the semester loader.
        LoaderManager.getInstance(this).initLoader(SEMESTER_LOADER, null, this);


    }


    /**
     * Handle clicks on the floating action button.
     */
    private void setupFloatingActionButton() {

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // start the InfoActivity.
                Intent intent = new Intent(GpaActivity.this, InfoActivity.class);
                startActivity(intent);

            }
        });

    }


    /**
     * Handle clicks on the cancel button.
     */
    private void setupCancelButton() {

        Button cancelButton = (Button) findViewById(R.id.activity_gpa_button_cancel);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // make the layout in the display mode
                // by show the floating action button and hide two buttons (calculate - cancel).
                removeCalculateLayout();

            }
        });


    }


    /**
     * Handle clicks on the calculate button.
     */
    private void setupCalculateButton() {

        Button calculateButton = (Button) findViewById(R.id.activity_gpa_button_calculate);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get the semester uris (location inside database)for the selected semester items.
                ArrayList<Uri> semesterUris = mSemesterCursorAdapter.getSemesterSelectedUris();

                // open the CumulativeGpaActivity by intent contain the semester uris.
                Intent intent = new Intent(GpaActivity.this, CumulativeGpaActivity.class);
                intent.putExtra("semester_uris", semesterUris);
                startActivity(intent);

            }
        });


    }


    /**
     * Handle clicks on the items in the semester ListView.
     * Control showing or hiding the empty views in the layout depending on if there is items or not
     * inside the ListView.
     */
    private void setupSemesterListView() {


        mSemesterListView.setAdapter(mSemesterCursorAdapter);

        // to hide the empty views from the layout when there is a semester item in the listView.
        RelativeLayout relativeLayout =  (RelativeLayout) findViewById(R.id.activity_gpa_layout_for_empty_view);
        mSemesterListView.setEmptyView(relativeLayout);

        // handle clicks on the items in the semester ListView.
        mSemesterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // open AddSemesterActivity by intent contain uri refer to the location for that
                // semester inside the database.
                // id parameter refer to the unique id for the semester inside database.
                Intent intent = new Intent(GpaActivity.this, AddSemesterActivity.class);
                Uri currentSemesterUri = new ContentUris().withAppendedId(SemesterGpaEntry.CONTENT_URI, id);
                intent.setData(currentSemesterUri);
                startActivity(intent);

            }
        });


    }


    /**
     * Override method to inflate our custom menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // for inflate the menu that we made for GpaActivity.
        getMenuInflater().inflate(R.menu.menu_gpa, menu);

        return true;
    }


    /**
     * Override method to handle the clicks on the menu icons.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // handle clicks on any menu icons.
        switch (item.getItemId()) {

            // for settings button.
            case R.id.menu_gpa_settings:

                // start SettingActivity.
                Intent intent = new Intent(GpaActivity.this , SettingsActivity.class);
                startActivity(intent);

                return true;

            // for calculate total gpa button.
            case R.id.menu_gpa_calculate_total_gpa:

                // start the calculate mode that hide the floating action button and display
                // the calculate button layout that contain buttons (calculate - cancel).
                displayCalculateLayout();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Check if the user logged in or not when the app open.
     * Make the user log in when he open the app for the first time.
     */
    private void checkPreference () {

        // get the user name from preference settings.
        String studentNamePreference = mSharedPrefs.getString(
                getString(R.string.settings_student_name_key),
                getString(R.string.settings_student_name_default));

        // check if the user didn't login and in this case send him to the log in layout.
        if (studentNamePreference.equals(getString(R.string.settings_student_name_default))) {

            // start LoginActivity to make the user login.
            Intent numbersIntent = new Intent(GpaActivity.this, LoginActivity.class);
            startActivity(numbersIntent);
            finish();
        }

    }


    /**
     * (Calculate total gpa mode).
     * Repair the layout to start for calculating total gpa.
     * Hide the floating action button & Show buttons (calculate - cancel).
     */
    private void displayCalculateLayout() {

        // determine that the layout at.
        mMode = CALCULATE_TOTAL_GPA;
        Log.i(LOG_TAG, "The mode that the layout at : " + mMode);

        // hide the floating action button.
        mFloatingActionButton.setVisibility(View.GONE);

        // show the layout that contain buttons (calculate - cancel).
        mCalculateButtonsLayout.setVisibility(View.VISIBLE);

        // add shadow under buttons layout.
        addShadowUnderButtons();

        // show the checkBox on the items.
        mSemesterCursorAdapter.setAdapterMode(CALCULATE_TOTAL_GPA);

    }


    /**
     * (Display items mode).
     * Return the layout to display the items in the listViews again.
     * Show the floating action button & Hide buttons (calculate - cancel).
     */
    private void removeCalculateLayout() {

        // determine that the layout at.
        mMode = DISPLAY_ITEMS;
        Log.i(LOG_TAG, "The mode that the layout at : " + mMode);

        // show the floating action button.
        mFloatingActionButton.setVisibility(View.VISIBLE);

        // hide the layout that contain buttons (calculate - cancel).
        mCalculateButtonsLayout.setVisibility(View.GONE);

        // remove teh shadow that was under buttons layout by make the listView bottomPadding = 0.
        mSemesterListView.setPadding(0, 0, 0, 0);

        // hide the checkBox on the items.
        mSemesterCursorAdapter.setAdapterMode(DISPLAY_ITEMS);

    }


    /**
     * Add bottom padding to the semester listView to make the last item in that list appear above
     * the buttons (calculate & cancel) in calculate mode.
     */
    private void addShadowUnderButtons() {

        // to get the height after the activity layout inflated.
        ViewTreeObserver observerForBasicInfoLayout = mCalculateButtonsLayout.getViewTreeObserver();
        observerForBasicInfoLayout.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                // (important) to clear the removeOnGlobalLayoutListener from the view.
                // without it the listener will work without stopping when the activity start.
                // we can note that by using logs.
                mCalculateButtonsLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // get the height of the linear that contain the two buttons (calculate - cancel).
                int heightOfBasicInfoLayout = mCalculateButtonsLayout.getHeight(); // in pixels.

                // add button padding to the ListView to make a shadow under the linear of buttons.
                mSemesterListView.setPadding(0, 0, 0, heightOfBasicInfoLayout);

            }
        });

    }



    /**
     * Setup all loader functions in the activity.
     * Specify what exactly we want to get from the database.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle args) {

        // to get data from the database by specific order :
        // first by student name ASCENDING => (a : z).
        // second by student ID ASCENDING => (1 : 10).
        // third by the semester ASCENDING => (1 : 10).
        String sortOrder = SemesterGpaEntry.COLUMN_STUDENT_NAME + " ASC, "
                + SemesterGpaEntry.COLUMN_STUDENT_ID + " ASC, "
                + SemesterGpaEntry.COLUMN_SEMESTER_NUMBER + " ASC";


        // handle what each loader do.
        switch (loaderID) {

            // for semester loader.
            case SEMESTER_LOADER:
                return new CursorLoader(this,
                        SemesterGpaEntry.CONTENT_URI,
                        null, // null because all the table columns will be used.
                        null,
                        null,
                        sortOrder);

            default:
                return null;
        }

    }


    /**
     * Add the Cursor that get from the database to the adapter to display the items with the
     * semester info stored inside that cursor.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        mSemesterCursorAdapter.swapCursor(cursor);

    }


    /**
     * Don't know exactly.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mSemesterCursorAdapter.swapCursor(null);

    }



}
