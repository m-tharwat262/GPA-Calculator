package com.example.android.explorationgpa;


import android.util.Log;

import java.util.Arrays;


public class CalculatorForTotalGpa {

    private static final String LOG_TAG = CalculatorForTotalGpa.class.getSimpleName();

    //    - The calculation process for % scale :
    //      1. get subject degrees from 100 (ex : 90 - 80 - 77)
    //      2. get subject hours (ex : 3 - 2 - 3)
    //      3. multiple each degree with the hours (ex: 90 * 3)
    //      4. sum the total multiple in step 3 (ex :   (90*3)+(80*2)+(77*3)  )
    //      5. sum all hours (ex :   (3+2+3)  )
    //      6. divide step 4 / step 5  (ex :   [(90*3)+(80*2)+(77*3)] / [(3+2+3)]    )
    //      7. finish

    //    - The calculation process for 4 scale :
    //      1. get subject degrees from 100 (ex : 90 - 80 - 77)
    //      2. get the point for each degree (ex :  3.7 - 3 - 2.7)
    //      3. get subject hours (ex : 3 - 2 - 3)
    //      4. multiple each point with the hours (ex: 3.7 * 3)
    //      5. sum the total multiple in step 3 (ex :   (3.7*3)+(3*2)+(2.7*3)  )
    //      6. sum all hours (ex :   (3+2+3)  )
    //      7. divide step 5 / step 6  (ex :   [(3.7*3)+(3*2)+(2.7*3)] / [(3+2+3)]    )
    //      8. finish

    //      (100 >= x >= 93)  return   "(A)"   -    "4"
    //      (93 > x >= 88)    return   "(A-)"  -   "3.7"
    //      (88 > x >= 82)    return   "(B+)"  -   "3.3"
    //      (82 > x >= 78)    return   "(B)"   -    "3"
    //      (78 > x >= 74)    return   "(B-)"  -   "2.7"
    //      (74 > x >= 70)    return   "(C+)"  -   "2.3"
    //      (70 > x >= 65)    return   "(C)"   -    "2"
    //      (65 > x >= 60)    return   "(C-)"  -   "1.7"
    //      (60 > x >= 55)    return   "(D+)"  -   "1.3"
    //      (55 > x >= 50)    return   "(D)"   -    "1"


    /**
     * Get points for each subject that for 4 scale type.
     *
     * @param degrees array contain the student degrees.
     *
     * @return array of points (0 - 4) for each subject.
     */
    private static double[] calculatePointsOfDegree(double[] degrees) {

        int arraySize = degrees.length;
        double[] gpaPoints = new double[arraySize];

        for (int i = 0; i < arraySize; i++) {

            double subjectDegree = degrees[i];

            if (100.0 >= subjectDegree && subjectDegree >= 93.0) {
                gpaPoints[i] = 4.0;
            } else if (93.0 > subjectDegree && subjectDegree >= 88.0) {
                gpaPoints[i] = 3.7;
            } else if (88.0 > subjectDegree && subjectDegree >= 82.0) {
                gpaPoints[i] = 3.3;
            } else if (82.0 > subjectDegree && subjectDegree >= 78.0) {
                gpaPoints[i] = 3.0;
            } else if (78.0 > subjectDegree && subjectDegree >= 74.0) {
                gpaPoints[i] = 2.7;
            } else if (74.0 > subjectDegree && subjectDegree >= 70.0) {
                gpaPoints[i] = 2.3;
            } else if (70.0 > subjectDegree && subjectDegree >= 65.0) {
                gpaPoints[i] = 2.0;
            } else if (65.0 > subjectDegree && subjectDegree >= 60.0) {
                gpaPoints[i] = 1.7;
            } else if (60.0 > subjectDegree && subjectDegree >= 55.0) {
                gpaPoints[i] = 1.3;
            } else if (55.0 > subjectDegree && subjectDegree >= 50.0) {
                gpaPoints[i] = 1.0;
            } else {
                gpaPoints[i] = 0.0;
            }
        }

        return gpaPoints;
    }


