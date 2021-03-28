package com.example.android.explorationgpa;

public class SemesterInfo {


    /**
     * take the number of the year and term and specify the exact semester the user want
     *
     * @param year the year which the user choose (o-1-2-3-4).
     * @param term the term which the user choose (1-2).
     *
     * @return the semester that the user want (1-2-3-4-5-6-7-8-9-10)
     */
    public static int getNumberOfSemester(int year, int term) {

        int semester = 0;

        if (year == 0 && term == 1) {
            semester = 1;
        } else if (year == 0 && term == 2) {
            semester = 2;
        } else if (year == 1 && term == 1) {
            semester = 3;
        } else if (year == 1 && term == 2) {
            semester = 4;
        } else if (year == 2 && term == 1) {
            semester = 5;
        } else if (year == 2 && term == 2) {
            semester = 6;
        } else if (year == 3 && term == 1) {
            semester = 7;
        } else if (year == 3 && term == 2) {
            semester = 8;
        } else if (year == 4 && term == 1) {
            semester = 9;
        } else if (year == 4 && term == 2) {
            semester = 10;
        }

        return semester;
    }
}
