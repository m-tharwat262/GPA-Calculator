package com.example.android.explorationgpa.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.example.android.explorationgpa.data.ExplorationContract.SemesterGpaEntry;


public class ExplorationProvider extends ContentProvider {


    public static final String LOG_TAG = ExplorationProvider.class.getSimpleName(); // class name.


    private SemesterGpaDbHelper mSemesterDbHelper; // get instance of the semester database.
    private static final int SEMESTER_GPA = 100; // URI pattern to all the semester_gap table.
    private static final int SEMESTER_GPA_ID = 101; // URI pattern to single column in the semester_gap table.


    // initialize the UriMatcher.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // add the table paths and his pattern number in the uri matcher.
    static {
        sUriMatcher.addURI(ExplorationContract.CONTENT_AUTHORITY, ExplorationContract.PATH_SEMESTER_GPA, SEMESTER_GPA);
        sUriMatcher.addURI(ExplorationContract.CONTENT_AUTHORITY, ExplorationContract.PATH_SEMESTER_GPA + "/#", SEMESTER_GPA_ID);
    }


    /**
     * initialize databases here.
     */
    @Override
    public boolean onCreate() {

        // initialize semester database.
        mSemesterDbHelper = new SemesterGpaDbHelper(getContext());

        return false;
    }


    /**
     * Read from the database (all the database or a single row).
     *
     * @param uri uri for the path in the database.
     * @param projection specific column in the database.
     * @param selection specific row in the database.
     * @param selectionArgs the value for the selection parameter above.
     * @param sortOrder the order that the results must return with.
     *
     * @return Cursor contain the results after the reading from database finish.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // access to the semester database to read the data from it.
        SQLiteDatabase semesterDatabase = mSemesterDbHelper.getReadableDatabase();

        // for the return result from the database.
        Cursor cursor;

        // get the pattern that the uri equal.
        int match = sUriMatcher.match(uri);

        // setup functions for every uri pattern.
        switch (match) {

            // semester_gpa database with no id.
            case SEMESTER_GPA:

                // setup the input inside the query function.
                cursor = semesterDatabase.query(SemesterGpaEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);

                break;

            // semester_gpa database with id.
            case SEMESTER_GPA_ID:

                // setup the input inside the query function (after the WHERE word).
                selection = SemesterGpaEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = semesterDatabase.query(SemesterGpaEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;

            // to handle if the is no match for the uri inserted with the uri patterns.
            default:

                throw new IllegalArgumentException("Cannot query unknown URI " + uri);

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // return Cursor contain the data from the database.
        return cursor;
    }


    @Override
    public Uri insert( Uri uri, ContentValues values) {

        // TODO : handle it.
        return null;
    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        // TODO : handle it.
        return 0;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        // TODO : handle it.
        return 0;
    }


    @Override
    public String getType(Uri uri) {

        // TODO : handle it.
        return null;
    }

}