    /**
     * Make an array contain hours for the subjects that have degrees (more than 0.0).
     * Any Subject have zero degree will assume that the hours equal to zero too.
     *
     * @param year number of the year.
     * @param term number of the term.
     * @param degrees array contain the subject degrees.
     *
     * @return array of hours that will used in the calculation.
     */
    private static double[] getSubjectsHours(int year, int term, double[] degrees) {

        double[] hours = SemesterInfo.getHoursForSemester(year, term);

        int arraySize = degrees.length; // get the array size.

        for (int i = 0 ; i < arraySize ; i++) {

            if ( (100.0 < degrees[i]) || (degrees[i] < 50.0) ) {
                hours[i] = 0.0;
            }

        }

        return hours;
    }


    /**
     * Multiple two numbers together:
     * For (4 Scale Type) => points & hours.
     * For (% Scale Type) => degrees & hours.
     *
     * @param pointsOrDegree refer to points (in 4 scale type) or degrees (1- 100 in % scale type).
     * @param hours array contains subject hours.
     *
     * @return array of result of multiple for multiplications.
     */
    private static double[] multipleTwoNumbers(double[] pointsOrDegree, double[] hours) {

        int arraySize = pointsOrDegree.length; // get the array size.
        double[] results = new double[arraySize];

        for (int i = 0 ; i < arraySize ; i++) {

            double firstNumberFromArray = pointsOrDegree[i];
            double secondNumberFromArray = hours[i];

            double multipleResult = firstNumberFromArray * secondNumberFromArray;

            results[i] = multipleResult;

        }

        return results;
    }


    /**
     * Sum numbers inside the given array:
     * For (4 Scale Type) => result of multiple points & hours.
     * For (% Scale Type) => result of multiple degrees & hours.
     *
     * @param multipleResults results of multiple two numbers.
     *
     * @return total number for the sum results.
     */
    private static double sumMultipleResults(double[] multipleResults) {

        int arraySize = multipleResults.length; // get the array size.
        double totalOfSumAllMultipleResults = 0.0;

        for (int i = 0 ; i < arraySize ; i++) {

            double multipleResultFromArray = multipleResults[i];

            totalOfSumAllMultipleResults += multipleResultFromArray;

        }

        return totalOfSumAllMultipleResults;
    }


    /**
     * Sum numbers inside the given array (hours).
     *
     * @param hours the subject hours.
     *
     * @return total number for the sum results.
     */
    private static double sumAllHours(double[] hours) {

        int arraySize = hours.length; // get the array size.
        double totalOfSumAllHours = 0.0;

        for (int i = 0 ; i < arraySize ; i++) {

            double hoursFromArray = hours[i];

            totalOfSumAllHours += hoursFromArray;

        }

        if (totalOfSumAllHours == 0.0) {
            totalOfSumAllHours = 1.0;
        }

        return totalOfSumAllHours;
    }


    /**
     * Divide two numbers together:
     * For (4 Scale Type) => sum ( points * hours ) / sum (hours).
     * For (% Scale Type) => sum ( degrees * hours ) / sum (hours).
     *
     * @param top the number that put on above the slash.
     * @param button the number that put under the slash.
     *
     * @return result for the deviation.
     */
    private static double divideTwoNumbers(double top, double button) {

        double result = top / button;

        return result;
    }


    /**
     * Determine the total gpa as letter (A-B-C...).
     *
     * @return letter refers to the total gap.
     */
    private static String getGpaLetter(double gpa) {

        String letter;

        if (100.0 >= gpa && gpa >= 93.0) {
            letter = "(A)";
        } else if (93.0 > gpa && gpa >= 88.0) {
            letter = "(A-)";
        } else if (88.0 > gpa && gpa >= 82.0) {
            letter = "(B+)";
        } else if (82.0 > gpa && gpa >= 78.0) {
            letter = "(B)";
        } else if (78.0 > gpa && gpa >= 74.0) {
            letter = "(B-)";
        } else if (74.0 > gpa && gpa >= 70.0) {
            letter = "(C+)";
        } else if (70.0 > gpa && gpa >= 65.0) {
            letter = "(C)";
        } else if (65.0 > gpa && gpa >= 60.0) {
            letter = "(C-)";
        } else if (60.0 > gpa && gpa >= 55.0) {
            letter = "(D+)";
        } else if (55.0 > gpa && gpa >= 50.0) {
            letter = "(D)";
        } else {
            letter = "(-)";
        }

        Log.i(LOG_TAG, "end of getGpaLetter method inside CalculatorForTotalGpa class : "
                + letter);
        return letter;
    }


