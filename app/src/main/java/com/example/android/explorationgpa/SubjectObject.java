package com.example.android.explorationgpa;

public class SubjectObject {

    private int mResourceIdForSubjectName; // for the resource id fot the string of the subject name
    private double mSubjectDegree; // fot the degree of the subject which the student give


    /**
     * constructor method to make an instance from the subject object.
     *
     * @param resourceIdForSubjectName subject resource id for subject name.
     * @param subjectDegree subject degree which the user give.
     */
    public SubjectObject(int resourceIdForSubjectName, double subjectDegree) {

        mResourceIdForSubjectName = resourceIdForSubjectName;
        mSubjectDegree = subjectDegree;
    }


    /**
     * constructor method to make an instance from the subject object.
     *
     * @param resourceIdForSubjectName subject resource id for subject name.
     */
    public SubjectObject(int resourceIdForSubjectName) {

        mResourceIdForSubjectName = resourceIdForSubjectName;
    }


    /**
     * get the resource id for the subject name from the string.xml.
     *
     * @return resource id from the string.xml.
     */
    public int getResourceIdForSubjectName() {
        return mResourceIdForSubjectName;
    }


    /**
     * get degree of the subject which the user give.
     *
     * @return student degree in the subject.
     */
    public double getSubjectDegree() {
        return mSubjectDegree;
    }


    /**
     * set degree of the subject which the user give.
     *
     * @param subjectDegree student degree in the subject
     */
    public void setSubjectDegree(double subjectDegree) {
        mSubjectDegree = subjectDegree;
    }


    /**
     * determine the gpa letter (A-B-C...) for the subject by the student degree in it.
     *
     * @return gpa letter.
     */
    public String getGpaLetterOfSubject() {

        if (100.0 >= mSubjectDegree && mSubjectDegree >= 93.0) {
            return "(A)";
        } else if (93.0 > mSubjectDegree && mSubjectDegree >= 88.0) {
            return "(A-)";
        } else if (88.0 > mSubjectDegree && mSubjectDegree >= 82.0) {
            return "(B+)";
        } else if (82.0 > mSubjectDegree && mSubjectDegree >= 78.0) {
            return "(B)";
        } else if (78.0 > mSubjectDegree && mSubjectDegree >= 74.0) {
            return "(B-)";
        } else if (74.0 > mSubjectDegree && mSubjectDegree >= 70.0) {
            return "(C+)";
        } else if (70.0 > mSubjectDegree && mSubjectDegree >= 65.0) {
            return "(C)";
        } else if (65.0 > mSubjectDegree && mSubjectDegree >= 60.0) {
            return "(C-)";
        } else if (60.0 > mSubjectDegree && mSubjectDegree >= 55.0) {
            return "(D+)";
        } else if (55.0 > mSubjectDegree && mSubjectDegree >= 50.0) {
            return "(D)";
        } else {
            return "(F!)";
        }

    }



}