package com.example.android.explorationgpa.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;


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


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // TODO : handle it.
        return null;
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