    /**
     * Execute the methods that related to the (4 scale type) to get the total gpa as number (0-4).
     *
     * @param year number of the year (0-5).
     * @param term number of the term (1-2).
     * @param degrees array contain the subjects degrees.
     *
     * @return number refers to the total gpa in (4 scale type).
     */
    private static double executeMethodsForFourScale(int year, int term, double[] degrees) {

        double[] totalPoints = calculatePointsOfDegree(degrees);

        double[] totalHours = getSubjectsHours(year, term, degrees);

        double[] ResultsForMultiplePointsAndHours = multipleTwoNumbers(totalPoints, totalHours);

        double sumResultOfMultiple = sumMultipleResults(ResultsForMultiplePointsAndHours);

        double sumAllHours = sumAllHours(totalHours);

        double resultOfDivide = divideTwoNumbers(sumResultOfMultiple, sumAllHours);

        Log.i(LOG_TAG, "end of executeMethodsForFourScale method inside CalculatorForTotalGpa class : "
                + resultOfDivide);
        return resultOfDivide;

    }


    /**
     * Execute the methods that related to the (% scale type) to get the total gpa as number (0-100).
     *
     * @param year number of the year (0-5).
     * @param term number of the term (1-2).
     * @param degrees array contain the subjects degrees.
     *
     * @return number refers to the total gpa in (% scale type).
     */
    private static double executeMethodsForPercentageScale(int year, int term, double[] degrees) {

        double[] totalHours = getSubjectsHours(year, term, degrees);

        double[] ResultsForMultipleDegreesAndHours = multipleTwoNumbers(degrees, totalHours);

        double sumResultOfMultiple = sumMultipleResults(ResultsForMultipleDegreesAndHours);

        double sumAllHours = sumAllHours(totalHours);

        double resultOfDivide = divideTwoNumbers(sumResultOfMultiple, sumAllHours);

        Log.i(LOG_TAG, "end of executeMethodsForPercentageScale method inside CalculatorForTotalGpa class : "
                + resultOfDivide);
        return resultOfDivide;


    }


    /**
     * Get the total gpa for (4 scale type) as double number.
     *
     * @param year number of the year (0-5).
     * @param term number of the term (1-2).
     * @param degrees array contain the subjects degrees.
     *
     * @return total gpa.
     */
    public static double getTotalGpaOfSemesterForFourScale(int year, int term, double[] degrees) {

        double totalGpa = executeMethodsForFourScale(year, term, degrees);

        return totalGpa;
    }


    /**
     * Get the total gpa for (% scale type) as double number.
     *
     * @param year number of the year (0-5).
     * @param term number of the term (1-2).
     * @param degrees array contain the subjects degrees.
     *
     * @return total gpa.
     */
    public static double getTotalGpaOfSemesterForPercentageScale(int year, int term, double[] degrees) {

        double totalGpa = executeMethodsForPercentageScale(year, term, degrees);

        return totalGpa;
    }


    /**
     * Get the total gpa letter and make it ready as a String to display on the screen.
     *
     * @param year number of the year (0-5).
     * @param term number of the term (1-2).
     * @param degrees array contain the subjects degrees.
     *
     * @return total gpa as letter.
     */
    public static String getTotalGpaOfSemesterAsLetter(int year, int term, double[] degrees) {

        double totalGpa = executeMethodsForPercentageScale(year, term, degrees);

        String totalGpaLetter = getGpaLetter(totalGpa);

        return totalGpaLetter;
    }


