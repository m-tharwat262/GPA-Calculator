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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.android.explorationgpa.data.ExplorationContract.SemesterGpaEntry;
import com.example.android.explorationgpa.settings.SettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class GpaActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final String LOG_TAG = GpaActivity.class.getSimpleName(); // activity name.

    private SharedPreferences mSharedPrefs; // to access to the preference settings.

    private FloatingActionButton mFloatingActionButton; // icon that start to add a new semester.

    private SemesterCursorAdapter mSemesterCursorAdapter; // adapter for the semester items.

    private static final int SEMESTER_LOADER = 0; // number of the semester loader.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);


        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.activity_gpa_floating_action_button);


        // check if the user log in before or not.
        checkPreference();

        // handle clicks on the floating action button.
        setupFloatingActionButton();


        // display the semesters as items inside the listView.
        mSemesterCursorAdapter = new SemesterCursorAdapter(this, null);


        // handle everything related to the semester ListView(adapting, empty views, item clicks).
        setupSemesterListView();


        // start the semester loader.
        LoaderManager.getInstance(this).initLoader(SEMESTER_LOADER, null, this);


    }



    /**
     * Handle clicks on the items in the semester ListView.
     * Control showing or hiding the empty views in the layout depending on if there is items or not
     * inside the ListView.
     */
    private void setupSemesterListView() {

        ListView semesterListView = (ListView) findViewById(R.id.activity_gpa_listView_for_semesters);
        semesterListView.setAdapter(mSemesterCursorAdapter);

        // to hide the empty views from the layout when there is a semester item in the listView.
        RelativeLayout relativeLayout =  (RelativeLayout) findViewById(R.id.activity_gpa_linear_for_empty_view);
        semesterListView.setEmptyView(relativeLayout);

        // handle clicks on the items in the semester ListView.
        semesterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // open AddSemesterActivity by intent contain uri refer to the location for that
                // semester inside the database.
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

                // TODO: handle click on the button to calculate total gpa for all semesters.

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
     * don not know exactly.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mSemesterCursorAdapter.swapCursor(null);

    }



}
