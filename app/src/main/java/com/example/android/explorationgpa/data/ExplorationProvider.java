package com.example.android.explorationgpa.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
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



    /**
     * Write inside the database to add a new data.
     *
     * @param uri uri for the database path.
     * @param values contain the columns keys and its values.
     *
     * @return uri refer to the row place inside the database.
     */
    @Override
    public Uri insert( Uri uri, ContentValues values) {

        // get the pattern that the uri equal.
        final int match = sUriMatcher.match(uri);

        // setup functions for every uri pattern (no single uris by id).
        switch (match) {

            // semester_gpa database.
            case SEMESTER_GPA:

                // execute helper method to insert the data inside the database.
                // return the uri that refer to the row place inside the database.
                return insertSemesterGpa(uri, values);

            // to handle if the is no match for the uri inserted with the uri patterns.
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }


    /**
     * (Helper Method to Insert Data Inside semester Database).
     * Insert data inside the semester gpa database.
     * Check the validation for the data inserted from the user.
     *
     * @param uri uri for the database path.
     * @param values contain the columns keys and its values.
     *
     * @return uri refer to the row place inside the database.
     */
    private Uri insertSemesterGpa(Uri uri, ContentValues values) {

        // sanity chick for the student name before enter it inside the database.
        String studentName = values.getAsString(SemesterGpaEntry.COLUMN_STUDENT_NAME);
        if (studentName == null) {
            throw new IllegalArgumentException("gpa item requires a name");
        }

        // sanity chick for the student ID before enter it inside the database.
        Integer studentCode = values.getAsInteger(SemesterGpaEntry.COLUMN_STUDENT_ID);
        if (studentCode == null || studentCode < 0) {
            throw new IllegalArgumentException("gpa item requires valid id");
        }

        // sanity chick for the semester number before enter it inside the database.
        Integer studentSemester = values.getAsInteger(SemesterGpaEntry.COLUMN_SEMESTER_NUMBER);
        if (studentSemester == null || !SemesterGpaEntry.isValidSemester(studentSemester)) {
            throw new IllegalArgumentException("gpa item requires valid semester number");
        }


        // access to the database to write inside it.
        SQLiteDatabase semesterDatabase = mSemesterDbHelper.getWritableDatabase();

        // insert the data inside the database and get the row number that the data inserted at.
        long id = semesterDatabase.insert(SemesterGpaEntry.TABLE_NAME, null, values);

        // if the insertion failed show a Log(e) for that.
        // make the uri return value equal null in case the insertion failed.
        if (id == -1) {

            Log.e(LOG_TAG, "Failed to insert row for " + uri);

            return null;
        }

        // Notify that there is changing happened in the database to sync changes to the network or activities.
        getContext().getContentResolver().notifyChange(uri, null);

        // return the uri for the place that the data inserted inside database.
        return ContentUris.withAppendedId(uri, id);
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