    /**
     * Shows the results for all methods that use for calculate the total gpa as a number (0-4)
     * by using (4 scale type) in the LOGs as (i) to track every part of the calculation process.
     *
     * @param year number of the year (0-5).
     * @param term number of the term (1-2).
     * @param degrees array contain the subjects degrees.
     */
    public void getLogsTotalGpaForFourScale(int year, int term, double[] degrees) {

        double[] totalPoints = calculatePointsOfDegree(degrees);
        Log.i(LOG_TAG, "end of calculatePointsOfDegree method inside CalculatorForTotalGpa class : "
                + Arrays.toString(totalPoints));


        double[] totalHours = getSubjectsHours(year, term, degrees);
        Log.i(LOG_TAG, "end of getSubjectsHours method inside CalculatorForTotalGpa class : "
                + Arrays.toString(totalHours));


        double[] ResultsForMultiplePointsAndHours = multipleTwoNumbers(totalPoints, totalHours);
        Log.i(LOG_TAG, "end of multipleTwoNumbers method inside CalculatorForTotalGpa class : "
                + Arrays.toString(ResultsForMultiplePointsAndHours));


        double sumResultOfMultiple = sumMultipleResults(ResultsForMultiplePointsAndHours);
        Log.i(LOG_TAG, "end of sumMultipleResults method inside CalculatorForTotalGpa class : "
                + sumResultOfMultiple);


        double sumAllHours = sumAllHours(totalHours);
        Log.i(LOG_TAG, "end of sumAllHours method inside CalculatorForTotalGpa class : "
                + sumAllHours);


        double resultOfDivide = divideTwoNumbers(sumResultOfMultiple, sumAllHours);
        Log.i(LOG_TAG, "end of sumAllHours method inside CalculatorForTotalGpa class : "
                + resultOfDivide);

        Log.i(LOG_TAG, "end of executeMethodsForFourScale method (same above) inside CalculatorForTotalGpa class : "
                + resultOfDivide);

        double totalGpaForFourScale = getTotalGpaOfSemesterForFourScale(year, term, degrees);
        Log.i(LOG_TAG, "end of getTotalGpaOfSemesterForFourScale method inside CalculatorForTotalGpa class : "
                + totalGpaForFourScale);
    }


    /**
     * Shows the results for all methods that use for calculate the total gpa as a number (0-100 %)
     * by using (percentage scale type) in the LOGs as (i) to track every part of the calculation process.
     *
     * @param year number of the year (0-5).
     * @param term number of the term (1-2).
     * @param degrees array contain the subjects degrees.
     */
    public void getLogsForTotalGpaPercentageScale(int year, int term, double[] degrees) {

        double[] totalHours = getSubjectsHours(year, term, degrees);
        Log.i(LOG_TAG, "end of getSubjectsHours method inside CalculatorForTotalGpa class : "
                + Arrays.toString(totalHours));

        double[] ResultsForMultipleDegreesAndHours = multipleTwoNumbers(degrees, totalHours);
        Log.i(LOG_TAG, "end of multipleTwoNumbers method inside CalculatorForTotalGpa class : "
                + Arrays.toString(ResultsForMultipleDegreesAndHours));

        double sumResultOfMultiple = sumMultipleResults(ResultsForMultipleDegreesAndHours);
        Log.i(LOG_TAG, "end of sumMultipleResults method inside CalculatorForTotalGpa class : "
                + sumResultOfMultiple);

        double sumAllHours = sumAllHours(totalHours);
        Log.i(LOG_TAG, "end of sumAllHours method inside CalculatorForTotalGpa class : "
                + sumAllHours);

        double resultOfDivide = divideTwoNumbers(sumResultOfMultiple, sumAllHours);
        Log.i(LOG_TAG, "end of sumAllHours method inside CalculatorForTotalGpa class : "
                + resultOfDivide);

        Log.i(LOG_TAG, "end of executeMethodsForPercentageScale method (same above) inside CalculatorForTotalGpa class : "
                + resultOfDivide);

        double totalGpaForPercentageScale = getTotalGpaOfSemesterForPercentageScale(year, term, degrees);
        Log.i(LOG_TAG, "end of getTotalGpaOfSemesterForPercentageScale method inside CalculatorForTotalGpa class : "
                + totalGpaForPercentageScale);
    }


    /**
     * Shows the results for all methods that use for calculate the total gpa as a letter (A-B-C...)
     * in the LOGs as (i) to track every part of the calculation process.
     *
     * @param year number of the year (0-5).
     * @param term number of the term (1-2).
     * @param degrees array contain the subjects degrees.
     */
    public void getLogTagForTotalGpaAsLetter(int year, int term, double[] degrees) {

        getLogsForTotalGpaPercentageScale(year, term , degrees);

        double totalGpaAsPercentage = executeMethodsForPercentageScale(year, term, degrees);

        String totalGpaAsLetter = getGpaLetter(totalGpaAsPercentage);
        Log.i(LOG_TAG, "end of getGpaLetter method inside CalculatorForTotalGpa class : "
                + totalGpaAsLetter);

        Log.i(LOG_TAG, "end of getTotalGpaOfSemesterAsLetter method (same above) inside CalculatorForTotalGpa class : "
                + totalGpaAsLetter);

    }


}
