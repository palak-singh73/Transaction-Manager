package com.example.project3;

/**
 * Holds the identity of holders
 * Includes first name, last name, and date of birth
 * @author Palak Singh, Daniel Guan
 */
public class Profile implements Comparable<Profile>{

    private String fname;
    private String lname;
    private Date dob;

    /**
     * Contructor to make a profile object
     * @param fname String holding the first name
     * @param lname String holding the last name
     * @param dob Date that corresponds to when the person is born
     */
    public Profile(String fname, String lname, String dob){
        this.fname = fname;
        this.lname = lname;
        this.dob = new Date(dob);
    }

    /**
     * Compares two profile, first based on first name, then last name, and lastly dob
     * @param profile the object to be compared.
     * @return -1 if the given profile is before the parameter profile
     *          1 if the given profile is after the parameter profile
     *          0 if both profiles are the same
     */
    @Override
    public int compareTo(Profile profile){
        if(!lname.equalsIgnoreCase(profile.lname)){
            return lname.compareTo(profile.lname);
        }else if (!fname.equalsIgnoreCase(profile.fname)){
            return fname.compareTo(profile.fname);
        }
        return dob.compareTo(profile.dob);
    }

    /**
     * Getter method for date of birth of account holder
     * @return Date of birth
     */
    public Date getDob(){
        return dob;
    }

    /**
     * toString method to show account holder information
     * @return String FirstName LastName MM/DD/YYYY
     */
    public String toString(){
        return fname + " " + lname + " " + dob.toString();
    }
}