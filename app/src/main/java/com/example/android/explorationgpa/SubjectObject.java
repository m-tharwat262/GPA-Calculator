package com.example.android.explorationgpa;

public class SubjectObject {

    private int mResourceIdForSubjectName; // for the resource id fot the string of the subject name
    private int mSubjectDegree; // fot the degree of the subject which the student give


    /**
     * constructor method to make an instance from the subject object.
     *
     * @param resourceIdForSubjectName subject resource id for subject name.
     * @param subjectDegree subject degree which the user give.
     */
    public SubjectObject(int resourceIdForSubjectName, int subjectDegree) {

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
    public int getSubjectDegree() {
        return mSubjectDegree;
    }


    /**
     * set degree of the subject which the user give.
     *
     * @param subjectDegree student degree in the subject
     */
    public void setSubjectDegree(int subjectDegree) {
        mSubjectDegree = subjectDegree;
    }


    /**
     * determine the gpa letter (A-B-C...) for the subject by the student degree in it.
     *
     * @return gpa letter.
     */
    public String getGpaLetterOfSubject() {

        if (100 >= mSubjectDegree && mSubjectDegree >= 93) {
            return "(A)";
        } else if (93 > mSubjectDegree && mSubjectDegree >= 88) {
            return "(A-)";
        } else if (88 > mSubjectDegree && mSubjectDegree >= 82) {
            return "(B+)";
        } else if (82 > mSubjectDegree && mSubjectDegree >= 78) {
            return "(B)";
        } else if (78 > mSubjectDegree && mSubjectDegree >= 74) {
            return "(B-)";
        } else if (74 > mSubjectDegree && mSubjectDegree >= 70) {
            return "(C+)";
        } else if (70 > mSubjectDegree && mSubjectDegree >= 65) {
            return "(C)";
        } else if (65 > mSubjectDegree && mSubjectDegree >= 60) {
            return "(C-)";
        } else if (60 > mSubjectDegree && mSubjectDegree >= 55) {
            return "(D+)";
        } else if (55 > mSubjectDegree && mSubjectDegree >= 50) {
            return "(D)";
        } else {
            return "(F!)";
        }

    }



}