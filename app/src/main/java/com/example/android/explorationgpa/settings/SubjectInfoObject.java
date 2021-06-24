package com.example.android.explorationgpa.settings;



public class SubjectInfoObject {


    private int mSubjectNameResourceIds; // for the subject name resource id.
    private double mSubjectHours; // for the subject hours.
    private double mSubjectSuccessDegrees; // for the required degree to pass in the subject.



    /**
     * Constructor method to make an instance from the subject object.
     *
     * @param subjectNameResourceIds the subject name resource id.
     * @param subjectHours the subject hours.
     * @param subjectSuccessDegrees the required degree to pass in the subject.
     */
    public SubjectInfoObject(int subjectNameResourceIds, double subjectHours, double subjectSuccessDegrees) {

        mSubjectNameResourceIds = subjectNameResourceIds;
        mSubjectHours = subjectHours;
        mSubjectSuccessDegrees = subjectSuccessDegrees;

    }



    /**
     * Get the resource id for the subject name from the string.xml.
     *
     * @return resource id from the string.xml.
     */
    public int getSubjectNameResourceIds() {
        return mSubjectNameResourceIds;
    }


    /**
     * Get the subject hours.
     *
     * @return double number (usually 2.0 or 3.0).
     */
    public double getSubjectHours() {
        return mSubjectHours;
    }


    /**
     * Get the required degree to pass in the subject.
     *
     * @return double number (usually 50.0 or 60.0).
     */
    public double getSubjectSuccessDegrees() {
        return mSubjectSuccessDegrees;
    }



}
