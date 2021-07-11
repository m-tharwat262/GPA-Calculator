package com.example.android.explorationgpa;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SemesterInfo {


    /**
     * Check if the app preference Settings set the subject language to English or Arabic
     *
     * @param context the context or activity that method use at.
     *
     * @return true if the subject language set to be English or false in Arabic case.
     */
    private static boolean hasEnglishLanguage(Context context) {


        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        // get the subject language from the preference settings.
        String subjectLanguage = sharedPrefs.getString(
                context.getString(R.string.settings_subject_language_key),
                context.getString(R.string.settings_subject_language_default));


        // if the subject language is English the method will return true.
        if ( subjectLanguage.equals(context.getString(R.string.settings_subject_language_default)) ) {
            return true;
        }

        return false;

    }


    /**
     * Take the number of the semester to specify the exact year that the semester at.
     *
     * @param semester number of the semester (1-2-3-4-5-6-7-8-9-10).
     *
     * @return number of the year (0-1-2-3-4).
     */
    public static int getNumberOfYear(int semester) {

        if (semester == 3 || semester == 4) {
            return 1;
        } else if (semester == 5 || semester == 6) {
            return 2;
        } else if (semester == 7 || semester == 8) {
            return 3;
        } else if (semester == 9 || semester == 10) {
            return 4;
        } else {
            return 0;
        }

    }


    /**
     * Take the number of the semester to specify the exact term that the semester at.
     *
     * @param semesterNumber number of the semester (1-2-3-4-5-6-7-8-9-10).
     *
     * @return number of the year (0-1-2-3-4).
     */
    public static int getNumberOfTerm(int semesterNumber) {

        if (semesterNumber == 2 || semesterNumber == 4 || semesterNumber == 6 || semesterNumber == 8 || semesterNumber == 10) {
            return 2;
        } else {
            return 1;
        }

    }


    /**
     * Take the number of the year and term to specify the exact semester the user want.
     *
     * @param yearNumber the year which the user choose (o-1-2-3-4).
     * @param termNumber the term which the user choose (1-2).
     *
     * @return the semester that the user want (1-2-3-4-5-6-7-8-9-10).
     */
    public static int getNumberOfSemester(int yearNumber, int termNumber) {

        int semester = 0;

        if (yearNumber == 0 && termNumber == 1) {
            semester = 1;
        } else if (yearNumber == 0 && termNumber == 2) {
            semester = 2;
        } else if (yearNumber == 1 && termNumber == 1) {
            semester = 3;
        } else if (yearNumber == 1 && termNumber == 2) {
            semester = 4;
        } else if (yearNumber == 2 && termNumber == 1) {
            semester = 5;
        } else if (yearNumber == 2 && termNumber == 2) {
            semester = 6;
        } else if (yearNumber == 3 && termNumber == 1) {
            semester = 7;
        } else if (yearNumber == 3 && termNumber == 2) {
            semester = 8;
        } else if (yearNumber == 4 && termNumber == 1) {
            semester = 9;
        } else if (yearNumber == 4 && termNumber == 2) {
            semester = 10;
        }

        return semester;
    }




    /**
     * Determine the semester by the year & term and get the resources id for subjects string for
     * that semester in an array.
     *
     * @param yearNumber the year which the user select (0-1-2-3-4-5).
     * @param termNumber the term which the user select (1-2).
     *
     * @return array with resources id for subjects string for the semester.
     */
    public static int[] getSubjectsOfSemester(Context context, int yearNumber, int termNumber) {

        boolean englishLanguage = hasEnglishLanguage(context);


        int numOfSemester = getNumberOfSemester(yearNumber, termNumber);// determine the semester (1:10)

        int[] subjects = null;

            switch (numOfSemester) {
                case 1:
                    if (englishLanguage) {
                        subjects = getEnglishSubjectsForSemester_1();
                    } else {
                        subjects = getArabicSubjectsForSemester_1();
                    }
                    break;
                case 2:
                    if (englishLanguage) {
                        subjects = getEnglishSubjectsForSemester_2();
                    } else {
                        subjects = getArabicSubjectsForSemester_2();
                    }
                    break;
                case 3:
                    if (englishLanguage) {
                        subjects = getEnglishSubjectsForSemester_3();
                    } else {
                        subjects = getArabicSubjectsForSemester_3();
                    }
                    break;
                case 4:
                    if (englishLanguage) {
                        subjects = getEnglishSubjectsForSemester_4();
                    } else {
                        subjects = getArabicSubjectsForSemester_4();
                    }
                    break;
                case 5:
                    if (englishLanguage) {
                        subjects = getEnglishSubjectsForSemester_5();
                    } else {
                        subjects = getArabicSubjectsForSemester_5();
                    }
                    break;
                case 6:
                    if (englishLanguage) {
                        subjects = getEnglishSubjectsForSemester_6();
                    } else {
                        subjects = getArabicSubjectsForSemester_6();
                    }
                    break;
                case 7:
                    if (englishLanguage) {
                        subjects = getEnglishSubjectsForSemester_7();
                    } else {
                        subjects = getArabicSubjectsForSemester_7();
                    }
                    break;
                case 8:
                    if (englishLanguage) {
                        subjects = getEnglishSubjectsForSemester_8();
                    } else {
                        subjects = getArabicSubjectsForSemester_8();
                    }
                    break;
                case 9:
                    if (englishLanguage) {
                        subjects = getEnglishSubjectsForSemester_9();
                    } else {
                        subjects = getArabicSubjectsForSemester_9();
                    }
                    break;
                case 10:
                    if (englishLanguage) {
                        subjects = getEnglishSubjectsForSemester_10();
                    } else {
                        subjects = getArabicSubjectsForSemester_10();
                    }
                    break;

            }
        return subjects;
    }


    /**
     * Get the resources id for subjects string for that semester as array.
     *
     * @param semesterNumber exact semester we want get the subject resource ids from it.
     *
     * @return array with resources id for subjects string for the semester.
     */
    public static int[] getSubjectsOfSemester(Context context, int semesterNumber) {

        boolean englishLanguage = hasEnglishLanguage(context);


        int[] subjects = null;

        switch (semesterNumber) {
            case 1:
                if (englishLanguage) {
                    subjects = getEnglishSubjectsForSemester_1();
                } else {
                    subjects = getArabicSubjectsForSemester_1();
                }
                break;
            case 2:
                if (englishLanguage) {
                    subjects = getEnglishSubjectsForSemester_2();
                } else {
                    subjects = getArabicSubjectsForSemester_2();
                }
                break;
            case 3:
                if (englishLanguage) {
                    subjects = getEnglishSubjectsForSemester_3();
                } else {
                    subjects = getArabicSubjectsForSemester_3();
                }
                break;
            case 4:
                if (englishLanguage) {
                    subjects = getEnglishSubjectsForSemester_4();
                } else {
                    subjects = getArabicSubjectsForSemester_4();
                }
                break;
            case 5:
                if (englishLanguage) {
                    subjects = getEnglishSubjectsForSemester_5();
                } else {
                    subjects = getArabicSubjectsForSemester_5();
                }
                break;
            case 6:
                if (englishLanguage) {
                    subjects = getEnglishSubjectsForSemester_6();
                } else {
                    subjects = getArabicSubjectsForSemester_6();
                }
                break;
            case 7:
                if (englishLanguage) {
                    subjects = getEnglishSubjectsForSemester_7();
                } else {
                    subjects = getArabicSubjectsForSemester_7();
                }
                break;
            case 8:
                if (englishLanguage) {
                    subjects = getEnglishSubjectsForSemester_8();
                } else {
                    subjects = getArabicSubjectsForSemester_8();
                }
                break;
            case 9:
                if (englishLanguage) {
                    subjects = getEnglishSubjectsForSemester_9();
                } else {
                    subjects = getArabicSubjectsForSemester_9();
                }
                break;
            case 10:
                if (englishLanguage) {
                    subjects = getEnglishSubjectsForSemester_10();
                } else {
                    subjects = getArabicSubjectsForSemester_10();
                }
                break;

        }
        return subjects;
    }




    /**
     * Determine the semester by the year & term and get the hours for the subjects in that semester
     * in an array.
     *
     * @param yearNumber the year which the user select (0-1-2-3-4-5).
     * @param termNumber the term which the user select (1-2).
     *
     * @return array with hours for the subjects in the semester.
     */
    public static double[] getHoursForSemester(int yearNumber, int termNumber) {

        int numOfSemester = getNumberOfSemester(yearNumber, termNumber); // determine the semester (1:10)

        double[] hours = null;

        switch (numOfSemester) {
            case 1:
                hours = getHoursForSemester_1();
                break;
            case 2:
                hours = getHoursForSemester_2();
                break;
            case 3:
                hours = getHoursForSemester_3();
                break;
            case 4:
                hours = getHoursForSemester_4();
                break;
            case 5:
                hours = getHoursForSemester_5();
                break;
            case 6:
                hours = getHoursForSemester_6();
                break;
            case 7:
                hours = getHoursForSemester_7();
                break;
            case 8:
                hours = getHoursForSemester_8();
                break;
            case 9:
                hours = getHoursForSemester_9();
                break;
            case 10:
                hours = getHoursForSemester_10();
                break;
        }

        return hours;
    }


    /**
     * Get the hours for the subjects in the semester that inserted to the method.
     *
     * @param semesterNumber exact semester we want get the subject hours from it.
     *
     * @return array with hours for the subjects in the semester.
     */
    public static double[] getHoursForSemester(int semesterNumber) {

        double[] hours = null;

        switch (semesterNumber) {
            case 1:
                hours = getHoursForSemester_1();
                break;
            case 2:
                hours = getHoursForSemester_2();
                break;
            case 3:
                hours = getHoursForSemester_3();
                break;
            case 4:
                hours = getHoursForSemester_4();
                break;
            case 5:
                hours = getHoursForSemester_5();
                break;
            case 6:
                hours = getHoursForSemester_6();
                break;
            case 7:
                hours = getHoursForSemester_7();
                break;
            case 8:
                hours = getHoursForSemester_8();
                break;
            case 9:
                hours = getHoursForSemester_9();
                break;
            case 10:
                hours = getHoursForSemester_10();
                break;
        }

        return hours;
    }




    /**
     * Determine the semester by the year & term and get the success degree required to pass in
     * each subject in that semester in an array.
     *
     * @param yearNumber the year which the user select (0-1-2-3-4-5).
     * @param termNumber the term which the user select (1-2).
     *
     * @return array with success degree for the subjects in the semester.
     */
    public static double[] getSuccessDegreeForSemester(int yearNumber, int termNumber) {

        int numOfSemester = getNumberOfSemester(yearNumber, termNumber); // determine the semester (1:10)

        double[] hours = null;

        switch (numOfSemester) {
            case 1:
                hours = getSuccessDegreeForSemester_1();
                break;
            case 2:
                hours = getSuccessDegreeForSemester_2();
                break;
            case 3:
                hours = getSuccessDegreeForSemester_3();
                break;
            case 4:
                hours = getSuccessDegreeForSemester_4();
                break;
            case 5:
                hours = getSuccessDegreeForSemester_5();
                break;
            case 6:
                hours = getSuccessDegreeForSemester_6();
                break;
            case 7:
                hours = getSuccessDegreeForSemester_7();
                break;
            case 8:
                hours = getSuccessDegreeForSemester_8();
                break;
            case 9:
                hours = getSuccessDegreeForSemester_9();
                break;
            case 10:
                hours = getSuccessDegreeForSemester_10();
                break;
        }

        return hours;
    }


    /**
     * Get the success degree required to pass in each subject in the semester inserted to the
     * method in an array.
     *
     * @param semesterNumber exact semester we want get the subject success degree from it.
     *
     * @return array with success degree for the subjects in the semester.
     */
    public static double[] getSuccessDegreeForSemester(int semesterNumber) {

        double[] hours = null;

        switch (semesterNumber) {
            case 1:
                hours = getSuccessDegreeForSemester_1();
                break;
            case 2:
                hours = getSuccessDegreeForSemester_2();
                break;
            case 3:
                hours = getSuccessDegreeForSemester_3();
                break;
            case 4:
                hours = getSuccessDegreeForSemester_4();
                break;
            case 5:
                hours = getSuccessDegreeForSemester_5();
                break;
            case 6:
                hours = getSuccessDegreeForSemester_6();
                break;
            case 7:
                hours = getSuccessDegreeForSemester_7();
                break;
            case 8:
                hours = getSuccessDegreeForSemester_8();
                break;
            case 9:
                hours = getSuccessDegreeForSemester_9();
                break;
            case 10:
                hours = getSuccessDegreeForSemester_10();
                break;
        }

        return hours;
    }




    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * English names for semester (1).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getEnglishSubjectsForSemester_1() {

        int[] subjects = new int[7];
        subjects[0] = R.string.semester_1_subject_1_english;
        subjects[1] = R.string.semester_1_subject_2_english;
        subjects[2] = R.string.semester_1_subject_3_english;
        subjects[3] = R.string.semester_1_subject_4_english;
        subjects[4] = R.string.semester_1_subject_5_english;
        subjects[5] = R.string.semester_1_subject_6_english;
        subjects[6] = R.string.semester_1_subject_7_english;

        return subjects;
    }

    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * English names for semester (2).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getEnglishSubjectsForSemester_2() {

        int[] subjects = new int[6];
        subjects[0] = R.string.semester_2_subject_1_english;
        subjects[1] = R.string.semester_2_subject_2_english;
        subjects[2] = R.string.semester_2_subject_3_english;
        subjects[3] = R.string.semester_2_subject_4_english;
        subjects[4] = R.string.semester_2_subject_5_english;
        subjects[5] = R.string.semester_2_subject_6_english;

        return subjects;
    }

    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * English names for semester (3).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getEnglishSubjectsForSemester_3() {

        int[] subjects = new int[7];
        subjects[0] = R.string.semester_3_subject_1_english;
        subjects[1] = R.string.semester_3_subject_2_english;
        subjects[2] = R.string.semester_3_subject_3_english;
        subjects[3] = R.string.semester_3_subject_4_english;
        subjects[4] = R.string.semester_3_subject_5_english;
        subjects[5] = R.string.semester_3_subject_6_english;
        subjects[6] = R.string.semester_3_subject_7_english;

        return subjects;
    }

    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * English names for semester (4).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getEnglishSubjectsForSemester_4() {

        int[] subjects = new int[7];
        subjects[0] = R.string.semester_4_subject_1_english;
        subjects[1] = R.string.semester_4_subject_2_english;
        subjects[2] = R.string.semester_4_subject_3_english;
        subjects[3] = R.string.semester_4_subject_4_english;
        subjects[4] = R.string.semester_4_subject_5_english;
        subjects[5] = R.string.semester_4_subject_6_english;
        subjects[6] = R.string.semester_4_subject_7_english;

        return subjects;
    }

    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * English names for semester (5).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getEnglishSubjectsForSemester_5() {

        int[] subjects = new int[7];
        subjects[0] = R.string.semester_5_subject_1_english;
        subjects[1] = R.string.semester_5_subject_2_english;
        subjects[2] = R.string.semester_5_subject_3_english;
        subjects[3] = R.string.semester_5_subject_4_english;
        subjects[4] = R.string.semester_5_subject_5_english;
        subjects[5] = R.string.semester_5_subject_6_english;
        subjects[6] = R.string.semester_5_subject_7_english;

        return subjects;
    }

    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * English names for semester (6).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getEnglishSubjectsForSemester_6() {

        int[] subjects = new int[6];
        subjects[0] = R.string.semester_6_subject_1_english;
        subjects[1] = R.string.semester_6_subject_2_english;
        subjects[2] = R.string.semester_6_subject_3_english;
        subjects[3] = R.string.semester_6_subject_4_english;
        subjects[4] = R.string.semester_6_subject_5_english;
        subjects[5] = R.string.semester_6_subject_6_english;

        return subjects;
    }

    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * English names for semester (7).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getEnglishSubjectsForSemester_7() {

        int[] subjects = new int[6];
        subjects[0] = R.string.semester_7_subject_1_english;
        subjects[1] = R.string.semester_7_subject_2_english;
        subjects[2] = R.string.semester_7_subject_3_english;
        subjects[3] = R.string.semester_7_subject_4_english;
        subjects[4] = R.string.semester_7_subject_5_english;
        subjects[5] = R.string.semester_7_subject_6_english;

        return subjects;
    }

    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * English names for semester (8).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getEnglishSubjectsForSemester_8() {

        int[] subjects = new int[6];
        subjects[0] = R.string.semester_8_subject_1_english;
        subjects[1] = R.string.semester_8_subject_2_english;
        subjects[2] = R.string.semester_8_subject_3_english;
        subjects[3] = R.string.semester_8_subject_4_english;
        subjects[4] = R.string.semester_8_subject_5_english;
        subjects[5] = R.string.semester_8_subject_6_english;

        return subjects;
    }

    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * English names for semester (9).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getEnglishSubjectsForSemester_9() {

        int[] subjects = new int[7];
        subjects[0] = R.string.semester_9_subject_1_english;
        subjects[1] = R.string.semester_9_subject_2_english;
        subjects[2] = R.string.semester_9_subject_3_english;
        subjects[3] = R.string.semester_9_subject_4_english;
        subjects[4] = R.string.semester_9_subject_5_english;
        subjects[5] = R.string.semester_9_subject_6_english;
        subjects[6] = R.string.semester_9_subject_7_english;

        return subjects;
    }

    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * English names for semester (10).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getEnglishSubjectsForSemester_10() {

        int[] subjects = new int[7];
        subjects[0] = R.string.semester_10_subject_1_english;
        subjects[1] = R.string.semester_10_subject_2_english;
        subjects[2] = R.string.semester_10_subject_3_english;
        subjects[3] = R.string.semester_10_subject_4_english;
        subjects[4] = R.string.semester_10_subject_5_english;
        subjects[5] = R.string.semester_10_subject_6_english;
        subjects[6] = R.string.semester_10_subject_7_english;

        return subjects;
    }



    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * Arabic names for semester (1).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getArabicSubjectsForSemester_1() {

        int[] subjects = new int[7];
        subjects[0] = R.string.semester_1_subject_1_arabic;
        subjects[1] = R.string.semester_1_subject_2_arabic;
        subjects[2] = R.string.semester_1_subject_3_arabic;
        subjects[3] = R.string.semester_1_subject_4_arabic;
        subjects[4] = R.string.semester_1_subject_5_arabic;
        subjects[5] = R.string.semester_1_subject_6_arabic;
        subjects[6] = R.string.semester_1_subject_7_arabic;

        return subjects;
    }

    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * Arabic names for semester (2).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getArabicSubjectsForSemester_2() {

        int[] subjects = new int[6];
        subjects[0] = R.string.semester_2_subject_1_arabic;
        subjects[1] = R.string.semester_2_subject_2_arabic;
        subjects[2] = R.string.semester_2_subject_3_arabic;
        subjects[3] = R.string.semester_2_subject_4_arabic;
        subjects[4] = R.string.semester_2_subject_5_arabic;
        subjects[5] = R.string.semester_2_subject_6_arabic;

        return subjects;
    }

    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * Arabic names for semester (3).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getArabicSubjectsForSemester_3() {

        int[] subjects = new int[7];
        subjects[0] = R.string.semester_3_subject_1_arabic;
        subjects[1] = R.string.semester_3_subject_2_arabic;
        subjects[2] = R.string.semester_3_subject_3_arabic;
        subjects[3] = R.string.semester_3_subject_4_arabic;
        subjects[4] = R.string.semester_3_subject_5_arabic;
        subjects[5] = R.string.semester_3_subject_6_arabic;
        subjects[6] = R.string.semester_3_subject_7_arabic;

        return subjects;
    }

    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * Arabic names for semester (4).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getArabicSubjectsForSemester_4() {

        int[] subjects = new int[7];
        subjects[0] = R.string.semester_4_subject_1_arabic;
        subjects[1] = R.string.semester_4_subject_2_arabic;
        subjects[2] = R.string.semester_4_subject_3_arabic;
        subjects[3] = R.string.semester_4_subject_4_arabic;
        subjects[4] = R.string.semester_4_subject_5_arabic;
        subjects[5] = R.string.semester_4_subject_6_arabic;
        subjects[6] = R.string.semester_4_subject_7_arabic;

        return subjects;
    }

    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * Arabic names for semester (5).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getArabicSubjectsForSemester_5() {

        int[] subjects = new int[7];
        subjects[0] = R.string.semester_5_subject_1_arabic;
        subjects[1] = R.string.semester_5_subject_2_arabic;
        subjects[2] = R.string.semester_5_subject_3_arabic;
        subjects[3] = R.string.semester_5_subject_4_arabic;
        subjects[4] = R.string.semester_5_subject_5_arabic;
        subjects[5] = R.string.semester_5_subject_6_arabic;
        subjects[6] = R.string.semester_5_subject_7_arabic;

        return subjects;
    }

    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * Arabic names for semester (6).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getArabicSubjectsForSemester_6() {

        int[] subjects = new int[6];
        subjects[0] = R.string.semester_6_subject_1_arabic;
        subjects[1] = R.string.semester_6_subject_2_arabic;
        subjects[2] = R.string.semester_6_subject_3_arabic;
        subjects[3] = R.string.semester_6_subject_4_arabic;
        subjects[4] = R.string.semester_6_subject_5_arabic;
        subjects[5] = R.string.semester_6_subject_6_arabic;

        return subjects;
    }

    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * Arabic names for semester (7).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getArabicSubjectsForSemester_7() {

        int[] subjects = new int[6];
        subjects[0] = R.string.semester_7_subject_1_arabic;
        subjects[1] = R.string.semester_7_subject_2_arabic;
        subjects[2] = R.string.semester_7_subject_3_arabic;
        subjects[3] = R.string.semester_7_subject_4_arabic;
        subjects[4] = R.string.semester_7_subject_5_arabic;
        subjects[5] = R.string.semester_7_subject_6_arabic;

        return subjects;
    }

    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * Arabic names for semester (8).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getArabicSubjectsForSemester_8() {

        int[] subjects = new int[6];
        subjects[0] = R.string.semester_8_subject_1_arabic;
        subjects[1] = R.string.semester_8_subject_2_arabic;
        subjects[2] = R.string.semester_8_subject_3_arabic;
        subjects[3] = R.string.semester_8_subject_4_arabic;
        subjects[4] = R.string.semester_8_subject_5_arabic;
        subjects[5] = R.string.semester_8_subject_6_arabic;

        return subjects;
    }

    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * Arabic names for semester (9).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getArabicSubjectsForSemester_9() {

        int[] subjects = new int[7];
        subjects[0] = R.string.semester_9_subject_1_arabic;
        subjects[1] = R.string.semester_9_subject_2_arabic;
        subjects[2] = R.string.semester_9_subject_3_arabic;
        subjects[3] = R.string.semester_9_subject_4_arabic;
        subjects[4] = R.string.semester_9_subject_5_arabic;
        subjects[5] = R.string.semester_9_subject_6_arabic;
        subjects[6] = R.string.semester_9_subject_7_arabic;

        return subjects;
    }

    /**
     * Helper method in the class to setup the array to contain string resources id for the subject
     * Arabic names for semester (10).
     *
     * @return array with resources id for the subjects string.
     */
    private static int[] getArabicSubjectsForSemester_10() {

        int[] subjects = new int[7];
        subjects[0] = R.string.semester_10_subject_1_arabic;
        subjects[1] = R.string.semester_10_subject_2_arabic;
        subjects[2] = R.string.semester_10_subject_3_arabic;
        subjects[3] = R.string.semester_10_subject_4_arabic;
        subjects[4] = R.string.semester_10_subject_5_arabic;
        subjects[5] = R.string.semester_10_subject_6_arabic;
        subjects[6] = R.string.semester_10_subject_7_arabic;

        return subjects;
    }



    /**
     * Helper method in the class to setup the array to contain hours for the subjects in the
     * semester (1).
     *
     * @return array with hours for subjects.
     */
    private static double[] getHoursForSemester_1() {

        double[] hours = new double[7];
        hours[0] = 3.0;
        hours[1] = 3.0;
        hours[2] = 3.0;
        hours[3] = 2.0;
        hours[4] = 2.0;
        hours[5] = 3.0;
        hours[6] = 3.0;

        return hours;
    }

    /**
     * Helper method in the class to setup the array to contain hours for the subjects in the
     * semester (2).
     *
     * @return array with hours for subjects.
     */
    private static double[] getHoursForSemester_2() {

        double[] hours = new double[6];
        hours[0] = 3.0;
        hours[1] = 3.0;
        hours[2] = 3.0;
        hours[3] = 3.0;
        hours[4] = 2.0;
        hours[5] = 2.0;

        return hours;
    }

    /**
     * Helper method in the class to setup the array to contain hours for the subjects in the
     * semester (3).
     *
     * @return array with hours for subjects.
     */
    private static double[] getHoursForSemester_3() {

        double[] hours = new double[7];
        hours[0] = 3.0;
        hours[1] = 3.0;
        hours[2] = 2.0;
        hours[3] = 3.0;
        hours[4] = 2.0;
        hours[5] = 3.0;
        hours[6] = 2.0;

        return hours;
    }

    /**
     * Helper method in the class to setup the array to contain hours for the subjects in the
     * semester (4).
     *
     * @return array with hours for subjects.
     */
    private static double[] getHoursForSemester_4() {

        double[] hours = new double[7];
        hours[0] = 2.0;
        hours[1] = 3.0;
        hours[2] = 3.0;
        hours[3] = 3.0;
        hours[4] = 3.0;
        hours[5] = 3.0;
        hours[6] = 2.0;

        return hours;
    }

    /**
     * Helper method in the class to setup the array to contain hours for the subjects in the
     * semester (5).
     *
     * @return array with hours for subjects.
     */
    private static double[] getHoursForSemester_5() {

        double[] hours = new double[7];
        hours[0] = 2.0;
        hours[1] = 3.0;
        hours[2] = 3.0;
        hours[3] = 3.0;
        hours[4] = 3.0;
        hours[5] = 3.0;
        hours[6] = 2.0;

        return hours;
    }

    /**
     * Helper method in the class to setup the array to contain hours for the subjects in the
     * semester (6).
     *
     * @return array with hours for subjects.
     */
    private static double[] getHoursForSemester_6() {

        double[] hours = new double[6];
        hours[0] = 3.0;
        hours[1] = 3.0;
        hours[2] = 3.0;
        hours[3] = 2.0;
        hours[4] = 3.0;
        hours[5] = 2.0;

        return hours;
    }

    /**
     * Helper method in the class to setup the array to contain hours for the subjects in the
     * semester (7).
     *
     * @return array with hours for subjects.
     */
    private static double[] getHoursForSemester_7() {

        double[] hours = new double[6];
        hours[0] = 3.0;
        hours[1] = 3.0;
        hours[2] = 3.0;
        hours[3] = 2.0;
        hours[4] = 3.0;
        hours[5] = 2.0;

        return hours;
    }

    /**
     * Helper method in the class to setup the array to contain hours for the subjects in the
     * semester (8).
     *
     * @return array with hours for subjects.
     */
    private static double[] getHoursForSemester_8() {

        double[] hours = new double[6];
        hours[0] = 3.0;
        hours[1] = 3.0;
        hours[2] = 3.0;
        hours[3] = 2.0;
        hours[4] = 3.0;
        hours[5] = 2.0;

        return hours;
    }

    /**
     * Helper method in the class to setup the array to contain hours for the subjects in the
     * semester (9).
     *
     * @return array with hours for subjects.
     */
    private static double[] getHoursForSemester_9() {

        double[] hours = new double[7];
        hours[0] = 3.0;
        hours[1] = 3.0;
        hours[2] = 3.0;
        hours[3] = 3.0;
        hours[4] = 3.0;
        hours[5] = 2.0;
        hours[6] = 2.0;

        return hours;
    }

    /**
     * Helper method in the class to setup the array to contain hours for the subjects in the
     * semester (10).
     *
     * @return array with hours for subjects.
     */
    private static double[] getHoursForSemester_10() {

        double[] hours = new double[7];
        hours[0] = 3.0;
        hours[1] = 3.0;
        hours[2] = 3.0;
        hours[3] = 3.0;
        hours[4] = 3.0;
        hours[5] = 2.0;
        hours[6] = 2.0;

        return hours;
    }



    /**
     * Helper method in the class to setup the array to contain success degree required to pass in
     * each subject in the semester (1).
     *
     * @return array with success degree for subjects.
     */
    private static double[] getSuccessDegreeForSemester_1() {

        double[] hours = new double[7];
        hours[0] = 50.0;
        hours[1] = 50.0;
        hours[2] = 50.0;
        hours[3] = 50.0;
        hours[4] = 50.0;
        hours[5] = 50.0;
        hours[6] = 50.0;

        return hours;
    }

    /**
     * Helper method in the class to setup the array to contain success degree required to pass in
     * each subject in the semester (2).
     *
     * @return array with success degree for subjects.
     */
    private static double[] getSuccessDegreeForSemester_2() {

        double[] hours = new double[6];
        hours[0] = 50.0;
        hours[1] = 50.0;
        hours[2] = 50.0;
        hours[3] = 50.0;
        hours[4] = 50.0;
        hours[5] = 50.0;

        return hours;
    }

    /**
     * Helper method in the class to setup the array to contain success degree required to pass in
     * each subject in the semester (3).
     *
     * @return array with success degree for subjects.
     */
    private static double[] getSuccessDegreeForSemester_3() {

        double[] hours = new double[7];
        hours[0] = 50.0;
        hours[1] = 50.0;
        hours[2] = 50.0;
        hours[3] = 50.0;
        hours[4] = 50.0;
        hours[5] = 50.0;
        hours[6] = 50.0;

        return hours;
    }

    /**
     * Helper method in the class to setup the array to contain success degree required to pass in
     * each subject in the semester (4).
     *
     * @return array with success degree for subjects.
     */
    private static double[] getSuccessDegreeForSemester_4() {

        double[] hours = new double[7];
        hours[0] = 50.0;
        hours[1] = 50.0;
        hours[2] = 50.0;
        hours[3] = 50.0;
        hours[4] = 50.0;
        hours[5] = 50.0;
        hours[6] = 50.0;

        return hours;
    }

    /**
     * Helper method in the class to setup the array to contain success degree required to pass in
     * each subject in the semester (5).
     *
     * @return array with success degree for subjects.
     */
    private static double[] getSuccessDegreeForSemester_5() {

        double[] hours = new double[7];
        hours[0] = 50.0;
        hours[1] = 50.0;
        hours[2] = 50.0;
        hours[3] = 50.0;
        hours[4] = 50.0;
        hours[5] = 50.0;
        hours[6] = 50.0;

        return hours;
    }

    /**
     * Helper method in the class to setup the array to contain success degree required to pass in
     * each subject in the semester (6).
     *
     * @return array with success degree for subjects.
     */
    private static double[] getSuccessDegreeForSemester_6() {

        double[] hours = new double[6];
        hours[0] = 50.0;
        hours[1] = 50.0;
        hours[2] = 50.0;
        hours[3] = 50.0;
        hours[4] = 50.0;
        hours[5] = 50.0;

        return hours;
    }

    /**
     * Helper method in the class to setup the array to contain success degree required to pass in
     * each subject in the semester (7).
     *
     * @return array with success degree for subjects.
     */
    private static double[] getSuccessDegreeForSemester_7() {

        double[] hours = new double[6];
        hours[0] = 50.0;
        hours[1] = 50.0;
        hours[2] = 50.0;
        hours[3] = 50.0;
        hours[4] = 50.0;
        hours[5] = 50.0;

        return hours;
    }

    /**
     * Helper method in the class to setup the array to contain success degree required to pass in
     * each subject in the semester (8).
     *
     * @return array with success degree for subjects.
     */
    private static double[] getSuccessDegreeForSemester_8() {

        double[] hours = new double[6];
        hours[0] = 50.0;
        hours[1] = 50.0;
        hours[2] = 50.0;
        hours[3] = 50.0;
        hours[4] = 50.0;
        hours[5] = 50.0;

        return hours;
    }

    /**
     * Helper method in the class to setup the array to contain success degree required to pass in
     * each subject in the semester (9).
     *
     * @return array with success degree for subjects.
     */
    private static double[] getSuccessDegreeForSemester_9() {

        double[] hours = new double[7];
        hours[0] = 50.0;
        hours[1] = 50.0;
        hours[2] = 50.0;
        hours[3] = 50.0;
        hours[4] = 50.0;
        hours[5] = 50.0;
        hours[6] = 50.0;

        return hours;
    }

    /**
     * Helper method in the class to setup the array to contain success degree required to pass in
     * each subject in the semester (10).
     *
     * @return array with success degree for subjects.
     */
    private static double[] getSuccessDegreeForSemester_10() {

        double[] hours = new double[7];
        hours[0] = 50.0;
        hours[1] = 50.0;
        hours[2] = 50.0;
        hours[3] = 50.0;
        hours[4] = 50.0;
        hours[5] = 50.0;
        hours[6] = 50.0;

        return hours;
    }


}
