package com.example.android.explorationgpa.data;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;



public final class ExplorationContract {


    public static final String CONTENT_AUTHORITY = "com.example.android.explorationgpa"; // the content authority.

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY); // the uri for the database.

    public static final String PATH_SEMESTER_GPA = "semester_gpa"; // table name for the semester gpa used in uri path.
    public static final String PATH_CUMULATIVE_GPA = "cumulative_gpa"; // table name for the cumulative used in the uri path.




    // Class For the semester total gpa in the databases.
    public static final class SemesterGpaEntry implements BaseColumns {


        // the uri contains the path to the semester gpa table in the databases.
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SEMESTER_GPA);

        public final static String TABLE_NAME = "semester_gpa"; // table name.


        // head names for the table rows.
        public final static String _ID = BaseColumns._ID; // unique id for each column.
        public final static String COLUMN_STUDENT_NAME = "name"; // student name.
        public final static String COLUMN_STUDENT_ID = "id"; // student id.
        public final static String COLUMN_SEMESTER_NUMBER = "semester"; // semester number.
        public final static String COLUMN_SEMESTER_DEGREES = "degrees"; // degrees.
        public final static String COLUMN_UNIX = "unix"; // for the date and time.


        // constant values for the semester number.
        public static final int SEMESTER_ONE = 1;
        public static final int SEMESTER_TWO = 2;
        public static final int SEMESTER_THREE = 3;
        public static final int SEMESTER_FOUR = 4;
        public static final int SEMESTER_FIVE = 5;
        public static final int SEMESTER_SIX = 6;
        public static final int SEMESTER_SEVEN = 7;
        public static final int SEMESTER_EIGHT = 8;
        public static final int SEMESTER_NINE = 9;
        public static final int SEMESTER_TEN = 10;



        /**
         * Check if the semester number can insert to the database or not.
         *
         * @param semesterNumber number of the semester.
         *
         * @return boolean value to know if the number entered is valid ot not.
         */
        public static boolean isValidSemester(int semesterNumber) {

            if (semesterNumber == SEMESTER_ONE || semesterNumber == SEMESTER_TWO ||
                    semesterNumber == SEMESTER_THREE || semesterNumber == SEMESTER_FOUR ||
                    semesterNumber == SEMESTER_FIVE || semesterNumber == SEMESTER_SIX ||
                    semesterNumber == SEMESTER_SEVEN || semesterNumber == SEMESTER_EIGHT ||
                    semesterNumber == SEMESTER_NINE || semesterNumber == SEMESTER_TEN) {

                return true;
            }

            return false;
        }



        // for outside apps that may be allow to access to the database in the app.
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SEMESTER_GPA;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SEMESTER_GPA;


    }




    // Class For the cumulative gpa for the databases.
    public static final class CumulativeGpaEntry implements BaseColumns {


        // the uri contains the path to the cumulative gpa table in the databases.
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CUMULATIVE_GPA);

        public final static String TABLE_NAME = "cumulative_gpa"; // table name.



        // head names for the table rows.
        public final static String _ID = BaseColumns._ID; // unique id for each column.
        public final static String COLUMN_STUDENT_NAME = "name"; // student name.
        public final static String COLUMN_STUDENT_ID = "id"; // student id.
        public final static String COLUMN_SEMESTER_NUMBERS = "semester_numbers"; // all semester numbers.
        public final static String COLUMN_SEMESTER_DEGREES = "semester_degrees"; // all degrees in each semester.
        public final static String COLUMN_SEMESTER_URIS = "uris"; // semester uris refer to location (inside semester database).
        public final static String COLUMN_GPA_NUMBER = "gpa_number"; // the cumulative gpa as number.
        public final static String COLUMN_GPA_LETTER = "gpa_letter"; // the cumulative gpa as letter.
        public final static String COLUMN_UNIX = "unix"; // for the date and time.


    }



}
